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
package com.getperka.flatpack.codex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.BooleanCodex;
import com.getperka.flatpack.codexes.CharacterCodex;
import com.getperka.flatpack.codexes.DateTimeZoneCodex;
import com.getperka.flatpack.codexes.EnumCodex;
import com.getperka.flatpack.codexes.HasUuidClassCodex;
import com.getperka.flatpack.codexes.JsonElementCodex;
import com.getperka.flatpack.codexes.NumberCodex;
import com.getperka.flatpack.codexes.ToStringCodex;
import com.getperka.flatpack.codexes.TypeHintCodex;
import com.getperka.flatpack.codexes.VoidCodex;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.TestTypeSource;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonPrimitive;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;

/**
 * Test serializing basic data types.
 */
public class ValueCodexTest extends FlatPackTest {
  enum MyEnum {
    A, B
  }

  @Inject
  private Injector injector;

  @Inject
  private TypeLiteral<BooleanCodex> booleanCodex;
  @Inject
  private TypeLiteral<CharacterCodex> charCodex;
  @Inject
  private TypeLiteral<DateTimeZoneCodex> dateTzCodex;
  @Inject
  private TypeLiteral<EnumCodex<MyEnum>> enumCodex;
  @Inject
  private TypeLiteral<HasUuidClassCodex> classCodex;
  @Inject
  private TypeLiteral<JsonElementCodex> jsonElementCodex;
  @Inject
  private TypeLiteral<NumberCodex<Double>> numberDoubleCodex;
  @Inject
  private TypeLiteral<NumberCodex<Float>> numberFloatCodex;
  @Inject
  private TypeLiteral<NumberCodex<Integer>> numberIntegerCodex;
  @Inject
  private TypeLiteral<ToStringCodex<Object>> toStringBadCodex;
  @Inject
  private TypeLiteral<ToStringCodex<BigDecimal>> toStringStringCodex;
  @Inject
  private TypeLiteral<ToStringCodex<DateTime>> toStringObjectCodex;
  @Inject
  private TypeLiteral<TypeHintCodex> typeHintCodex;
  @Inject
  private TypeLiteral<VoidCodex> voidCodex;

  @Test
  public void testBoolean() {
    testCodex(booleanCodex, true);
    testCodex(booleanCodex, false);
    testCodex(booleanCodex, null);
  }

  @Test
  public void testChar() {
    testCodex(charCodex, 'c');

    // Try number -> character conversion
    JsonPrimitive p = new JsonPrimitive((int) 'c');
    DeserializationContext ctx = deserializationContext();
    assertEquals('c', injector.getInstance(Key.get(charCodex)).read(p, ctx).charValue());
    closeContext();

    testCodex(charCodex, '\0');
    testCodex(charCodex, '\uffff');
    testCodex(charCodex, null);
  }

  @Test
  public void testDateTzCodex() {
    testCodex(dateTzCodex, DateTimeZone.UTC);
    testCodex(dateTzCodex, null);
  }

  @Test
  public void testEnum() {
    testCodex(enumCodex, MyEnum.A);
    testCodex(enumCodex, MyEnum.B);
    testCodex(enumCodex, null);
  }

  @Test
  public void testHasUuidClass() {
    testCodex(classCodex, Employee.class);
  }

  @Test
  public void testJsonElementCodex() {
    testCodex(jsonElementCodex, new JsonPrimitive("Hello world!"));
  }

  @Test
  public void testNumber() {
    testCodex(numberDoubleCodex, 42d);
    testCodex(numberDoubleCodex, 42.2);
    testCodex(numberFloatCodex, 42f);
    testCodex(numberFloatCodex, 42.2f);
    testCodex(numberIntegerCodex, 42);
  }

  @Test
  public void testToString() {
    try {
      testCodex(toStringBadCodex, false);
      fail();
    } catch (ProvisionException expected) {}
    testCodex(toStringStringCodex, BigDecimal.valueOf(42.2));
    testCodex(toStringObjectCodex, DateTime.now());
  }

  @Test
  public void testTypeHint() {
    testCodex(typeHintCodex, TypeHint.create(getClass()));
  }

  @Test
  public void testVoid() {
    testCodex(voidCodex, null);
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration().addTypeSource(new TestTypeSource());
  }

  /**
   * Adds a simple equality or nullity test to the end of a serialization / deserialization pass.
   */
  @Override
  protected <T> T testCodex(TypeLiteral<? extends Codex<T>> codexType, T value) {
    T returned = super.testCodex(codexType, value);
    if (value == null) {
      assertNull(returned);
    } else {
      assertEquals(value, returned);
    }
    return returned;
  }
}
