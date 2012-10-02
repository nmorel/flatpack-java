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
import java.util.Map;

import javax.inject.Inject;

import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.google.inject.TypeLiteral;

/**
 * A map of String to a arbitrary value.
 * 
 * @param <V> the map value type
 */
public class StringMapCodex<V> extends Codex<Map<String, V>> {
  private Codex<V> valueCodex;

  protected StringMapCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.MAP)
        .withMapKey(new Type.Builder()
            .withJsonKind(JsonKind.STRING)
            .build())
        .withMapValue(valueCodex.describe())
        .build();
  }

  @Override
  public String getPropertySuffix() {
    return valueCodex.getPropertySuffix();
  }

  @Override
  public Map<String, V> readNotNull(JsonElement element, DeserializationContext context)
      throws IOException {
    Map<String, V> toReturn = FlatPackCollections.mapForIteration();
    for (Map.Entry<String, JsonElement> elt : element.getAsJsonObject().entrySet()) {
      context.pushPath("[" + elt.getKey() + "]");
      try {
        V value = valueCodex.read(elt.getValue(), context);
        toReturn.put(elt.getKey().toString(), value);
      } catch (Exception e) {
        context.fail(e);
      } finally {
        context.popPath();
      }
    }
    return toReturn;
  }

  @Override
  public void scanNotNull(Map<String, V> object, SerializationContext context) {
    for (Map.Entry<String, V> entry : object.entrySet()) {
      context.pushPath("[" + entry.getKey() + "]");
      try {
        valueCodex.scan(entry.getValue(), context);
      } finally {
        context.popPath();
      }
    }
  }

  @Override
  public void writeNotNull(Map<String, V> object, SerializationContext context)
      throws IOException {
    JsonWriter writer = context.getWriter();
    writer.beginObject();
    for (Map.Entry<String, V> entry : object.entrySet()) {
      String key = entry.getKey().toString();
      context.pushPath("[" + key + "]");
      try {
        writer.name(key);
        valueCodex.write(entry.getValue(), context);
      } finally {
        context.popPath();
      }
    }
    writer.endObject();
  }

  @Inject
  @SuppressWarnings("unchecked")
  void inject(TypeLiteral<V> valueType, TypeContext typeContext) {
    this.valueCodex = (Codex<V>) typeContext.getCodex(valueType.getType());
  }
}