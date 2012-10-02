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
import java.math.BigInteger;

import javax.inject.Inject;

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonElement;
import com.google.inject.TypeLiteral;

/**
 * Primitive numberic support.
 *
 * @param <N> the boxed Number type
 */
public class NumberCodex<N extends Number> extends ValueCodex<N> {
  private Class<N> clazz;

  @Inject
  protected NumberCodex() {}

  @Override
  public Type describe() {
    Type.Builder builder = new Type.Builder();
    if (BigDecimal.class.equals(clazz) || BigInteger.class.equals(clazz)) {
      // Accepts strings or numbers
      builder.withJsonKind(JsonKind.ANY);
    } else if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
      builder.withJsonKind(JsonKind.DOUBLE);
    } else {
      builder.withJsonKind(JsonKind.INTEGER);
    }
    return builder.withTypeHint(TypeHint.create(clazz)).build();
  }

  /**
   * Returns {@code true} for {@code null} or {@code 0} values.
   */
  @Override
  public boolean isDefaultValue(N value) {
    if (value == null) {
      return true;
    }
    if (BigDecimal.class.equals(clazz)) {
      return BigDecimal.ZERO.compareTo((BigDecimal) value) == 0;
    }
    if (BigInteger.class.equals(clazz)) {
      return BigInteger.ZERO.compareTo((BigInteger) value) == 0;
    }
    if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
      return value.doubleValue() == 0.0;
    }
    return value.intValue() == 0;
  }

  @Override
  public N readNotNull(JsonElement element, DeserializationContext context) {
    Object toReturn;
    if (BigDecimal.class.equals(clazz)) {
      toReturn = element.getAsBigDecimal();
    } else if (BigInteger.class.equals(clazz)) {
      toReturn = element.getAsBigInteger();
    } else if (Byte.class.equals(clazz)) {
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
    if (BigDecimal.class.equals(clazz) || BigInteger.class.equals(clazz)) {
      context.getWriter().value(object.toString());
    }else{
      context.getWriter().value(object);
    }
  }

  @Inject
  @SuppressWarnings("unchecked")
  void inject(TypeLiteral<N> type) {
    this.clazz = (Class<N>) type.getRawType();
  }
}