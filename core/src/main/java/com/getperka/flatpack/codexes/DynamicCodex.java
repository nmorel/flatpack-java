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
import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Provider;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Allows arbitrary objects to be written by examining their type and read by inferring a type from
 * the payload.
 */
public class DynamicCodex extends Codex<Object> {
  private static final Pattern UUID_PATTERN = Pattern
      .compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

  // Use Provider to prevent cyclic reference
  private Provider<ListCodex<Object>> listCodex;
  private Provider<StringMapCodex<Object>> mapCodex;
  private TypeContext typeContext;

  protected DynamicCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder().withJsonKind(JsonKind.ANY).build();
  }

  /**
   * Attempt to infer the type from the JsonElement presented.
   * <ul>
   * <li>boolean -> {@link Boolean}
   * <li>number -> {@link BigDecimal}
   * <li>string -> {@link HasUuid} or {@link String}
   * <li>array -> {@link ListCodex}
   * <li>object -> {@link StringMapCodex}
   * </ul>
   */
  @Override
  public Object readNotNull(JsonElement element, DeserializationContext context) throws Exception {
    if (element.isJsonPrimitive()) {
      JsonPrimitive primitive = element.getAsJsonPrimitive();
      if (primitive.isBoolean()) {
        return primitive.getAsBoolean();
      } else if (primitive.isNumber()) {
        // Always return numbers as BigDecimals for consistency
        return primitive.getAsBigDecimal();
      } else {
        String value = primitive.getAsString();

        // Interpret UUIDs as entity references
        if (UUID_PATTERN.matcher(value).matches()) {
          UUID uuid = UUID.fromString(value);
          HasUuid entity = context.getEntity(uuid);
          if (entity != null) {
            return entity;
          }
        }

        return value;
      }
    } else if (element.isJsonArray()) {
      return listCodex.get().readNotNull(element, context);
    } else if (element.isJsonObject()) {
      return mapCodex.get().readNotNull(element, context);
    }
    context.fail(new UnsupportedOperationException("Cannot infer data type for "
      + element.toString()));
    return null;
  }

  @Override
  public void scanNotNull(Object object, SerializationContext context) {
    Codex<Object> actual = typeContext.getCodex(object.getClass());
    if (actual == this) {
      context.fail(new UnsupportedOperationException(object.getClass().getName()));
    }
    actual.scan(object, context);
  }

  @Override
  public void writeNotNull(Object object, SerializationContext context) throws IOException {
    Codex<Object> actual = typeContext.getCodex(object.getClass());
    actual.write(object, context);
  }

  @Inject
  void inject(TypeContext typeContext, Provider<ListCodex<Object>> listCodex,
      Provider<StringMapCodex<Object>> mapCodex) {
    this.listCodex = listCodex;
    this.mapCodex = mapCodex;
    this.typeContext = typeContext;
  }
}