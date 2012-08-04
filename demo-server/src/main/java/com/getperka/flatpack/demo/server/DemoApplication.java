/*
 * #%L
 * FlatPack Demonstration Server
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
package com.getperka.flatpack.demo.server;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.Application;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceMapper;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.jersey.FlatPackProvider;
import com.getperka.flatpack.jersey.FlatPackResolver;
import com.getperka.flatpack.search.SearchTypeSource;

/**
 * A trivial JAX-RS application that registers a single resource and configures the FlatPack
 * adapters.
 */
public class DemoApplication extends Application {

  /**
   * In a production system, this would typically be implemented as a query on the underlying
   * database.
   */
  private class FakeDatabaseResolver implements EntityResolver {
    @Override
    public <T extends HasUuid> T resolve(Class<T> clazz, UUID uuid) throws Exception {
      return db.get(clazz, uuid);
    }
  }

  /**
   * Provides hints to the FlatPack stack about an entity's persistence state to enable sparse
   * property transmission.
   */
  private class FakePersistenceMapper implements PersistenceMapper {
    /**
     * A real implementation may have separate type hierarchies for persistent and ephemeral
     * entities.
     */
    @Override
    public boolean canPersist(Class<? extends HasUuid> entityType) {
      return true;
    }

    @Override
    public boolean isPersisted(HasUuid entity) {
      return db.isPersisted(entity);
    }

  }

  private FakeDatabase db = new FakeDatabase();

  @Override
  public Set<Object> getSingletons() {
    Set<Object> toReturn = new LinkedHashSet<Object>();
    // Create the FlatPack configuration. This object adapts FlatPack behaviors to the local system.
    Configuration configuration = new Configuration()
        /*
         * The EntityResolver is optional and is used when FlatPack deserializes a payload to
         * retrieve a persistent entity to which properties in the incoming payload will be applied.
         * Implementors that do not that need to manually merge incoming DTOs with persistent state
         * may omit this configuration.
         */
        .addEntityResolver(new FakeDatabaseResolver())
        /*
         * The PersistenceMapper is optional and provides the FlatPack stack with hints related to a
         * entity's persistence status.
         */
        .addPersistenceMapper(new FakePersistenceMapper())
        /*
         * At least one TypeSource is required. The TypeSources associated with a FlatPack stack
         * determine the complete set of entity types that can be processed. This SearchTypeSource
         * scans the classpath for HasUuid subtypes.
         */
        .addTypeSource(new SearchTypeSource("com.getperka.flatpack.demo.server"))
        .withPrettyPrint(true)
        /*
         * A PrincipalMapper is optional and, if present, enables the use principal-based property
         * access restrictions whereby certain Principals are allowed to mutate only specific
         * entities. This can be used, for example, to ensure that a Customer can only mutate
         * properties that have an @InheritPrincipal chain back to the Customer.
         */
        .withPrincipalMapper(new DemoPrincipalMapper())
        /*
         * A RoleMapper enables role-based property access, which restricts property getters and
         * setters based on @PermitAll / @DenyAll / @RolesAllowed annotations. Beyond just simple
         * security measures, roles can be used to create sets of properties to reduce payload sizes
         * (e.g. "CustomerSummary" vs. "CustomerDetail").
         */
        .withRoleMapper(new DemoRoleMapper())
        .withVerbose(true);
    // The FlatPackResolver makes a FlatPack instance available through the Resources interface
    toReturn.add(new FlatPackResolver(configuration));
    // The FlatPackProvider installs MessageBodyReader/Writer behavior
    toReturn.add(new FlatPackProvider());
    // This is a Resource endpoint, where HTTP paths are resolved to method invocations
    toReturn.add(new DemoResource(db));
    return toReturn;
  }
}
