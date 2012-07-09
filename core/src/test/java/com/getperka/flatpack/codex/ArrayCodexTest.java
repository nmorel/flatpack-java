package com.getperka.flatpack.codex;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.ArrayCodex;
import com.google.inject.TypeLiteral;

public class ArrayCodexTest extends FlatPackTest {

  private static final TypeLiteral<ArrayCodex<String>> STRING =
      new TypeLiteral<ArrayCodex<String>>() {};

  @Test
  public void test() {
    String[] in = { "Hello", " ", "", null, "World!" };
    String[] out = testCodex(STRING, in);

    assertArrayEquals(in, out);
  }

  @Test
  public void testNull() {
    assertNull(testCodex(STRING, null));
  }
}
