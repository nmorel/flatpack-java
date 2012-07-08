package com.getperka.flatpack.inject;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.PrincipalMapper;

/**
 * A trivial implementation of {@link PrincipalMapper} that allows access to all objects.
 */
class PermissivePrincipalMapper implements PrincipalMapper {

  PermissivePrincipalMapper() {}

  @Override
  public List<Principal> getPrincipals(HasUuid entity) {
    return Collections.emptyList();
  }

  @Override
  public List<String> getRoles(Principal principal) {
    return Collections.emptyList();
  }

  @Override
  public boolean isAccessEnforced(Principal principal, HasUuid entity) {
    return false;
  }

  @Override
  public boolean isMapped(List<Class<? extends HasUuid>> pathSoFar,
      Class<? extends HasUuid> entitiyType) {
    return false;
  }
}