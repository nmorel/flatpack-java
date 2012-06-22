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

import java.util.UUID;

import com.getperka.flatpack.HasUuid;

/**
 * Allows existing entities to be injected into the reification process. The EntityResolver will be
 * used when there is an unsatisfied reference to a UUID in the payload (i.e. there is no object in
 * the {@code data} section with the requested UUID).
 */
public interface EntityResolver {
  /**
   * Return an existing instance of the requested type with the given uuid or {@code null}
   * 
   * @param <T> the type to be loaded
   * @param clazz the type to be loaded
   * @param uuid the UUID of the existing entity
   * @return the entity or {@code null} if it did not already exist
   */
  <T extends HasUuid> T resolve(Class<T> clazz, UUID uuid) throws Exception;
}
