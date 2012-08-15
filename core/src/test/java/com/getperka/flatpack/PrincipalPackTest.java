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

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.security.PermitAll;

import org.junit.Test;

import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.Person;
import com.getperka.flatpack.domain.TestTypeSource;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.PrincipalMapper;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * Verify that principal-related security works.
 */
public class PrincipalPackTest extends FlatPackTest {
  class TestEntityResolver implements EntityResolver {
    @Override
    public <T extends HasUuid> T resolve(Class<T> clazz, UUID uuid) throws Exception {
      return clazz.cast(data.get(uuid));
    }
  }

  static class TestPrincipal implements Principal {
    private final UUID uuid;

    public TestPrincipal(UUID uuid) {
      this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof TestPrincipal && uuid.equals(((TestPrincipal) o).uuid);
    }

    @Override
    public String getName() {
      return uuid.toString();
    }

    @Override
    public int hashCode() {
      return uuid.hashCode();
    }
  }

  static class TestPrincipalMapper implements PrincipalMapper {
    @Override
    public List<Principal> getPrincipals(HasUuid entity) {
      if (entity instanceof Person) {
        return Collections.<Principal> singletonList(new TestPrincipal(entity.getUuid()));
      }
      return null;
    }

    /**
     * It doesn't matter what role is returned here, as long as the list isn't zero-length since all
     * of the test objects are annotated with {@link PermitAll}.
     */
    @Override
    public List<String> getRoles(Principal principal) {
      return Collections.singletonList("role");
    }

    /**
     * No super-users.
     */
    @Override
    public boolean isAccessEnforced(Principal principal, HasUuid entity) {
      return true;
    }

    /**
     * Agree to map all Person subtypes to a Principal.
     */
    @Override
    public boolean isMapped(List<Class<? extends HasUuid>> pathSoFar,
        Class<? extends HasUuid> entitiyType) {
      return Person.class.isAssignableFrom(entitiyType);
    }
  }

  private final Map<UUID, HasUuid> data = FlatPackCollections.mapForLookup();

  @Test
  public void test() throws IOException {
    Employee e1 = makeEmployee();
    Employee e2 = makeEmployee();
    String e2Name = e2.getName();

    Employee e1Send = makeEmployee();
    e1Send.setUuid(e1.getUuid());
    e1Send.setName("Should see this");

    Employee e2Send = makeEmployee();
    e2Send.setUuid(e2.getUuid());
    e2Send.setName("Should not see this");

    data.put(e1.getUuid(), e1);
    data.put(e2.getUuid(), e2);

    // Update self
    Employee updated = deepPack(Employee.class, e1Send, new TestPrincipal(e1.getUuid()));
    assertEquals("Should see this", updated.getName());

    // Try updating one employee as another
    updated = deepPack(Employee.class, e2Send, new TestPrincipal(e1.getUuid()));
    assertEquals(e2Name, updated.getName());
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration()
        .addEntityResolver(new TestEntityResolver())
        .withPrincipalMapper(new TestPrincipalMapper())
        .addTypeSource(new TestTypeSource());
  }
}
