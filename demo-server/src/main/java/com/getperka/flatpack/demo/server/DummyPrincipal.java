/*
 * #%L
 * FlatPack Demonstration Server
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
