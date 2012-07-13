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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.codexes.EntityCodex;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.SerializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class EntityCodexTest extends FlatPackTest {

  @Inject
  private EntityCodex<Employee> employeeCodex;

  @Test
  public void testReadWriteProperties() {
    assertEquals("employee", employeeCodex.describe().getName());

    Employee e1 = makeEmployee();

    StringWriter out = new StringWriter();
    SerializationContext ctx = serializationContext(out);
    try {
      employeeCodex.writeProperties(e1, ctx);
    } finally {
      closeContext();
    }

    JsonObject obj = new JsonParser().parse(out.toString()).getAsJsonObject();
    // Check embedded properties
    assertFalse(obj.has("address"));
    assertTrue(obj.has("street"));

    Employee e2;
    DeserializationContext d = deserializationContext();
    try {
      e2 = employeeCodex.allocate(obj, d);
      employeeCodex.readProperties(e2, obj, d);

      // Check referential integrity
      assertSame(e2, employeeCodex.read(new JsonPrimitive(e2.getUuid().toString()), d));
    } finally {
      d.runPostWork();
      closeContext();
    }

    // Verify callbacks called
    assertTrue(e2.employeePostUnpack);
    assertTrue(e2.employeePreUnpack);
    assertTrue(e2.employeePre1Unpack);

    check(e1, e2);
  }
}
