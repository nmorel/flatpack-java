package com.getperka.flatpack;

import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.junit.After;
import org.junit.Before;

import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.inject.FlatPackModule;
import com.getperka.flatpack.inject.PackScope;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * A base test type for constructing a FlatPack stack.
 */
public abstract class FlatPackTest {

  @Inject
  private Provider<DeserializationContext> deserializationContexts;
  @Inject
  private Injector injector;
  @Inject
  protected FlatPack flatpack;
  @Inject
  private PackScope packScope;
  @Inject
  private Provider<SerializationContext> serializationContexts;

  @After
  public void after() {
    packScope.exit();
  }

  @Before
  public void before() {
    Guice.createInjector(new FlatPackModule(getConfiguration())).injectMembers(this);
  }

  protected void closeContext() {
    packScope.exit();
  }

  /**
   * Must be paired with a call to {@link #closeContext()}.
   */
  protected DeserializationContext deserializationContext() {
    packScope.enter();
    return deserializationContexts.get();
  }

  protected Configuration getConfiguration() {
    return new Configuration();
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
      JsonElement element = new Gson().fromJson(out.toString(), JsonElement.class);
      return codexes.get().read(element, deserialization);
    } finally {
      closeContext();
    }
  }
}
