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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.client.FlatPackRequest;
import com.getperka.flatpack.client.StatusCodeException;
import com.getperka.flatpack.util.FlatPackTypes;

public class FlatPackRequestBase<R extends FlatPackRequest<R, X>, X>
    extends RequestBase<R, FlatPackEntity<X>> implements FlatPackRequest<R, X> {
  private final Type returnType;

  private FlatPackEntity<X> toSend;

  protected FlatPackRequestBase(ApiBase api, Type returnType, String method, String path,
      Object... args) {
    super(api, method, path, args);
    this.returnType = returnType;
  }

  @Override
  public FlatPackEntity<X> getEntity() {
    return toSend;
  }

  @Override
  public FlatPackEntity<X> peek() {
    return getEntity();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setEntity(Object entity) {
    toSend = (FlatPackEntity<X>) FlatPackEntity.create(Object.class, entity, null);
  }

  @Override
  protected FlatPackEntity<X> execute(HttpURLConnection conn) throws IOException {
    Reader reader;
    int status = conn.getResponseCode();
    if (!isOk(status)) {
      InputStream errorStream = conn.getErrorStream();
      reader = errorStream == null ? null : new InputStreamReader(errorStream, FlatPackTypes.UTF8);
    } else {
      reader = new InputStreamReader(conn.getInputStream(), FlatPackTypes.UTF8);
    }

    Throwable cause = null;
    FlatPackEntity<X> entity = null;
    if (reader != null) {
      try {
        entity = getApi().getFlatPack().getUnpacker().unpack(returnType, reader, null);
      } catch (IOException e) {
        cause = e;
      } catch (RuntimeException e) {
        cause = e;
      }
    }

    // Treat any non-2XX response as an error
    if (!isOk(status)) {
      StatusCodeException sce = new StatusCodeException(status, cause);
      sce.setEntity(entity);
      throw sce;
    }

    // Spy on the response to see if there's a new token
    String accessToken = entity.getExtraData().get("access_token");
    if (accessToken != null) {
      getApi().setAccessToken(accessToken);
    }
    return entity;
  }

  @Override
  protected void writeEntity(HttpURLConnection connection) throws IOException {
    if (getEntity() == null) {
      return;
    }
    connection.setRequestProperty("Content-Type", "application/json; charset=UTF8");
    Writer out = new OutputStreamWriter(connection.getOutputStream(), FlatPackTypes.UTF8);
    getApi().getFlatPack().getPacker().pack(toSend, out);
    out.close();
  }
}