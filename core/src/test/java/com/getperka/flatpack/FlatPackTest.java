/*
 * #%L
 * FlatPack serialization code
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.junit.After;
import org.junit.Before;

import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.Manager;
import com.getperka.flatpack.domain.PersistentEmployee;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.inject.FlatPackTestModule;
import com.getperka.flatpack.inject.PackScope;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * A base test type for constructing a FlatPack stack.
 */
public abstract class FlatPackTest {

  private int counter;
  @Inject
  private Provider<DeserializationContext> deserializationContexts;
  @Inject
  private Provider<Employee> employees;
  @Inject
  private Provider<Manager> managers;
  @Inject
  private Injector injector;
  @Inject
  protected FlatPack flatpack;
  @Inject
  private PackScope packScope;
  @Inject
  private Provider<PersistentEmployee> persistentEmployees;
  @Inject
  private Provider<SerializationContext> serializationContexts;

  @After
  public void after() {
    packScope.exit();
  }

  @Before
  public void before() {
    Guice.createInjector(new FlatPackTestModule(getConfiguration())).injectMembers(this);
  }

  protected void check(Employee expected, Employee actual) {
    assertNotSame(expected, actual);
    assertEquals(expected, actual);
    assertEquals(expected.getAddress().getStreet(), actual.getAddress().getStreet());
    assertEquals(expected.getEmployeeNumber(), actual.getEmployeeNumber());
    assertEquals(expected.getName(), actual.getName());
  }

  protected void closeContext() {
    packScope.exit();
  }

  protected <T> T deepPack(Type type, T value) throws IOException {
    return deepPack(type, value, null);
  }

  protected <T> T deepPack(Type type, T value, Principal principal) throws IOException {
    @SuppressWarnings("unchecked")
    FlatPackEntity<T> entity = (FlatPackEntity<T>) FlatPackEntity.create(type, value, principal)
        .withTraversalMode(TraversalMode.DEEP);

    StringWriter out = new StringWriter();
    flatpack.getPacker().pack(entity, out);
    System.out.println(out.toString());

    FlatPackEntity<T> entity2 = flatpack.getUnpacker().unpack(
        type, new StringReader(out.toString()), principal);
    return entity2.getValue();
  }

  /**
   * Must be paired with a call to {@link #closeContext()}.
   */
  protected DeserializationContext deserializationContext() {
    packScope.enter();
    return deserializationContexts.get();
  }

  protected Configuration getConfiguration() {
    return new Configuration().withPrettyPrint(true).withVerbose(true);
  }

  protected Employee makeEmployee() {
    Employee toReturn = employees.get();
    toReturn.setEmployeeNumber(++counter);
    toReturn.setName("Employee Name");
    toReturn.getAddress().setStreet("street");
    return toReturn;
  }

  protected Manager makeManager() {
    Manager toReturn = managers.get();
    toReturn.setName("Manager Name");
    toReturn.getAddress().setStreet("manager street");
    return toReturn;
  }

  protected PersistentEmployee makePersistentEmployee() {
    PersistentEmployee toReturn = persistentEmployees.get();
    toReturn.setEmployeeNumber(++counter);
    toReturn.setName("Employee Name");
    toReturn.getAddress().setStreet("street");
    return toReturn;
  }

  /**
   * Must be paired with a call to {@link #closeContext()}.
   */
  protected SerializationContext serializationContext(FlatPackEntity<?> entity, Writer out) {
    JsonWriter json = new JsonWriter(out);
    json.setLenient(true);
    json.setIndent("  ");
    json.setSerializeNulls(false);
    packScope.enter().withEntity(entity).withJsonWriter(json);
    return serializationContexts.get();
  }

  protected SerializationContext serializationContext(Writer out) {
    return serializationContext(FlatPackEntity.nullResponse(), out);
  }

  /**
   * Performs an encode/decode operation.
   */
  protected <T> T testCodex(TypeLiteral<? extends Codex<T>> codexType, T value) {
    return testCodex(codexType, value, null);
  }

  /**
   * Performs an encode/decode operation, accumulating any scanned entities.
   */
  protected <T> T testCodex(TypeLiteral<? extends Codex<T>> codexType, T value, Set<HasUuid> scanned) {
    Provider<? extends Codex<T>> codexes = injector.getProvider(Key.get(codexType));

    // Just smoke-test the description
    assertNotNull(codexes.get().describe());

    StringWriter out = new StringWriter();
    SerializationContext serialization = serializationContext(out);
    try {
      Codex<T> codex = codexes.get();
      codex.scan(value, serialization);
      codexes.get().write(value, serialization);
      if (scanned != null) {
        scanned.addAll(serialization.getEntities());
      }
    } finally {
      closeContext();
    }

    DeserializationContext deserialization = deserializationContext();
    try {
      JsonElement element = new JsonParser().parse(out.toString());
      return codexes.get().read(element, deserialization);
    } finally {
      closeContext();
    }
  }
}
