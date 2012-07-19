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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.getperka.flatpack.domain.PersistentEmployee;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.TestTypeSource;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PersistentPackTest extends FlatPackTest {
  static class TestPersistenceMapper implements PersistenceMapper, EntityResolver {
    final Map<UUID, HasUuid> data = FlatPackCollections.mapForLookup();

    @Override
    public boolean canPersist(Class<? extends HasUuid> entityType) {
      return true;
    }

    @Override
    public boolean isPersisted(HasUuid entity) {
      data.put(entity.getUuid(), entity);
      return true;
    }

    @Override
    public <T extends HasUuid> T resolve(Class<T> clazz, UUID uuid) throws Exception {
      HasUuid toReturn = data.get(uuid);
      return toReturn == null ? null : clazz.cast(toReturn);
    }
  }

  private TestPersistenceMapper persistence = new TestPersistenceMapper();

  /**
   * Verify that persistent entities with no dirty properties do not have any entries in the data
   * section.
   */
  @Test
  public void testElidedEntities() throws IOException {
    PersistentEmployee e = makePersistentEmployee();
    e.markPersistent();

    StringWriter out = new StringWriter();
    flatpack.getPacker().pack(FlatPackEntity.entity(e), out);

    JsonObject payload = new JsonParser().parse(out.toString()).getAsJsonObject();
    assertTrue(out.toString(), payload.get("data").getAsJsonObject().entrySet().isEmpty());
  }

  /**
   * Verify that an entity that supports dirty-tracking results in fewer properties in the payload
   * and that the dirty-tracking methods are called as expected.
   */
  @Test
  public void testSparseProperties() throws IOException {
    PersistentEmployee e = makePersistentEmployee();
    e.setTrackedProperty("trackedProperty");
    assertEquals(Collections.singleton("trackedProperty"), e.dirtyPropertyNames());

    StringWriter out = new StringWriter();
    flatpack.getPacker().pack(FlatPackEntity.entity(e), out);

    assertFalse(out.toString().contains("name"));
    assertTrue(out.toString().contains("trackedProperty"));

    FlatPackEntity<PersistentEmployee> entity = flatpack.getUnpacker()
        .unpack(PersistentEmployee.class, new StringReader(out.toString()), null);
    PersistentEmployee e2 = entity.getValue();
    assertTrue(e2.wasPersistent());
    assertTrue(e2.dirtyPropertyNames().isEmpty());
  }

  /**
   * Just make sure the test resolver is capturing data correctly.
   */
  @Test
  public void testWiring() throws IOException {
    Employee e = makeEmployee();
    Employee e2 = deepPack(Employee.class, e);

    assertSame(e, e2);
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration()
        .addEntityResolver(persistence)
        .addPersistenceMapper(persistence)
        .addTypeSource(new TestTypeSource());
  }
}
