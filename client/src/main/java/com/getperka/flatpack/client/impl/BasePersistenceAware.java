/*
 * #%L
 * FlatPack Client
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
package com.getperka.flatpack.client.impl;

import java.util.Collections;
import java.util.Set;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.PostUnpack;
import com.getperka.flatpack.collections.DirtyFlag;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * A base type for generated model types.
 */
public class BasePersistenceAware extends BaseHasUuid implements PersistenceAware {
  private final Set<String> dirtyProperties = FlatPackCollections.setForIteration();
  private boolean persistent;

  @Override
  public Set<String> dirtyPropertyNames() {
    return Collections.unmodifiableSet(dirtyProperties);
  }

  @Override
  public void markPersistent() {
    persistent = true;
    dirtyProperties.clear();
  }

  @Override
  public boolean wasPersistent() {
    return persistent;
  }

  protected DirtyFlag dirtyFlag(String propertyName) {
    return new DirtyFlag(dirtyProperties, propertyName);
  }

  @PostUnpack
  void postUnpack() {
    dirtyProperties.clear();
  }
}
