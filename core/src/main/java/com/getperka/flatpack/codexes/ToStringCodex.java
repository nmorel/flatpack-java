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
package com.getperka.flatpack.codexes;

import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.inject.Inject;

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonElement;
import com.google.inject.TypeLiteral;

/**
 * Attempts to handle any value-like object that has a public, one-arg constructor that accepts a
 * String or an Object.
 */
public class ToStringCodex<T> extends ValueCodex<T> {
  private Constructor<T> constructor;

  protected ToStringCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.STRING)
        .withTypeHint(TypeHint.create(constructor.getDeclaringClass()))
        .build();
  }

  @Override
  public T readNotNull(JsonElement element, DeserializationContext context) throws Exception {
    return constructor.newInstance(element.getAsString());
  }

  @Override
  public void writeNotNull(T object, SerializationContext context) throws IOException {
    context.getWriter().value(object.toString());
  }

  @Inject
  @SuppressWarnings("unchecked")
  void inject(TypeLiteral<T> type) {
    Constructor<T> found = null;
    try {
      found = (Constructor<T>) type.getRawType().getConstructor(String.class);
    } catch (NoSuchMethodException e) {}
    try {
      found = (Constructor<T>) type.getRawType().getConstructor(Object.class);
    } catch (NoSuchMethodException e) {}
    if (found == null) {
      throw new IllegalArgumentException("No suitable constructors");
    }
    constructor = found;
  }
}