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

import static com.getperka.flatpack.util.FlatPackTypes.decapitalize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.google.gson.JsonElement;
import com.google.inject.TypeLiteral;

/**
 * Enum support.
 * 
 * @param <E> the type of enum
 */
public class EnumCodex<E extends Enum<E>> extends ValueCodex<E> {
  private Class<E> clazz;

  protected EnumCodex() {}

  @Override
  public Type describe() {
    List<String> values = new ArrayList<String>();
    for (Enum<?> value : clazz.getEnumConstants()) {
      values.add(value.name());
    }
    return new Type.Builder()
        .withJsonKind(JsonKind.STRING)
        .withEnumValues(values)
        .withName(decapitalize(clazz.getSimpleName()))
        .build();
  }

  @Override
  public E readNotNull(JsonElement element, DeserializationContext context) {
    return Enum.valueOf(clazz, element.getAsString());
  }

  @Override
  public void writeNotNull(E object, SerializationContext context) throws IOException {
    context.getWriter().value(object.name());
  }

  @Inject
  @SuppressWarnings("unchecked")
  void inject(TypeLiteral<E> type) {
    clazz = (Class<E>) type.getRawType();
  }
}