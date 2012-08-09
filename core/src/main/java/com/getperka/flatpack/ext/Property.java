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

import static com.getperka.flatpack.util.FlatPackTypes.UTF8;
import static com.getperka.flatpack.util.FlatPackTypes.hasAnnotationWithSimpleName;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.UUID;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.InheritPrincipal;
import com.getperka.flatpack.JsonProperty;
import com.getperka.flatpack.RoleMapper;
import com.getperka.flatpack.SuppressDefaultValue;
import com.getperka.flatpack.inject.DisableRoleChecks;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * An immutable view of a property that should be serialized.
 */
public class Property extends BaseHasUuid {

  /**
   * Constructs {@link Property} instances.
   */
  static class Builder {

    @Inject
    @DisableRoleChecks
    private boolean allowAll;

    @Inject
    private Property prop;

    @Inject
    private TypeContext typeContext;

    Builder() {}

    public Property build() {
      Property toReturn = prop;
      prop = null;

      Method getter = toReturn.getGetter();
      Method setter = toReturn.getSetter();
      if (allowAll) {
        toReturn.getterRoles = toReturn.setterRoles = allRoles;
      } else {
        toReturn.getterRoleNames = extractRoleNames(getter);
        toReturn.setterRoleNames = extractRoleNames(setter);
        if (toReturn.setterRoleNames == noRoleNames) {
          toReturn.setterRoleNames = toReturn.getterRoleNames;
        }
        toReturn.getterRoles = extractRoles(toReturn.roleMapper, toReturn.getterRoleNames);
        toReturn.setterRoles = extractRoles(toReturn.roleMapper, toReturn.setterRoleNames);
      }

      if (getter != null) {
        Class<?> enclosingType = getter.getDeclaringClass();
        toReturn.enclosingTypeName = typeContext.getPayloadName(enclosingType);

        java.lang.reflect.Type returnType = getter.getGenericReturnType();
        toReturn.codex = typeContext.getCodex(returnType);
        analyzeAnnotations(toReturn, getter);
      } else if (setter != null) {
        Class<?> enclosingType = setter.getDeclaringClass();
        toReturn.enclosingTypeName = typeContext.getPayloadName(enclosingType);

        java.lang.reflect.Type paramType = setter.getGenericParameterTypes()[0];
        toReturn.codex = typeContext.getCodex(paramType);
        analyzeAnnotations(toReturn, setter);
      } else {
        throw new IllegalStateException("No getter or setter");
      }
      toReturn.type = toReturn.codex.describe();

      return toReturn;
    }

    /**
     * Copy initializer.
     */
    public Builder from(Property p) {
      prop.deepTraversalOnly = p.deepTraversalOnly;
      prop.getter = p.getter;
      prop.getterRoles = p.getterRoles;
      prop.implied = p.implied;
      prop.name = p.name;
      prop.setter = p.setter;
      prop.setterRoles = p.setterRoles;
      prop.setUuid(p.getUuid());
      return this;
    }

    /**
     * Returns the Property object under construction.
     */
    public Property peek() {
      return prop;
    }

    public Builder withDeepTraversalOnly(boolean only) {
      prop.deepTraversalOnly = only;
      return this;
    }

    public Builder withGetter(Method getter) {
      getter.setAccessible(true);
      prop.getter = getter;
      return this;
    }

    public Builder withImpliedProperty(Property implied) {
      prop.implied = implied;
      return this;
    }

    public Builder withName(String name) {
      prop.name = name;
      return this;
    }

    public Builder withSetter(Method setter) {
      setter.setAccessible(true);
      prop.setter = setter;
      return this;
    }

    private void analyzeAnnotations(Property toReturn, AnnotatedElement method) {
      toReturn.embedded = hasAnnotationWithSimpleName(method, "Embedded");
      toReturn.inheritPrincipal = method.isAnnotationPresent(InheritPrincipal.class);
      toReturn.suppressDefaultValue = method.isAnnotationPresent(SuppressDefaultValue.class);
    }
  }

  /**
   * Used internally to implement the {@link Builder#withAllowAllRoles(boolean)} method.
   */
  private interface AllRolesView {}

  private interface NoRolesView {}

  /**
   * Sorts Property objects by {@link #getName()}.
   */
  public static final Comparator<Property> PROPERTY_NAME_COMPARATOR = new Comparator<Property>() {
    @Override
    public int compare(Property o1, Property o2) {
      return o1.getName().compareTo(o2.getName());
    }
  };

