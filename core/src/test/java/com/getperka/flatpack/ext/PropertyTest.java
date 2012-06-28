package com.getperka.flatpack.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.junit.Test;

import com.getperka.flatpack.RoleMapper;

public class PropertyTest {

  static class FakeRoleMapper implements RoleMapper {
    @Override
    public Class<?> mapRole(String roleName) {
      return PropertyTest.class;
    }
  }

  @RolesAllowed("foobar")
  static class HasMethod {
    static void noAnnotation() {}
  }

  @DenyAll
  static void denyAll() {}

  static void noAnnotation() {}

  @PermitAll
  static void permitAll() {}

  @RolesAllowed("foobar")
  static void rolesAllowedOne() {}

  @RolesAllowed({})
  static void rolesAllowedZero() {}

  @Test
  public void testCheckRoles() {
    Set<Class<?>> empty = Collections.<Class<?>> emptySet();
    Set<Class<?>> full = Collections.<Class<?>> singleton(PropertyTest.class);
    Set<Class<?>> other = Collections.<Class<?>> singleton(UUID.class);
    Set<String> foobarRole = Collections.singleton("foobar");
    RoleMapper mapper = new FakeRoleMapper();

    // Access allowed when no RoleMapper is installed
    assertTrue(Property.checkRoles(null, empty, null));

    // Access allowed with allRoles
    assertTrue(Property.checkRoles(null, Property.allRoles, null));
    assertTrue(Property.checkRoles(mapper, Property.allRoles, null));

    // Access denied with empty set
    assertFalse(Property.checkRoles(mapper, null, null));
    assertFalse(Property.checkRoles(mapper, empty, null));

    // Access allowed
    assertTrue(Property.checkRoles(mapper, full, foobarRole));
    assertTrue(Property.checkRoles(mapper, Collections.<Class<?>> singleton(Object.class),
        foobarRole));
    assertFalse(Property.checkRoles(mapper, other, foobarRole));
  }

  @Test
  public void testRoleNameExtraction() {
    assertSame(Collections.emptySet(), names("denyAll"));
    assertSame(Property.noRoleNames, names("noAnnotation"));
    assertSame(Property.allRoleNames, names("permitAll"));
    assertEquals(Collections.singleton("foobar"), names("rolesAllowedOne"));
    assertSame(Collections.emptySet(), names("rolesAllowedZero"));
    assertEquals(Collections.singleton("foobar"), names(HasMethod.class, "noAnnotation"));
  }

  private Set<String> names(Class<?> clazz, String methodName) {
    try {
      Method method = clazz.getDeclaredMethod(methodName);
      return Property.extractRoleNames(method);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private Set<String> names(String methodName) {
    return names(getClass(), methodName);
  }
}
