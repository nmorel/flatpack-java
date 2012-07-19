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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.joda.time.DateTime;

import com.getperka.flatpack.util.FlatPackCollections;
import com.getperka.flatpack.util.FlatPackTypes;
import com.google.inject.TypeLiteral;

/**
 * Encapsulates a return value, the role(s) to encode the entity for, and an optional collection of
 * extra entities to include in the payload.
 * 
 * @param <T> the type of value being returned
 */
public class FlatPackEntity<T> extends TypeReference<T> {
  /**
   * A convenience method to create a FlatPackEntity for a List of entities.
   * 
   * @param <T> the type of entity contained in the list.
   * @param clazz the entity type
   */
  public static <T extends HasUuid> FlatPackEntity<Collection<? extends T>> collectionOf(
      final Class<T> clazz) {
    return new FlatPackEntity<Collection<? extends T>>(new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        return new Type[] { clazz };
      }

      @Override
      public Type getOwnerType() {
        return null;
      }

      @Override
      public Type getRawType() {
        return Collection.class;
      }
    });
  }

  /**
   * A factory method to create a fully-specified FlatPackEntity.
   */
  public static FlatPackEntity<?> create(Type returnType, Object toReturn, Principal principal) {
    // Convert primitive types to boxed
    Class<?> erased = FlatPackTypes.erase(returnType);
    if (erased.isPrimitive()) {
      returnType = FlatPackTypes.box(erased);
    }
    return new FlatPackEntity<Object>(returnType).withPrincipal(principal).withValue(toReturn);
  }

  /**
   * A convenience method to create a FlatPackEntity that embeds a single entity as the return
   * value. This method is null-safe.
   */
  public static <T extends HasUuid> FlatPackEntity<T> entity(T toReturn) {
    return new FlatPackEntity<T>(toReturn == null ? HasUuid.class : toReturn.getClass())
        .withValue(toReturn);
  }

  /**
   * A convenience method to create a FlatPackEntity for a Map of entities to entities.
   * 
   * @param <K> the type of entity for the map keys
   * @param <V> the type of entity for the map values
   * @param key the key entity type
   * @param value the value entity type
   */
  public static <K extends HasUuid, V extends HasUuid> FlatPackEntity<Map<? extends K, ? extends V>> mapOf(
      final Class<K> key, final Class<V> value) {
    return new FlatPackEntity<Map<? extends K, ? extends V>>(new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        return new Type[] { key, value };
      }

      @Override
      public Type getOwnerType() {
        return null;
      }

      @Override
      public Type getRawType() {
        return Map.class;
      }
    });
  }

  /**
   * Convenience method to return a FlatPackEntity that represents a null value. It is permissible
   * to call the extra decoration methods on the value returned from this method.
   */
  public static FlatPackEntity<Void> nullResponse() {
    return new FlatPackEntity<Void>(Void.class);
  }

  /**
   * A convenience method to create a FlatPackEntity for a Map of strings to entities.
   * 
   * @param <V> the type of entity for the map values
   * @param value the value entity type
   */
  public static <V extends HasUuid> FlatPackEntity<Map<String, ? extends V>> stringMapOf(
      final Class<V> value) {
    return new FlatPackEntity<Map<String, ? extends V>>(new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        return new Type[] { String.class, value };
      }

      @Override
      public Type getOwnerType() {
        return null;
      }

      @Override
      public Type getRawType() {
        return Map.class;
      }
    });
  }

  private DateTime lastModifiedTime;
  private Map<String, String> extraData;
  private Set<HasUuid> extraEntities;
  private Map<String, String> extraErrors;
  private Map<String, String> extraWarnings;
  private List<EntityMetadata> metadata;
  private Principal principal;
  private TraversalMode traversalMode = TraversalMode.SIMPLE;
  private T value;
  private Set<ConstraintViolation<?>> violations;

  /**
   * Provide type information from implicit parameterization.
   */
  protected FlatPackEntity() {}

  /**
   * Allow construction via injection.
   */
  @Inject
  FlatPackEntity(TypeLiteral<T> type) {
    super(type.getType());
  }

  /**
   * Only used by create methods.
   */
  private FlatPackEntity(Type type) {
    super(type);
  }

  /**
   * Populate the {@code errors} segment of the payload from {@link ConstraintViolation}.
   */
  public FlatPackEntity<T> addConstraintViolations(Set<? extends ConstraintViolation<?>> violations) {
    if (this.violations == null) {
      this.violations = FlatPackCollections.setForIteration();
    }
    this.violations.addAll(violations);
    return this;
  }

  /**
   * Add a key-value pair to the {@code errors} segment of the payload.
   */
  public FlatPackEntity<T> addError(String key, String message) {
    if (extraErrors == null) {
      extraErrors = FlatPackCollections.mapForIteration();
    }
    extraErrors.put(key, message);
    return this;
  }

  /**
   * Add entities not normally reachable from {@link #getValue()} into the payload.
   */
  public FlatPackEntity<T> addExtraEntities(Collection<? extends HasUuid> entities) {
    if (extraEntities == null) {
      extraEntities = FlatPackCollections.setForIteration();
    }
    extraEntities.addAll(entities);
    return this;
  }

  /**
   * Add an entity not normally reachable from {@link #getValue()} into the payload.
   */
  public FlatPackEntity<T> addExtraEntity(HasUuid entity) {
    if (extraEntities == null) {
      extraEntities = FlatPackCollections.setForIteration();
    }
    extraEntities.add(entity);
    return this;
  }

  /**
   * Add a key-value pair to the {@code warnings} segment of the payload.
   */
  public FlatPackEntity<T> addWarning(String key, String message) {
    if (extraWarnings == null) {
      extraWarnings = FlatPackCollections.mapForIteration();
    }
    extraWarnings.put(key, message);
    return this;
  }

  /**
   * Returns an immutable view of the ConstraintViolations that have been added to the
   * FlatPackEntity.
   */
  public Set<ConstraintViolation<?>> getConstraintViolations() {
    return violations == null ? Collections.<ConstraintViolation<?>> emptySet() :
        Collections.unmodifiableSet(violations);
  }

  /**
   * Returns an immutable view of the extra side-channel entries to include at the root of the
   * payload.
   */
  public Map<String, String> getExtraData() {
    return extraData == null ? Collections.<String, String> emptyMap() :
        Collections.unmodifiableMap(extraData);
  }

  /**
   * Returns an immutable view of the extra entities to include in the returned payload.
   */
  public Set<HasUuid> getExtraEntities() {
    return extraEntities == null ? Collections.<HasUuid> emptySet() :
        Collections.unmodifiableSet(extraEntities);
  }

  /**
   * Returns an immutable view of the extra errors to include in the returned payload.
   */
  public Map<String, String> getExtraErrors() {
    return extraErrors == null ? Collections.<String, String> emptyMap() :
        Collections.unmodifiableMap(extraErrors);
  }

  /**
   * Returns an immutable view of the extra warnings to include in the returned payload.
   */
  public Map<String, String> getExtraWarnings() {
    return extraWarnings == null ? Collections.<String, String> emptyMap() :
        Collections.unmodifiableMap(extraWarnings);
  }

  /**
   * Clients with some amount of temporary storage may request that entities that were created or
   * last modified before a particular instant in time be excluded from the data section.
   */
  public DateTime getLastModifiedTime() {
    return lastModifiedTime;
  }

  public Principal getPrincipal() {
    return principal;
  }

  public TraversalMode getTraversalMode() {
    return traversalMode;
  }

  /**
   * Returns the data previous passed to {@link #withValue(Object)}.
   */
  public T getValue() {
    return value;
  }

  /**
   * Allows extra string data to be returned at the top level of the payload.
   */
  public void putExtraData(String key, String value) {
    if (extraData == null) {
      extraData = new TreeMap<String, String>();
    }
    extraData.put(key, value);
  }

  public FlatPackEntity<T> withLastModifiedTime(DateTime lastModified) {
    this.lastModifiedTime = lastModified;
    return this;
  }

  public FlatPackEntity<T> withPrincipal(Principal principal) {
    this.principal = principal;
    return this;
  }

  public FlatPackEntity<T> withTraversalMode(TraversalMode traversalMode) {
    this.traversalMode = traversalMode;
    return this;
  }

  public FlatPackEntity<T> withValue(T value) {
    // Sanity-check type assignments
    if (value != null && !FlatPackTypes.erase(getType()).isInstance(value)) {
      throw new IllegalArgumentException(value.getClass().getName() + " is not assignable to "
        + getType());
    }
    this.value = value;
    return this;
  }

  FlatPackEntity<T> addMetadata(EntityMetadata meta) {
    if (metadata == null) {
      metadata = FlatPackCollections.listForAny();
    }
    metadata.add(meta);
    return this;
  }

  List<EntityMetadata> getMetadata() {
    return metadata == null ? Collections.<EntityMetadata> emptyList() :
        Collections.unmodifiableList(metadata);
  }
}
