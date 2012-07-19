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
package com.getperka.flatpack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.Manager;
import com.getperka.flatpack.domain.TestTypeSource;

/**
 * Test full encoding of {@link Packer} and {@link Unpacker}.
 */
public class PackTest extends FlatPackTest {

  @Test
  public void test() throws IOException {
    Employee employee = makeEmployee();
    Employee employee2 = deepPack(Employee.class, employee);

    check(employee, employee2);
  }

  @Test
  public void testImpliedProperties() throws IOException {
    Manager manager = makeManager();
    Employee employeeA = makeEmployee();
    Employee employeeB = makeEmployee();

    // A -> manager -> B
    employeeA.setManager(manager);
    manager.getEmployees().add(employeeB);

    Employee employeeA2 = deepPack(Employee.class, employeeA);
    assertEquals(2, employeeA2.getManager().getEmployees().size());
    for (Employee toTest : employeeA2.getManager().getEmployees()) {
      if (employeeA.equals(toTest)) {
        check(employeeA, toTest);
      } else if (employeeB.equals(toTest)) {
        check(employeeB, toTest);
      } else {
        fail("Unmatched employee");
      }
    }
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration().addTypeSource(new TestTypeSource());
  }
}
