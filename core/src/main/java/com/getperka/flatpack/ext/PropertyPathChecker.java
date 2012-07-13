/*
 * #%L
 * FlatPack serialization code
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