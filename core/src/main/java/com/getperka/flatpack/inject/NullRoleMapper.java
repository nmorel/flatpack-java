package com.getperka.flatpack.inject;

import com.getperka.flatpack.RoleMapper;

/**
 * An implementation of {@link RoleMapper} that always returns {@code null}.
 */
class NullRoleMapper implements RoleMapper {
  NullRoleMapper() {}

  @Override
  public Class<?> mapRole(String roleName) {
    return null;
  }
}