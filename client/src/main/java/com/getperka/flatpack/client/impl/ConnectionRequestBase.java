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

import java.io.IOException;
import java.net.HttpURLConnection;

import com.getperka.flatpack.client.Request;

public class ConnectionRequestBase<R extends Request<R, HttpURLConnection>> extends
    RequestBase<R, HttpURLConnection> {

  protected ConnectionRequestBase(ApiBase api, String method, String path, Object... args) {
    super(api, method, path, args);
  }

  @Override
  protected HttpURLConnection execute(HttpURLConnection response) {
    return response;
  }

  @Override
  protected void writeEntity(HttpURLConnection connection) throws IOException {}
}