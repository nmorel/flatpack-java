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

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonPrimitive;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Providers;

public class DateCodexTest extends FlatPackTest {

  @Inject
  private Injector injector;
  @Inject
  private TypeLiteral<java.sql.Date> javaSqlDate;
  @Inject
  private TypeLiteral<java.sql.Time> javaSqlTime;
  @Inject
  private TypeLiteral<java.sql.Timestamp> javaSqlTimestamp;
  @Inject
  private TypeLiteral<java.util.Date> javaUtilDate;
  @Inject
  private TypeContext typeContext;

  private final long now = System.currentTimeMillis();

  @Test
  public void testJavaSqlDate() throws Exception {
    test(javaSqlDate);
  }

  @Test
  public void testJavaSqlTime() throws Exception {
    test(javaSqlTime);
  }

  @Test
  public void testJavaSqlTimestamp() throws Exception {
    test(javaSqlTimestamp);
  }

  @Test
  public void testJavaUtilDate() throws Exception {
    test(javaUtilDate);
  }

  private <D extends java.util.Date> void test(TypeLiteral<D> type) throws Exception {
    test(type, 0);
    test(type, now);
  }

  private <D extends java.util.Date> void test(TypeLiteral<D> type, long instant) throws Exception {
    @SuppressWarnings("unchecked")
    D date = (D) type.getRawType().getConstructor(long.class).newInstance(instant);

    @SuppressWarnings("unchecked")
    Codex<D> codex = (Codex<D>) typeContext.getCodex(type.getType());
    DeserializationContext ctx = deserializationContext();
    Object read = codex.readNotNull(new JsonPrimitive(instant), ctx);
    closeContext();

    assertEquals(date, read);

    D returned = super.testCodex(Providers.of(codex), date, new HashSet<HasUuid>());
    assertEquals(date, returned);
  }

}
