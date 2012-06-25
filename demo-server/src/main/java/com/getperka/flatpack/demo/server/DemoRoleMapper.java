package com.getperka.flatpack.demo.server;

import com.getperka.flatpack.RoleMapper;

public class DemoRoleMapper implements RoleMapper {
  private static class Admin {}

  private static class Nobody {}

  /**
   * The simple role name should be mapped onto a class hierarchy where assignability is used to
   * determine access. For example, if a property setter requires the role {@value Roles#ADMIN}
   * which is mapped to {@link Admin}, then a user may only set that role if their Principal is
   * mapped onto a role type assignable to {@link Admin}.
   */
  @Override
  public Class<?> mapRole(String roleName) {
    if (Roles.ADMIN.equals(roleName)) {
      return Admin.class;
    }
    return Nobody.class;
  }

}
