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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.codexes.DynamicCodex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.inject.TypeLiteral;

public class DynamicCodexTest extends FlatPackTest {

  @Inject
  DynamicCodex codex;

  @Inject
  TypeContext typeContext;

  @Test
  public void testList() {
    List<Object> data = new ArrayList<Object>(Arrays.<Object> asList(false, null, "Hello World!"));
    assertEquals(data, testCodex(TypeLiteral.get(DynamicCodex.class), data));
  }

  @Test
  public void testMap() throws Exception {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("true", true);
    data.put("false", false);
    data.put("string", "Hello World!");

    JsonObject obj = new JsonObject();
    obj.addProperty("true", true);
    obj.addProperty("false", false);
    obj.addProperty("string", "Hello World!");

    DeserializationContext d = deserializationContext();
    assertEquals(data, codex.readNotNull(obj, d));
    assertEquals(data, typeContext.getCodex(Map.class).readNotNull(obj, d));
  }

  @Test
  public void testPrimitives() {
    DeserializationContext ctx = deserializationContext();

    assertTrue((Boolean) codex.read(new JsonPrimitive(true), ctx));
    assertEquals(BigDecimal.valueOf(42), codex.read(new JsonPrimitive(42), ctx));
    assertEquals(BigDecimal.valueOf(42.2), codex.read(new JsonPrimitive(42.2), ctx));
    assertEquals("Hello World!", codex.read(new JsonPrimitive("Hello World!"), ctx));

    UUID uuid = UUID.randomUUID();
    assertEquals(uuid.toString(), codex.read(new JsonPrimitive(uuid.toString()), ctx));

    HasUuid entity = new BaseHasUuid();
    ctx.putEntity(uuid, entity, true);
    assertSame(entity, codex.read(new JsonPrimitive(uuid.toString()), ctx));
  }
}
