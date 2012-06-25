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

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonElement;

public class NumberCodex<N extends Number> extends ValueCodex<N> {
  private final Class<N> clazz;

  public NumberCodex(Class<N> clazz) {
    this.clazz = clazz;
  }

  @Override
  public Type describe(TypeContext context) {
    if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
      return new Type.Builder().withJsonKind(JsonKind.DOUBLE).build();

    } else {
      return new Type.Builder().withJsonKind(JsonKind.INTEGER).build();
    }
  }

  /**
   * Returns {@code true} for {@code null} or {@code 0} values.
   */
  @Override
  public boolean isDefaultValue(N value) {
    if (value == null) {
      return true;
    }
    if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
      return value.doubleValue() == 0.0;
    }
    return value.intValue() == 0;
  }

  @Override
  public N readNotNull(JsonElement element, DeserializationContext context) {
    Object toReturn;
    if (Byte.class.equals(clazz)) {
      toReturn = element.getAsByte();
    } else if (Double.class.equals(clazz)) {
      toReturn = element.getAsDouble();
    } else if (Float.class.equals(clazz)) {
      toReturn = element.getAsFloat();
    } else if (Integer.class.equals(clazz)) {
      toReturn = element.getAsInt();
    } else if (Long.class.equals(clazz)) {
      toReturn = element.getAsLong();
    } else if (Short.class.equals(clazz)) {
      toReturn = element.getAsShort();
    } else {
      throw new UnsupportedOperationException("Unimplemented Number type " + clazz.getName());
    }
    return clazz.cast(toReturn);
  }

  @Override
  public void writeNotNull(N object, SerializationContext context) throws IOException {
    context.getWriter().value(object);
  }
}