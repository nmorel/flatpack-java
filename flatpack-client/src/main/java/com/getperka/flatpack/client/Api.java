/*
 * #%L
 * FlatPack Client
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
package com.getperka.flatpack.client;

import java.net.URI;
import java.util.logging.Logger;

/**
 * A base interface for generated FlatPack API accessors.
 */
public interface Api {

  /**
   * Retrieve the current OAuth2 access token associated with the Api instance.
   */
  String getAccessToken();

  /**
   * Retrieve the base URI used to access the server.
   */
  URI getServerBase();

  /**
   * Set the OAuth2 access token to be used when making requests.
   */
  void setAccessToken(String accessToken);

  /**
   * Set the base URI used to access the server.
   */
  void setServerBase(URI serverBase);

  /**
   * Enable verbose output via a {@link Logger}.
   */
  void setVerbose(boolean verbose);

}