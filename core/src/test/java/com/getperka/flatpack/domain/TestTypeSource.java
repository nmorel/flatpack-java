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

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.getperka.flatpack.TypeSource;

public class TestTypeSource implements TypeSource {
  private final Set<Class<?>> types;

  public TestTypeSource() {
    types = new LinkedHashSet<Class<?>>(Arrays.<Class<?>> asList(
        Employee.class, Manager.class, Person.class));
  }

  @Override
  public Set<Class<?>> getTypes() {
    return types;
  }

}
