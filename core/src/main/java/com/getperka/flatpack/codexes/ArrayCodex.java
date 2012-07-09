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

import static com.getperka.flatpack.util.FlatPackTypes.erase;

import java.io.IOException;
import java.lang.reflect.Array;

import javax.inject.Inject;

import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.google.inject.TypeLiteral;

public class ArrayCodex<T> extends Codex<T[]> {
  private final Class<T> elementType;
  private final Codex<T> valueCodex;

  @Inject
  @SuppressWarnings("unchecked")
  ArrayCodex(TypeLiteral<T> elementType, TypeContext context) {
    this.elementType = erase(elementType.getType());
    this.valueCodex = (Codex<T>) context.getCodex(elementType.getType());
  }

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.LIST)
        .withListElement(valueCodex.describe())
        .build();
  }

  @Override
  public String getPropertySuffix() {
    return valueCodex.getPropertySuffix();
  }

  @Override
  public T[] readNotNull(JsonElement element, DeserializationContext context) throws Exception {
    JsonArray array = element.getAsJsonArray();
    @SuppressWarnings("unchecked")
    T[] toReturn = (T[]) Array.newInstance(elementType, array.size());
    for (int i = 0, j = array.size(); i < j; i++) {
      context.pushPath("[" + i + "]");
      toReturn[i] = valueCodex.read(array.get(i), context);
      context.popPath();
    }
    return toReturn;
  }

  @Override
  public void scanNotNull(T[] object, SerializationContext context) {
    int count = 0;
    for (T t : object) {
      context.pushPath("[" + count++ + "]");
      valueCodex.scan(t, context);
      context.popPath();
    }
  }

  @Override
  public void writeNotNull(T[] object, SerializationContext context) throws IOException {
    JsonWriter writer = context.getWriter();
    writer.beginArray();
    int count = 0;
    for (T t : object) {
      context.pushPath("[" + count++ + "]");
      valueCodex.write(t, context);
      context.popPath();
    }
    writer.endArray();
  }
}