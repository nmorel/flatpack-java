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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.DefaultUserIdentity;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Authentication.User;

public class DummyAuthenticator implements Authenticator {

  @Override
  public String getAuthMethod() {
    return "DUMMY";
  }

  @Override
  public boolean secureResponse(ServletRequest request, ServletResponse response,
      boolean mandatory, User validatedUser) throws ServerAuthException {
    return false;
  }

  @Override
  public void setConfiguration(AuthConfiguration configuration) {}

  @Override
  public Authentication validateRequest(ServletRequest request, ServletResponse response,
      boolean mandatory) throws ServerAuthException {

    HttpServletRequest req = (HttpServletRequest) request;
    DummyPrincipal principal;
    if (req.getParameter("isAdmin") != null) {
      principal = new DummyPrincipal("Hacker T. Admin", Roles.ADMIN);
    } else {
      principal = DummyPrincipal.NOBODY;
    }
    return new UserAuthentication(getAuthMethod(),
        new DefaultUserIdentity(null, principal, new String[] { principal.getRole() }));
  }
}
