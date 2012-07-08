package com.getperka.flatpack.inject;

import java.security.Principal;

class NullPrincipal implements Principal {
  @Override
  public String getName() {
    return null;
  }
}