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
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.codexes.EntityMapCodex;
import com.getperka.flatpack.codexes.StringMapCodex;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.TestTypeSource;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.inject.TypeLiteral;

public class MapCodexTest extends FlatPackTest {

  @Inject
  private TypeLiteral<StringMapCodex<String>> stringString;
  @Inject
  private TypeLiteral<EntityMapCodex<Employee, String>> employeeString;

  @Test
  public void testEntities() {
    Employee e = makeEmployee();
    Map<Employee, String> map = Collections.singletonMap(e, e.getUuid().toString());

    Set<HasUuid> scanned = FlatPackCollections.setForIteration();
    Map<Employee, String> map2 = testCodex(employeeString, map, scanned);

    assertEquals(map, map2);
    assertTrue(scanned.contains(e));
  }

  @Test
  public void testStrings() {
    Map<String, String> map = Collections.singletonMap("Hello", "World!");
    Map<String, String> map2 = testCodex(stringString, map);
    assertEquals(map, map2);
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration().addTypeSource(new TestTypeSource());
  }
}
