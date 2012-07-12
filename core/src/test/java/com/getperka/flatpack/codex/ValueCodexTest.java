package com.getperka.flatpack.codex;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.BooleanCodex;
import com.getperka.flatpack.codexes.DateTimeZoneCodex;
import com.getperka.flatpack.codexes.EnumCodex;
import com.getperka.flatpack.codexes.HasUuidClassCodex;
import com.getperka.flatpack.codexes.JsonElementCodex;
import com.getperka.flatpack.codexes.NumberCodex;
import com.getperka.flatpack.codexes.ToStringCodex;
import com.getperka.flatpack.codexes.TypeHintCodex;
import com.getperka.flatpack.codexes.VoidCodex;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonPrimitive;
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
  private TypeLiteral<BooleanCodex> booleanCodex;
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
}
