/*
 * #%L
 * FlatPack Jersey integration
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
package com.getperka.flatpack.jersey;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPack;

/**
 * Allows access to the FlatPack instance via the injectable {@link Providers} interface.
 */
@Provider
public class FlatPackResolver implements ContextResolver<FlatPack> {
  private final FlatPack flatpack;

  public FlatPackResolver(Configuration configuration) {
    flatpack = FlatPack.create(configuration);
  }

  @Override
  public FlatPack getContext(Class<?> type) {
    return flatpack;
  }
}
