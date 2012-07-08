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
package com.getperka.flatpack.ext;

import static com.getperka.flatpack.util.FlatPackCollections.listForAny;
import static com.getperka.flatpack.util.FlatPackCollections.mapForIteration;
import static com.getperka.flatpack.util.FlatPackCollections.mapForLookup;
import static com.getperka.flatpack.util.FlatPackCollections.sortedMapForIteration;
import static com.getperka.flatpack.util.FlatPackTypes.decapitalize;
import static com.getperka.flatpack.util.FlatPackTypes.erase;
import static com.getperka.flatpack.util.FlatPackTypes.getSingleParameterization;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.InheritPrincipal;
import com.getperka.flatpack.JsonProperty;
import com.getperka.flatpack.JsonTypeName;
import com.getperka.flatpack.codexes.DynamicCodex;
import com.getperka.flatpack.inject.AllTypes;
import com.getperka.flatpack.util.FlatPackTypes;

/**
 * Provides access to typesystem information and vends helper objects.
 * <p>
 * Instances of TypeContext are thread-safe and intended to be long-lived.
 */
public class TypeContext {

  /**
   * Extract the Java bean property name from a method. Note that this does not take any
   * {@link JsonProperty} annotations into account, Getters and setters must be collated by the Java
   * method names since setters aren't generally annotated.
   */
  private static String beanPropertyName(Method m) {
    String name = m.getName();
    if (name.startsWith("is")) {
      name = name.substring(2);
    } else {
      name = name.substring(3);
    }
    return decapitalize(name);
  }

  private static boolean isBoolean(Class<?> clazz) {
    return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
  }

