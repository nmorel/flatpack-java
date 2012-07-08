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
package com.getperka.flatpack.ext;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.getperka.flatpack.HasTimestamps;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.stream.JsonWriter;

/**
 * Holds all of the state necessary to perform a serialization.
 */
public class SerializationContext extends BaseContext {

  private final Map<HasUuid, Void> entities = FlatPackCollections.mapForIteration();

  @Inject
  private DateTime lastModifiedTime;
  @Inject
  private TraversalMode traversalMode;
  @Inject
  private JsonWriter writer;

  SerializationContext() {}

  /**
   * Returns {@code true} if {@code entity} needs to be processed.
   */
  public boolean add(HasUuid entity) {
    if (entities.containsKey(entity)) {
      return false;
    }
    entities.put(entity, null);
    return true;
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }

  /**
   * Returns the entities that should be included in the payload's data section, filtered by the
   * cutoff time.
   */
  public Set<HasUuid> getEntities() {
    if (lastModifiedTime == null) {
      return entities.keySet();
    }
    Set<HasUuid> toReturn = FlatPackCollections.setForIteration();
    for (HasUuid entity : entities.keySet()) {
      if (entity instanceof HasTimestamps) {
        HasTimestamps ts = (HasTimestamps) entity;
        DateTime lastModified = ts.getUpdatedAt() == null ? ts.getCreatedAt() : ts.getUpdatedAt();
        if (lastModifiedTime.isBefore(lastModified)) {
          toReturn.add(entity);
        }
      } else {
        toReturn.add(entity);
      }
    }
    return toReturn;
  }

  /**
   * Returns the {@link TraversalMode} that should be used when scanning an entity.
   */
  public TraversalMode getTraversalMode() {
    return traversalMode;
  }

  /**
   * Returns the JsonWriter accumulating JSON to be written.
   */
  public JsonWriter getWriter() {
    return writer;
  }
}
