package com.getperka.flatpack.codex;

import javax.inject.Inject;

import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.BooleanCodex;
import com.getperka.flatpack.codexes.DateTimeZoneCodex;
import com.getperka.flatpack.codexes.EnumCodex;
import com.getperka.flatpack.codexes.NumberCodex;
import com.google.inject.TypeLiteral;

/**
 * Test serializing basic data types.
 */
public class ValueCodexTest extends FlatPackTest {
  enum MyEnum {
    A, B
  }

  @Inject
  private TypeLiteral<BooleanCodex> booleanCodex;
  @Inject
  private TypeLiteral<DateTimeZoneCodex> dateTzCodex;
  @Inject
  private TypeLiteral<EnumCodex<MyEnum>> enumCodex;
  @Inject
  private TypeLiteral<NumberCodex<Double>> numberDoubleCodex;
  @Inject
  private TypeLiteral<NumberCodex<Float>> numberFloatCodex;
  @Inject
  private TypeLiteral<NumberCodex<Integer>> numberIntegerCodex;

  @Test
  public void testBoolean() {
    testCodex(booleanCodex, true);
    testCodex(booleanCodex, false);
    testCodex(booleanCodex, null);
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
  public void testNumber() {
    testCodex(numberDoubleCodex, 42d);
    testCodex(numberDoubleCodex, 42.2);
    testCodex(numberFloatCodex, 42f);
    testCodex(numberFloatCodex, 42.2f);
    testCodex(numberIntegerCodex, 42);
  }
}
