package com.getperka.flatpack.demo.server;

import java.security.Principal;

/**
 * A trivial Principal implementation that just stores the role for the user.
 */
public class DummyPrincipal implements Principal {
  public static final DummyPrincipal NOBODY = new DummyPrincipal("", Roles.NOBODY);
  private final String role;
  private final String name;

  public DummyPrincipal(String name, String role) {
    this.name = name;
    this.role = role;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getRole() {
    return role;
  }
}