  static final Set<Class<?>> allRoles =
      Collections.<Class<?>> singleton(AllRolesView.class);
  static final Set<Class<?>> noRoles =
      Collections.<Class<?>> singleton(NoRolesView.class);
  static final Set<String> allRoleNames = Collections.singleton("*");
  static final Set<String> noRoleNames = Collections.singleton("");

  static boolean checkRoles(RoleMapper roleMapper, Collection<Class<?>> required,
      Collection<String> credentials) {
    // Object comparison intentional
    if (roleMapper == null || required == allRoles) {
      return true;
    }
    if (required == null || required.isEmpty()) {
      return false;
    }
    for (String cred : credentials) {
      Class<?> credView = roleMapper.mapRole(cred);
      if (credView == null) {
        continue;
      }
      if (required.contains(credView)) {
        return true;
      }
      for (Class<?> req : required) {
        if (req.isAssignableFrom(credView)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the role names associated with a method or class. This method never returns
   * {@code null}, however there are several special return values:
   * <ul>
   * <li>{@link #noRoleNames} indicates that no access information was set
   * <li>{@link #allRoleNames} indicates that access should be allowed for all roles
   * <li>An empty set indicates access should be denied for all roles
   * </ul>
   */
  static Set<String> extractRoleNames(AnnotatedElement obj) {
    // Map no method to none-allowed
    if (obj == null) {
      return noRoleNames;
    }
    if (obj.isAnnotationPresent(DenyAll.class)) {
      return Collections.emptySet();
    }
    if (obj.isAnnotationPresent(PermitAll.class)) {
      return allRoleNames;
    }
    RolesAllowed view = obj.getAnnotation(RolesAllowed.class);
    if (view == null) {
      return noRoleNames;
    }
    Set<String> toReturn = FlatPackCollections.setForIteration();
    toReturn.addAll(Arrays.asList(view.value()));
    if (toReturn.isEmpty()) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(toReturn);
  }

  /**
   * Extract role information from a method or its declaring class.
   */
  static Set<String> extractRoleNames(Method method) {
    if (method == null) {
      return noRoleNames;
    }
    Set<String> toReturn = extractRoleNames((AnnotatedElement) method);
    if (noRoleNames == toReturn) {
      toReturn = extractRoleNames(method.getDeclaringClass());
    }
    return toReturn;
  }

  static Set<Class<?>> extractRoles(RoleMapper mapper, Set<String> roleNames) {
    // Object comparison intentional
    if (mapper == null || roleNames == null || noRoleNames == roleNames) {
      return noRoles;
    }
    if (allRoleNames == roleNames) {
      return allRoles;
    }
    Set<Class<?>> toReturn = FlatPackCollections.setForIteration();
    for (String name : roleNames) {
      Class<?> roleClass = mapper.mapRole(name);
      if (roleClass != null) {
        toReturn.add(roleClass);
      }
    }
    return Collections.unmodifiableSet(toReturn);
  }

  private Codex<?> codex;
  private boolean deepTraversalOnly;
  /**
   * This property is mutable by external callers. It's kind of a hack to allow the describe
   * endpoint to lazily add the doc strings.
   */
  private String docString;
  private String enclosingTypeName;
  private boolean embedded;
  private Method getter;
  private Set<Class<?>> getterRoles;
  private Set<String> getterRoleNames;
  private Property implied;
  private boolean inheritPrincipal;
  private String name;
  @Inject
  private RoleMapper roleMapper;
  private Method setter;
  private Set<Class<?>> setterRoles;
  private Set<String> setterRoleNames;
  private boolean suppressDefaultValue;
  private Type type;

  @Inject
  private Property() {}

  @DenyAll
  public Codex<?> getCodex() {
    return codex;
  }

  @PermitAll
  public String getDocString() {
    return docString;
  }

  /**
   * The payload name of the type that defines the property.
   */
  @PermitAll
  public String getEnclosingTypeName() {
    return enclosingTypeName;
  }

  /**
   * Returns the getter method for this Property. The returned method will have a non-{@code void}
   * return type and no parameters.
   */
  @DenyAll
  public Method getGetter() {
    return getter;
  }

  /**
   * Returns the role names that are allowed to get the property. A value containing a single
   * asterisk means that all roles may access the property.
   */
  @PermitAll
  public Set<String> getGetterRoleNames() {
    return getterRoleNames;
  }

  /**
   * When a new value is assigned to the current property in some instance, the implied property of
   * the new value should also be updated with the current instance.
   */
  @PermitAll
  public Property getImpliedProperty() {
    return implied;
  }

  /**
   * Returns the json payload name of the Property, which may differ from the bean name if a
   * {@link JsonProperty} annotation has been applied to the getter.
   */
  @PermitAll
  public String getName() {
    return name;
  }

  /**
   * Returns the optional setter for the property. The returned method will have a single parameter
   * and a {@code void} return type.
   */
  @DenyAll
  public Method getSetter() {
    return setter;
  }

  /**
   * Return the role names that are allowed to set this property. A value containing a single
   * asterisk means that all roles may set the property.
   */
  @PermitAll
  public Set<String> getSetterRoleNames() {
    return setterRoleNames;
  }

  /**
   * A simplified description of the property's type.
   */
  @PermitAll
  public Type getType() {
    return type;
  }

  /**
   * Returns {@code true} if the Property should be included only during a deep traversal.
   */
  @PermitAll
  public boolean isDeepTraversalOnly() {
    return deepTraversalOnly;
  }

  /**
   * Returns {@code true} if an entity Property's properties should be emitted into the owning
   * entity's properties.
   */
  @PermitAll
  public boolean isEmbedded() {
    return embedded;
  }

  /**
   * Returns {@code true} if the referred entity's owner should also be considered an owner of the
   * entity that defines the Property.
   */
  @PermitAll
  public boolean isInheritPrincipal() {
    return inheritPrincipal;
  }

  /**
   * If {@code true}, non-null properties that contain the property type's default value will not be
   * serialized. For example, integer properties whose values are {@code 0} will not be serialized.
   */
  @PermitAll
  public boolean isSuppressDefaultValue() {
    return suppressDefaultValue;
  }

  /**
   * Returns {@code true} if a user with the given roles may retrieve the property. The roles
   * provided must intersect or be assignable to the roles defined on the getter via
   * {@link RolesAllowed}.
   */
  public boolean mayGet(Collection<String> roles) {
    if (getter == null) {
      return false;
    }
    return checkRoles(roleMapper, getterRoles, roles);
  }

  /**
   * Returns {@code true} if a user with the given roles may set the property. The roles provided
   * must intersect or be assignable to the roles defined on the setter via {@link RolesAllowed}. If
   * no roles were specified on the setter method, the roles defined on the getter will be used.
   */
  public boolean maySet(Collection<String> roles) {
    if (setter == null) {
      return false;
    }
    return checkRoles(roleMapper, setterRoles, roles);
  }

  public void setDocString(String docString) {
    this.docString = docString;
  }

  /**
   * For debugging use only.
   */
  @Override
  public String toString() {
    return getEnclosingTypeName() + "." + getName() + " ::= " + getType();
  }

  @Override
  protected UUID defaultUuid() {
    return UUID.nameUUIDFromBytes((getEnclosingTypeName() + "." + getName()).getBytes(UTF8));
  }

  void setDeepTraversalOnly(boolean deepTraversalOnly) {
    this.deepTraversalOnly = deepTraversalOnly;
  }

  void setEmbedded(boolean embedded) {
    this.embedded = embedded;
  }

  void setEnclosingTypeName(String enclosingTypeName) {
    this.enclosingTypeName = enclosingTypeName;
  }

  void setGetterRoleNames(Set<String> names) {
    this.getterRoleNames = names;
  }

  void setImplied(Property implied) {
    this.implied = implied;
  }

  /**
   * Use for late fixups of implied properties when the OneToMany property is examined after the
   * ManyToOne relationship.
   */
  void setImpliedProperty(Property implied) {
    this.implied = implied;
  }

  void setInheritPrincipal(boolean inheritPrincipal) {
    this.inheritPrincipal = inheritPrincipal;
  }

  void setName(String name) {
    this.name = name;
  }

  void setSetterRoleNames(Set<String> names) {
    this.setterRoleNames = names;
  }

  void setSuppressDefaultValue(boolean suppressDefaultValue) {
    this.suppressDefaultValue = suppressDefaultValue;
  }

  void setType(Type type) {
    this.type = type;
  }
}
