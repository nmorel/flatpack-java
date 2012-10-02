package com.getperka.flatpack.codex;
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

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.NumberCodex;
import com.google.inject.TypeLiteral;

public class NumberCodexText extends FlatPackTest {

  @Inject
  private TypeLiteral<NumberCodex<BigDecimal>> bigDecimal;
  @Inject
  private TypeLiteral<NumberCodex<BigInteger>> bigInteger;

  @Test
  public void testBigDecimal() {
    BigDecimal value = BigDecimal.valueOf(Double.MAX_VALUE).multiply(BigDecimal.valueOf(1.5));
    testCodex(bigDecimal, value);
  }

  @Test
  public void testBigInteger() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(2));
    testCodex(bigInteger, value);
  }
}
