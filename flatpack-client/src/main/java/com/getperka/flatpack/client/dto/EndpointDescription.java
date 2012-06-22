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
package com.getperka.flatpack.client.dto;

import static com.getperka.flatpack.util.FlatPackTypes.UTF8;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.security.PermitAll;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.ext.Type;

/**
 * Describes an {@code HTTP} request endpoint.
 */
public class EndpointDescription extends BaseHasUuid {
  private String docString;
  private Type entity;
  private String method;
  private String path;
  private List<ParameterDescription> pathParameters;
  private List<ParameterDescription> queryParameters;
  private Type returnType;
  private Set<String> roleNames;

  public EndpointDescription(String method, String path) {
    this.method = method;
    this.path = path;
  }

  /**
   * Used for deserialization.
   */
  EndpointDescription() {}

  @PermitAll
  public String getDocString() {
    return docString;
  }

  /**
   * The expected entity type for the request. Generally, the {@code HTTP POST} body.
   */
  @PermitAll
  public Type getEntity() {
    return entity;
  }

  /**
   * The HTTP method used to access the endpoint.
   */
  @PermitAll
  public String getMethod() {
    return method;
  }

  /**
   * The path used to access the endpoint.
   */
  @PermitAll
  public String getPath() {
    return path;
  }

  /**
   * Describes any parameters embedded in {@link #getPath()}.
   */
  @PermitAll
  public List<ParameterDescription> getPathParameters() {
    return pathParameters;
  }

  /**
   * Describes any query parameters for the endpoint.
   */
  @PermitAll
  public List<ParameterDescription> getQueryParameters() {
    return queryParameters;
  }

  /**
   * The expected contents for the HTTP response.
   */
  @PermitAll
  public Type getReturnType() {
    return returnType;
  }

  /**
   * Return the role names that are allowed to access the endpoint. A {@code null} value means that
   * all roles are allowed, while a zero-length value means that no roles are allowed.
   */
  @PermitAll
  public Set<String> getRoleNames() {
    return roleNames;
  }

  public void setDocString(String docString) {
    this.docString = docString;
  }

  public void setEntity(Type entity) {
    this.entity = entity;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPathParameters(List<ParameterDescription> pathParameters) {
    this.pathParameters = pathParameters;
  }

  public void setQueryParameters(List<ParameterDescription> parameters) {
    this.queryParameters = parameters;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

  public void setRoleNames(Set<String> roleNames) {
    this.roleNames = roleNames;
  }

  @Override
  public String toString() {
    return method + " " + path;
  }

  @Override
  protected UUID defaultUuid() {
    return UUID.nameUUIDFromBytes((method + ":" + path).getBytes(UTF8));
  }
}
