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

import static com.getperka.flatpack.util.FlatPackTypes.getSingleParameterization;

import java.lang.reflect.Type;

/**
 * This is the type reference pattern to work around Java's lack of generic type literals.
 * 
 * @param <T> the {@link Type} to be represented
 */
public class TypeReference<T> {

  private final Type type;

  public TypeReference(Type type) {
    this.type = type;
  }

  protected TypeReference() {
    type = getSingleParameterization(getClass(), TypeReference.class);
  }

  public Type getType() {
    return type;
  }
}
