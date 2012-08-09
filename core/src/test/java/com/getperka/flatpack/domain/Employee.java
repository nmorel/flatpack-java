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
package com.getperka.flatpack.domain;

import com.getperka.flatpack.PostUnpack;
import com.getperka.flatpack.PreUnpack;
import com.google.gson.JsonObject;

public class Employee extends Person {
  private int employeeNumber;
  public boolean employeePreUnpack;
  public boolean employeePre1Unpack;
  public boolean employeePostUnpack;
  public String writeOnlyProperty;
  private Manager manager;

  Employee() {}

  public int getEmployeeNumber() {
    return employeeNumber;
  }

  public Manager getManager() {
    return manager;
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  public void setWriteOnlyProperty(String value) {
    writeOnlyProperty = value;
  }

  @PostUnpack
  void employeePostUnpack() {
    employeePostUnpack = true;
  }

  @PreUnpack
  void employeePreUnpack() {
    employeePreUnpack = true;
  }

  @PreUnpack
  void employeePreUnpack(JsonObject obj) {
    employeePre1Unpack = true;
  }
}
