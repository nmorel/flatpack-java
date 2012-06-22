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
package com.getperka.flatpack.client.impl;

import java.net.HttpURLConnection;
import java.net.URI;

import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.client.Api;

/**
 * A base class for accessing FlatPack API servers.
 */
public abstract class ApiBase implements Api {
  private String accessToken;
  private URI serverBase;
  private final FlatPack flatpack;

  private boolean verbose;

  protected ApiBase(FlatPack flatpack) {
    this.flatpack = flatpack;
  }

  @Override
  public String getAccessToken() {
    return accessToken;
  }

  @Override
  public URI getServerBase() {
    return serverBase;
  }

  @Override
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public void setServerBase(URI serverBase) {
    this.serverBase = serverBase;
  }

  @Override
  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  HttpURLConnection filter(HttpURLConnection conn) {
    if (accessToken != null) {
      conn.setRequestProperty("Authorization", "Bearer: " + accessToken);
    }
    return conn;
  }

  FlatPack getFlatPack() {
    return flatpack;
  }

  boolean isVerbose() {
    return verbose;
  }
}
