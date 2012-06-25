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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.FlatPackEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Adapts the FlatPack serialization mechanisms to the Jersey / jax-rs stack.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlatPackProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object>,
    ContainerRequestFilter, ContainerResponseFilter {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private static final ThreadLocal<Principal> requestPrincipal = new ThreadLocal<Principal>();
  private static final ThreadLocal<Map<String, String>> flatpackWarnings = new ThreadLocal<Map<String, String>>();

  @Context
  Providers providers;
  private FlatPack flatpack;

  /**
   * Capture the Principal associated with the current thread for use by the post-request filter
   * method.
   */
  @Override
  public ContainerRequest filter(ContainerRequest request) {
    requestPrincipal.set(request.getUserPrincipal());
    return request;
  }

  /**
   * This method will serialize a FlatPackEntity, rather than waiting for {@link #writeTo}. This is
   * because {@code writeTo} will be called after all of the ContainerResponseFilters have been
   * called, which may prevent some objects from being serialized if they depend on container state.
   */
  @Override
  public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
    try {
      if (!MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType())) {
        return response;
      }
      Object t = response.getEntity();
      if (t == null) {
        // No data, nothing to do
        return response;
      }

      FlatPackEntity<?> toSend;
      if (t instanceof FlatPackEntity) {
        toSend = (FlatPackEntity<?>) t;
      } else {
        // Possibly wrap a flatpack-able type in a FlatPackEntity
        Type type = response.getEntityType();
        if (type == null) {
          type = t.getClass();
        }
        if (getFlatPack().isRootType(type)) {
          Principal principal = request.getUserPrincipal();
          toSend = FlatPackEntity.create(type, t, principal);
        } else {
          toSend = null;
        }
      }

      // The response isn't something that should be packed, so just let it through
      if (toSend == null) {
        return response;
      }

      // Copy and thread-local warnings into the output
      Map<String, String> warnings = flatpackWarnings.get();
      if (warnings != null) {
        for (Map.Entry<String, String> entry : warnings.entrySet()) {
          toSend.addWarning(entry.getKey(), entry.getValue());
        }
      }

      // Create the payload
      StringWriter out = new StringWriter();
      try {
        getFlatPack().getPacker().pack(toSend, out);
      } catch (IOException e) {
        throw new WebApplicationException(e);
      }

      // Swap out the response's entity
      response.setEntity(out.toString(), String.class);

      return response;
    } finally {
      requestPrincipal.remove();
      flatpackWarnings.remove();
    }
  }

  @Override
  public long getSize(Object t, Class<?> type, Type genericType,
      Annotation[] annotations,
      MediaType mediaType) {
    return -1;
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType) {
    return getFlatPack().isRootType(genericType) || JsonElement.class.isAssignableFrom(type);
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType) {
    return FlatPackEntity.class.isAssignableFrom(type) || JsonElement.class.isAssignableFrom(type)
      || getFlatPack().isRootType(genericType);
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations,
      MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
      throws IOException, WebApplicationException {

    if (InputStream.class.equals(type)) {
      return entityStream;
    }

    Reader in = new InputStreamReader(entityStream, UTF8);
    if (Reader.class.equals(type)) {
      return in;
    }

    if (JsonElement.class.isAssignableFrom(type)) {
      return new Gson().fromJson(in, JsonElement.class);
    }

    FlatPackEntity<?> entity;
    Object toReturn;
    if (FlatPackEntity.class.equals(type)) {
      Type parameterization;
      if (genericType instanceof ParameterizedType) {
        parameterization = ((ParameterizedType) genericType).getActualTypeArguments()[0];
      } else {
        parameterization = Void.class;
      }
      entity = getFlatPack().getUnpacker().unpack(parameterization, in, requestPrincipal.get());
      toReturn = entity;
    } else {
      entity = getFlatPack().getUnpacker().unpack(genericType, in, requestPrincipal.get());
      toReturn = entity.getValue();
    }
    flatpackWarnings.set(entity.getExtraWarnings());
    return toReturn;
  }

  /**
   * This method generally shouldn't be called on the server, since the
   * {@link #filter(ContainerRequest, ContainerResponse)} method above should have already
   * serialized the response. It is, however, used when using {@code jersey-client} code.
   */
  @Override
  public void writeTo(Object t, Class<?> type, Type genericType,
      Annotation[] annotations,
      MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF8);
    try {
      if (t instanceof JsonElement) {
        new Gson().toJson((JsonElement) t, writer);
      } else if (t instanceof String) {
        writer.write((String) t);
      } else if (t instanceof FlatPackEntity) {
        FlatPackEntity<?> fpe = (FlatPackEntity<?>) t;
        getFlatPack().getPacker().pack(fpe, writer);
      } else if (getFlatPack().isRootType(genericType)) {
        FlatPackEntity<?> fpe = FlatPackEntity.create(genericType, t, null);
        getFlatPack().getPacker().pack(fpe, writer);
      } else {
        // Indicates an error in isWritable()
        throw new UnsupportedOperationException("Cannot write a " + t.getClass().getName());
      }
    } finally {
      writer.close();
    }
  }

  private FlatPack getFlatPack() {
    if (flatpack == null) {
      flatpack = providers.getContextResolver(FlatPack.class, MediaType.WILDCARD_TYPE)
          .getContext(FlatPack.class);
    }
    return flatpack;
  }
}
