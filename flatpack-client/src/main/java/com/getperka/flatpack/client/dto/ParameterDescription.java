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

import java.util.UUID;

import javax.annotation.security.PermitAll;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.ext.Type;

/**
 * Describes a path or query parameter in {@link EndpointDescription}.
 */
public class ParameterDescription extends BaseHasUuid {
  private String docString;
  private EndpointDescription endpoint;
  private String name;
  private Type type;

  public ParameterDescription(EndpointDescription endpoint, String name, Type type) {
    this.endpoint = endpoint;
    this.name = name;
    this.type = type;
  }

  ParameterDescription() {}

  @PermitAll
  public String getDocString() {
    return docString;
  }

  @PermitAll
  public EndpointDescription getEndpoint() {
    return endpoint;
  }

  @PermitAll
  public String getName() {
    return name;
  }

  @PermitAll
  public Type getType() {
    return type;
  }

  public void setDocString(String docString) {
    this.docString = docString;
  }

  public void setEndpoint(EndpointDescription endpoint) {
    this.endpoint = endpoint;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  protected UUID defaultUuid() {
    String key = endpoint.getUuid() + ":" + name;
    return UUID.nameUUIDFromBytes(key.getBytes(UTF8));
  }
}
