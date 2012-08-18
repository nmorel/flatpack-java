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

import java.util.Set;

/**
 * A capability interface for entity types that implement dirty-tracking.
 */
public interface PersistenceAware extends HasUuid {
  /**
   * Returns names of the properties of the object that should be serialized, assuming the server
   * has prior knowledge of the entity.
   */
  Set<String> dirtyPropertyNames();

  /**
   * Called by the entity deserialization when the incoming payload indicates that the entity has
   * been persisted on the other side of the connection.
   */
  void markPersistent();

  /**
   * Returns {@code true} if the entity was persisted on the other side of the connection.
   */
  boolean wasPersistent();
}
