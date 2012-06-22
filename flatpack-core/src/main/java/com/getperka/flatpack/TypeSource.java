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

import java.util.Collections;
import java.util.Set;

/**
 * Injectable logic to provide a FlatPack stack with the types that it should be able to unpack.
 */
public interface TypeSource {
  /**
   * A no-op type source.
   */
  TypeSource EMPTY = new TypeSource() {
    @Override
    public Set<Class<?>> getTypes() {
      return Collections.emptySet();
    }
  };

  /**
   * Returns all types that the {@link Unpacker} should be able to unpack. Any types in this set
   * that are not assignable to {@link HasUuid} will be ignored.
   */
  Set<Class<?>> getTypes();
}