  /**
   * Returns {@code true} for:
   * <ul>
   * <li>public Foo getFoo()</li>
   * <li>public boolean isFoo()</li>
   * <li>{@code @PermitAll} &lt;any modifier&gt; Foo getFoo()</li>
   * <li>{@code @RolesAllowed} &lt;any modifier&gt; Foo getFoo()</li>
   * </ul>
   * Ignores any method declared annotated with {@link Transient} unless {@link PermitAll} or
   * {@link RolesAllowed} is present.
   */
  private static boolean isGetter(Method m) {
    if (m.getParameterTypes().length != 0) {
      return false;
    }
    String name = m.getName();
    if (name.startsWith("get") && name.length() > 3 ||
      name.startsWith("is") && name.length() > 2 && isBoolean(m.getReturnType())) {

      if (m.isAnnotationPresent(DenyAll.class)) {
        return false;
      }
      if (m.isAnnotationPresent(PermitAll.class)) {
        return true;
      }
      if (m.isAnnotationPresent(RolesAllowed.class)) {
        return true;
      }
      if (m.isAnnotationPresent(Transient.class)) {
        return false;
      }
      if (Modifier.isPublic(m.getModifiers())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Analogous to {@link #isGetter(Method)}.
   */
  private static boolean isSetter(Method m) {
    if (m.isAnnotationPresent(DenyAll.class)) {
      return false;
    }
    if (m.getParameterTypes().length != 1) {
      return false;
    }
    if (!m.getName().startsWith("set")) {
      return false;
    }
    if (m.isAnnotationPresent(PermitAll.class)) {
      return true;
    }
    if (m.isAnnotationPresent(RolesAllowed.class)) {
      return true;
    }
    return !Modifier.isPrivate(m.getModifiers());
  }

  /**
   * Used to instantiate instances of
   */
  @Inject
  private Provider<Property.Builder> builderProvider;
  private final Map<String, Class<? extends HasUuid>> classes = sortedMapForIteration();
  private final CodexMapper codexMapper;
  private final Map<Type, Codex<?>> codexes = mapForLookup();
  private static final Logger logger = LoggerFactory.getLogger(TypeContext.class);
  private final Map<Class<?>, List<Property>> properties = mapForLookup();
  private final Map<Class<?>, List<PropertyPath>> principalPaths = mapForLookup();
  @Inject
  private PrincipalMapper principalMapper;
  /**
   * A DynamicCodex acts as a placeholder when type information can't be determined (which should be
   * rare).
   */
  @Inject
  private DynamicCodex dynamicCodex;

  @Inject
  TypeContext(CodexMapper codexMapper, @AllTypes Collection<Class<?>> allTypes) {
    this.codexMapper = codexMapper;

    if (allTypes.isEmpty()) {
      logger.warn("No unpackable classes. Will not be able to deserialize entity payloads");
      return;
    }

    for (Class<?> clazz : allTypes) {
      if (!HasUuid.class.isAssignableFrom(clazz)) {
        logger.warn("Ignoring type {} because it is not assignable to {}", clazz.getName(),
            HasUuid.class.getSimpleName());
        continue;
      }
      String payloadName = getPayloadName(clazz);
      if (classes.containsKey(payloadName)) {
        logger.error("Duplicate payload name {} in class {}",
            payloadName, clazz.getCanonicalName());
      } else {
        classes.put(payloadName, clazz.asSubclass(HasUuid.class));
        logger.debug("Flatpack map: {} -> {}", clazz.getCanonicalName(), payloadName);
      }
    }
  }

  /**
   * Examine a class and return {@link Property} helpers that describe all JSON properties that the
   * type is expected to interact with. Calls to this method are cached in the instance of
   * {@link TypeContext}.
   */
  public synchronized List<Property> extractProperties(Class<?> clazz) {
    // No properties on Object.class. Play nicely in case of null value.
    if (clazz == null || Object.class.equals(clazz)) {
      return Collections.emptyList();
    }

    // Cache check
    List<Property> toReturn = properties.get(clazz);
    if (toReturn != null) {
      return toReturn;
    }

    toReturn = listForAny();

    // Protect the return value and cache it
    List<Property> unmodifiable = Collections.unmodifiableList(toReturn);
    properties.put(clazz, unmodifiable);

    // Start by collecting all supertype properties
    toReturn.addAll(extractProperties(clazz.getSuperclass()));

    // Examine each declared method on the type and assemble Property objects
    Map<String, Property.Builder> builders = mapForIteration();
    for (Method m : clazz.getDeclaredMethods()) {
      if (isGetter(m)) {
        String beanPropertyName = beanPropertyName(m);
        Property.Builder builder = getBuilderForProperty(builders, beanPropertyName);
        toReturn.add(builder.peek());

        // Only use the getter to determine the actual json property name
        JsonProperty override = m.getAnnotation(JsonProperty.class);
        if (override != null) {
          builder.withName(override.value());
        } else {
          builder.withName(beanPropertyName);
        }
        builder.withGetter(m);
        /*
         * Disable traversal of OneToMany properties unless requested. Also wire up the implication
         * relationships between properties in the two models.
         */
        OneToMany oneToMany = m.getAnnotation(OneToMany.class);
        if (oneToMany != null) {
          builder.withDeepTraversalOnly(true);
          Class<?> otherModel = erase(getSingleParameterization(m.getGenericReturnType(),
              Collection.class));
          for (Property otherProperty : extractProperties(otherModel)) {
            if (otherProperty.getName().equals(oneToMany.mappedBy())) {
              builder.withImpliedProperty(otherProperty);
              otherProperty.setImpliedProperty(builder.peek());
              break;
            }
          }
        } else if (HasUuid.class.isAssignableFrom(m.getReturnType())) {
          /*
           * If the current property is a target of a OneToMany annotation on the other side of the
           * relationship, we want to fix up the current property's implied property before
           * returning it. In the case of a circular reference, the eager storage of the
           * unmodifiable list above will short-circuit this call.
           */
          extractProperties(m.getReturnType());
        }
      } else if (isSetter(m)) {
        Property.Builder builder = getBuilderForProperty(builders, beanPropertyName(m));
        builder.withSetter(m);
      }
    }

    // Finish construction
    for (Property.Builder builder : builders.values()) {
      builder.build();
    }

    return unmodifiable;
  }

  /**
   * Returns a Class from a payload name or {@code null} if the type is unknown.
   * 
   * @see Configuration#getDomainPackages()
   */
  public Class<? extends HasUuid> getClass(String simplePayloadName) {
    return classes.get(simplePayloadName);
  }

  /**
   * Convenience method to provide generics alignment.
   */
  @SuppressWarnings("unchecked")
  public <T> Codex<T> getCodex(Class<? extends T> clazz) {
    return (Codex<T>) getCodex((Type) clazz);
  }

  /**
   * Return a Codex instance that can operate on the specified type.
   */
  public synchronized Codex<?> getCodex(Type type) {
    Codex<?> toReturn = codexes.get(type);
    if (toReturn != null) {
      return toReturn;
    }

    toReturn = codexMapper.getCodex(this, type);

    if (toReturn == null) {
      toReturn = dynamicCodex;
    }

    codexes.put(type, toReturn);
    return toReturn;
  }

  public Collection<Class<? extends HasUuid>> getEntityTypes() {
    return Collections.unmodifiableCollection(classes.values());
  }

  /**
   * Returns the "type" name used for an entity type in the {@code data} section of the payload.
   */
  public String getPayloadName(Class<?> clazz) {
    JsonTypeName override = clazz.getAnnotation(JsonTypeName.class);
    if (override != null) {
      return override.value();
    }
    return FlatPackTypes.decapitalize(clazz.getSimpleName());
  }

  /**
   * Returns zero or more property paths that can be evaluated to find an object that can be
   * resolved to a user principal. The returned list will be ordered with the shortest paths first.
   */
  public synchronized List<PropertyPath> getPrincipalPaths(Class<? extends HasUuid> clazz) {
    List<PropertyPath> toReturn = principalPaths.get(clazz);
    if (toReturn != null) {
      return toReturn;
    }
    toReturn = Collections.unmodifiableList(computePrincipalPaths(clazz));
    principalPaths.put(clazz, toReturn);
    return toReturn;
  }

  /**
   * Initializes the recursive calls and sorts the result by path length.
   */
  private List<PropertyPath> computePrincipalPaths(Class<? extends HasUuid> clazz) {
    List<PropertyPath> allPaths = listForAny();
    computePrincipalPaths(new LinkedList<Property>(), clazz,
        new LinkedList<Class<? extends HasUuid>>(), allPaths);
    Collections.sort(allPaths, new Comparator<PropertyPath>() {
      @Override
      public int compare(PropertyPath o1, PropertyPath o2) {
        return o1.getPath().size() - o2.getPath().size();
      }
    });
    logger.debug("Principle paths for {} : {}", clazz.getName(), allPaths);
    return allPaths;
  }

  /**
   * Recursive implementation.
   * 
   * @param pathSoFar the current path for the type currently being examined
   * @param lookingAt the type to examine
   * @param seen the owner types of the properties in {@code pathSoFar}, to prevent cycles
   * @param accumulator an accumulator for data to return
   */
  private void computePrincipalPaths(Deque<Property> pathSoFar, Class<? extends HasUuid> lookingAt,
      LinkedList<Class<? extends HasUuid>> seen, List<PropertyPath> accumulator) {
    // Cycle detection
    if (seen.contains(lookingAt)) {
      return;
    }

    // Add the current path if it provides useful information
    if (principalMapper.isMapped(Collections.unmodifiableList(seen), lookingAt)) {
      accumulator.add(new PropertyPath(pathSoFar));
    }

    // Iterate over each property of the type and recurse
    seen.push(lookingAt);
    for (Property property : extractProperties(lookingAt)) {
      // Ignore properties that don't inherit
      if (!property.isInheritPrincipal()) {
        continue;
      }
      Type returnType = property.getGetter().getGenericReturnType();
      Class<? extends HasUuid> nextType;
      switch (property.getType().getJsonKind()) {
        case STRING:
          nextType = erase(returnType).asSubclass(HasUuid.class);
          break;
        case LIST:
          // TODO: This doesn't support arbitrary composition
          nextType = erase(getSingleParameterization(returnType, Collection.class))
              .asSubclass(HasUuid.class);
          break;
        default:
          throw new RuntimeException("Cannot use property " + property + " with "
            + InheritPrincipal.class.getSimpleName());
      }

      pathSoFar.addLast(property);
      computePrincipalPaths(pathSoFar, nextType, seen, accumulator);
      pathSoFar.removeLast();
    }
    seen.pop();
  }

  /**
   * Implements a get-or-create pattern.
   */
  private Property.Builder getBuilderForProperty(Map<String, Property.Builder> builders,
      String beanPropertyName) {
    Property.Builder builder = builders.get(beanPropertyName);
    if (builder == null) {
      builder = builderProvider.get();
      builders.put(beanPropertyName, builder);
    }
    return builder;
  }
}
