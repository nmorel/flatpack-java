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
import java.io.Reader;
import java.net.HttpURLConnection;

import com.getperka.flatpack.util.FlatPackTypes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * A Request that interprets the server response as JSON.
 * 
 * @param <R> the type of JsonRequestBase
 */
public class JsonRequestBase<R extends JsonRequestBase<R>> extends RequestBase<R, JsonElement> {

  protected JsonRequestBase(ApiBase api, String method, String path, Object... args) {
    super(api, method, path, args);
  }

  @Override
  protected JsonElement execute(HttpURLConnection response) throws IOException {
    int statusCode = response.getResponseCode();
    JsonElement toReturn = null;

    try {
      InputStream in = isOk(statusCode) ? response.getInputStream() : response.getErrorStream();
      Reader reader = new InputStreamReader(in, FlatPackTypes.UTF8);
      toReturn = new JsonParser().parse(reader);
    } catch (JsonParseException ignored) {
      // Probably a 500 crash page
      toReturn = new JsonObject();
    }

    if (toReturn.isJsonObject()) {
      toReturn.getAsJsonObject().addProperty("status_code", statusCode);
    }
    return toReturn;
  }

  @Override
  protected void writeEntity(HttpURLConnection connection) throws IOException {}

}
