package com.getperka.flatpack.codexes;

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

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Converts chars to JSON strings. Numeric values will be treated as a BMP code point.
 */
public class CharacterCodex extends ValueCodex<Character> {

  CharacterCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.STRING)
        .withTypeHint(TypeHint.create(Character.class))
        .build();
  }

  @Override
  public Character readNotNull(JsonElement element, DeserializationContext context)
      throws Exception {
    if (element.isJsonPrimitive()) {
      JsonPrimitive primitive = element.getAsJsonPrimitive();
      // Treat a number as a BMP code point
      if (primitive.isNumber()) {
        return (char) primitive.getAsInt();
      } else {
        return primitive.getAsCharacter();
      }
    }
    throw new IllegalArgumentException("Cannot convert " + element.toString() + " to a char");
  }

  @Override
  public void writeNotNull(Character object, SerializationContext context) throws Exception {
    context.getWriter().value(object.toString());
  }
}
