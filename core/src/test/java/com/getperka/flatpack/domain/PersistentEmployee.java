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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.getperka.flatpack.PersistenceAware;

public class PersistentEmployee extends Employee implements PersistenceAware {
  private Set<String> dirtyPropertyNames = new HashSet<String>();
  private boolean persistent;
  private String trackedProperty;

  PersistentEmployee() {}

  @Override
  public Set<String> dirtyPropertyNames() {
    return Collections.unmodifiableSet(dirtyPropertyNames);
  }

  public String getTrackedProperty() {
    return trackedProperty;
  }

  @Override
  public void markPersistent() {
    persistent = true;
    dirtyPropertyNames.clear();
  }

  public void setTrackedProperty(String trackedProperty) {
    dirtyPropertyNames.add("trackedProperty");
    this.trackedProperty = trackedProperty;
  }

  @Override
  public boolean wasPersistent() {
    return persistent;
  }

}