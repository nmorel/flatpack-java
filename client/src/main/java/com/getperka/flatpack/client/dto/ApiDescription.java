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
import java.util.UUID;

import javax.annotation.security.PermitAll;

import com.getperka.flatpack.BaseHasUuid;

/**
 * A description of the entities contained within an API.
 */
public class ApiDescription extends BaseHasUuid {
  private String apiName;
  private String apiVersion;
  private List<EndpointDescription> endpoints;
  private List<EntityDescription> entities;

  @PermitAll
  public String getApiName() {
    return apiName;
  }

  @PermitAll
  public String getApiVersion() {
    return apiVersion;
  }

  @PermitAll
  public List<EndpointDescription> getEndpoints() {
    return endpoints;
  }

  @PermitAll
  public List<EntityDescription> getEntities() {
    return entities;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public void setEndpoints(List<EndpointDescription> endpoints) {
    this.endpoints = endpoints;
  }

  public void setEntities(List<EntityDescription> entities) {
    this.entities = entities;
  }

  @Override
  protected UUID defaultUuid() {
    if (apiName == null) {
      throw new IllegalStateException();
    }
    return UUID.nameUUIDFromBytes((getClass().getName() + ":" + apiName).getBytes(UTF8));
  }
}