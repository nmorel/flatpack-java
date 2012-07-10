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
import java.util.Collection;

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

public abstract class CollectionCodex<T extends Collection<V>, V> extends Codex<T> {
  private Codex<V> valueCodex;

  CollectionCodex() {}

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
  public T readNotNull(JsonElement element, DeserializationContext context)
      throws Exception {
    T toReturn = newCollection();
    JsonArray array = element.getAsJsonArray();
    int count = 0;
    for (JsonElement elt : array) {
      context.pushPath("[" + count++ + "]");
      toReturn.add(valueCodex.read(elt, context));
      context.popPath();
    }
    return toReturn;
  }

  @Override
  public void scanNotNull(T collection, SerializationContext context) throws Exception {
    int count = 0;
    for (V t : collection) {
      context.pushPath("[" + count++ + "]");
      valueCodex.scan(t, context);
      context.popPath();
    }
  }

  @Override
  public void writeNotNull(T collection, SerializationContext context) throws IOException {
    JsonWriter writer = context.getWriter();
    writer.beginArray();
    int count = 0;
    for (V t : collection) {
      context.pushPath("[" + count++ + "]");
      valueCodex.write(t, context);
      context.popPath();
    }
    writer.endArray();
  }

  protected abstract T newCollection();

  @Inject
  @SuppressWarnings("unchecked")
  void setType(TypeLiteral<V> valueType, TypeContext context) {
    valueCodex = (Codex<V>) context.getCodex(valueType.getType());
  }
}