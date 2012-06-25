package com.getperka.flatpack.demo.server;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.InheritPrincipal;
import com.getperka.flatpack.ext.PrincipalMapper;

public class DemoPrincipalMapper implements PrincipalMapper {
  /**
   * If your Principals are derived from model object (e.g. users are stored as entities) or
   * specific entities should be editable by specific principals, this method can be used to provide
   * a mapping from the entity to identities.
   */
  @Override
  public List<Principal> getPrincipals(HasUuid entity) {
    return Collections.emptyList();
  }

  /**
   * Maps a Principal to one or more roles that are used to restrict which properties may be read or
   * set during serialization.
   */
  @Override
  public List<String> getRoles(Principal principal) {
    return Collections.singletonList(((DummyPrincipal) principal).getRole());
  }

  /**
   * This allows accesses to be disabled for certain classes of users or entities. Simple examples
   * include application super-users, however it may be desirable to have certain entities that are
   * mutable for a period of time and then sealed.
   */
  @Override
  public boolean isAccessEnforced(Principal principal, HasUuid entity) {
    return !Roles.ADMIN.equals(((DummyPrincipal) principal).getRole());
  }

  /**
   * This method is called when an {@link InheritPrincipal} is being evaluated to determine if the
   * entity type yields useful information for {@link #getPrincipals(HasUuid)}.
   */
  @Override
  public boolean isMapped(List<Class<? extends HasUuid>> pathSoFar,
      Class<? extends HasUuid> entitiyType) {
    return false;
  }
}
