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
import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.getperka.flatpack.client.Request;
import com.getperka.flatpack.util.FlatPackCollections;

abstract class RequestBase<R extends Request<R, X>, X> implements Request<R, X> {
  private static final Pattern pathArgPattern = Pattern.compile("[{][^}]*[}]");
  private final ApiBase api;
  private final Object[] args;
  private Object entity;
  private Map<String, Object> headers = Collections.emptyMap();
  private final String method;
  private final String path;
  private Map<String, Object> queryParams = Collections.emptyMap();

  protected RequestBase(ApiBase api, String method, String path, Object... args) {
    this.api = api;
    this.args = args;
    this.method = method;
    this.path = path;
  }

  @Override
  public X execute() throws IOException {
    String replacedPath = path;

    // Replace all {foo} in the path with the args
    Matcher m = pathArgPattern.matcher(replacedPath);
    int index = 0;
    while (m.find() && index < args.length) {
      replacedPath = m.replaceFirst(args[index++].toString());
      m = pathArgPattern.matcher(replacedPath);
    }

    StringBuilder sb = new StringBuilder(replacedPath);
    // Now add query parameters
    if (!queryParams.isEmpty()) {
      sb.append("?");
      boolean needsAmp = false;
      for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
        if (needsAmp) {
          sb.append("&");
        } else {
          needsAmp = true;
        }
        sb.append(entry.getKey()).append("=")
            .append(URLEncoder.encode(entry.getValue().toString(), "UTF8"));
      }
    }

    URI sendTo = api.getServerBase().resolve(sb.toString());

    HttpURLConnection conn = (HttpURLConnection) sendTo.toURL().openConnection();
    conn.setRequestMethod(method);
    for (Map.Entry<String, Object> entry : headers.entrySet()) {
      conn.setRequestProperty(entry.getKey(), entry.getValue().toString());
    }
    conn = api.filter(conn);
    writeEntity(conn);
    return execute(conn);
  }

  public Object getEntity() {
    return entity;
  }

  @Override
  public R header(String name, Object value) {
    if (headers.isEmpty()) {
      headers = FlatPackCollections.mapForIteration();
    }
    headers.put(name, value);
    return as();
  }

  @Override
  public R queryParameter(String name, Object value) {
    if (queryParams.isEmpty()) {
      queryParams = FlatPackCollections.mapForIteration();
    }
    queryParams.put(name, value);
    return as();
  }

  public void setEntity(Object entity) {
    this.entity = entity;
  }

  @SuppressWarnings("unchecked")
  protected R as() {
    return (R) this;
  }

  protected abstract X execute(HttpURLConnection response) throws IOException;

  protected ApiBase getApi() {
    return api;
  }

  /**
   * Returns {@code true} for a 2XX series response code.
   */
  protected boolean isOk(int statusCode) {
    return statusCode >= 200 && statusCode < 300;
  }

  protected abstract void writeEntity(HttpURLConnection connection) throws IOException;
}