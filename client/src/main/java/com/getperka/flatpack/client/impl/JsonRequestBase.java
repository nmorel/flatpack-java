package com.getperka.flatpack.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

import com.getperka.flatpack.util.FlatPackTypes;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
      toReturn = new Gson().fromJson(reader, JsonElement.class);
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
