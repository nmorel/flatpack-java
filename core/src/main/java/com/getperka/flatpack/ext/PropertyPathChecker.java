package com.getperka.flatpack.ext;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.inject.PackScoped;

/**
 * A utility class to examine the property paths available in a chain of HasUuid references to
 * determine if the current context's principal is listed.
 */
@PackScoped
class PropertyPathChecker implements PropertyPath.Receiver {
  private final Principal lookFor;
  private final PrincipalMapper mapper;
  private boolean result;

  @Inject
  PropertyPathChecker(PrincipalMapper mapper, Principal lookFor) {
    this.lookFor = lookFor;
    this.mapper = mapper;
  }

  public boolean getResult() {
    return result;
  }

  @Override
  public boolean receive(Object value) {
    if (value instanceof HasUuid) {
      List<Principal> principals = mapper.getPrincipals((HasUuid) value);
      if (principals != null && principals.contains(lookFor)) {
        result = true;
        return false;
      }
    }
    return true;
  }
}