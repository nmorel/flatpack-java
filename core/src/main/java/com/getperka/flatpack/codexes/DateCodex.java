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

import java.lang.reflect.Constructor;
import java.util.Date;

import javax.inject.Inject;

import org.joda.time.format.ISODateTimeFormat;

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.inject.TypeLiteral;

/**
 * Supports {@code java.util.Date} and its ilk.
 * <p>
 * Dates are always written as an ISO8601 time. Dates may be read as a number of milliseconds since
 * the epoch or something approximating an ISO8601 date-time string.
 * 
 * @param <D> the concrete type of Date to instantiate.
 */
public class DateCodex<D extends Date> extends ValueCodex<D> {
  private final Constructor<D> constructor;

  @Inject
  @SuppressWarnings("unchecked")
  DateCodex(TypeLiteral<D> dateType) {
    try {
      this.constructor = (Constructor<D>) dateType.getRawType().getConstructor(long.class);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Should not use DateCodex with a " + dateType);
    }
  }

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.STRING)
        .withTypeHint(TypeHint.create(constructor.getDeclaringClass()))
        .build();
  }

  @Override
  public D readNotNull(JsonElement element, DeserializationContext context) throws Exception {
    if (element.isJsonPrimitive()) {
      long instant;
      JsonPrimitive primitive = element.getAsJsonPrimitive();
      if (primitive.isNumber()) {
        instant = primitive.getAsLong();
      } else {
        instant = ISODateTimeFormat.dateTimeParser().parseMillis(primitive.getAsString());
      }
      return constructor.newInstance(instant);
    }
    throw new IllegalArgumentException("Could not parse " + element.toString() + " as a date value");
  }

  @Override
  public void writeNotNull(D object, SerializationContext context) throws Exception {
    String value = ISODateTimeFormat.dateTime().print(object.getTime());
    context.getWriter().value(value);
  }
}
