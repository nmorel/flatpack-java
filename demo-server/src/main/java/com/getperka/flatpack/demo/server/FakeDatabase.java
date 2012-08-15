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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;

/**
 * This is just a simple, in-memory datastructure to hold the sample data. In reality, you would
 * likely use a JPA implementation.
 */
public class FakeDatabase {
  // Retain order
  private final Map<UUID, HasUuid> allEntities = new LinkedHashMap<UUID, HasUuid>();

  public FakeDatabase() {
    // TODO: initialize from fake data
  }

  /**
   * Reset the contents of the fake store.
   */
  public void clear() {
    allEntities.clear();
  }

  /**
   * Return all entities of type {@code clazz} that were passed to {@link #persist(HasUuid)}.
   */
  public <T extends HasUuid> List<T> get(Class<T> clazz) {
    List<T> toReturn = new ArrayList<T>();
    for (HasUuid entity : allEntities.values()) {
      if (clazz.isInstance(entity)) {
        toReturn.add(clazz.cast(entity));
      }
    }
    return toReturn;
  }

  /**
   * Return a specific entity passed to {@link #persist(HasUuid)}.
   */
  public <T extends HasUuid> T get(Class<T> clazz, UUID uuid) {
    return clazz.cast(allEntities.get(uuid));
  }

  public boolean isPersisted(HasUuid entity) {
    return allEntities.containsKey(entity.getUuid());
  }

  /**
   * Add an entity. This will not add any referenced entities, so it isn't truly representative of
   * what a real persistence engine would do.
   */
  public void persist(HasUuid entity) {
    allEntities.put(entity.getUuid(), entity);
  }
}
