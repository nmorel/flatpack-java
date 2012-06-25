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
