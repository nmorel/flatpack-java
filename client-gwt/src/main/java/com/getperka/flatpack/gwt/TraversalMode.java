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
package com.getperka.flatpack.gwt;

/**
 * Controls how the entities included in a {@link FlatPackEntity} are scanned to find additional
 * entites to include in the payload.
 */
public enum TraversalMode {
  /**
   * All object properties, even those annotated with {@link Implies} or
   * {@link javax.persistence.OneToMany}, will be included in the payload. It should be enabled for
   * "description" endpoints where the complete relationships of an object are desired.
   */
  DEEP(false, true),
  /**
   * The default mode, which does not include properties annotated with {@link Implies} or
   * {@link javax.persistence.OneToMany} to reduce payload size.
   */
  SIMPLE(false, false),
  /**
   * Like {@link #SIMPLE}, but only objects explicitly added to the entity via
   * {@link FlatPackEntity#addExtraEntity(HasUuid)} will be included in the payload.
   */
  SPARSE(true, false);

  private final boolean sparse;
  private final boolean writeAllProperties;

  private TraversalMode(boolean sparse, boolean writeAllProperties) {
    this.sparse = sparse;
    this.writeAllProperties = writeAllProperties;
  }

  /**
   * Returns {@code true} if the normal entity-scanning behavior should be disabled.
   */
  public boolean isSparse() {
    return sparse;
  }

  /**
   * Returns {@code true} if all properties of an entity should be written into the payload.
   */
  public boolean writeAllProperties() {
    return writeAllProperties;
  }
}