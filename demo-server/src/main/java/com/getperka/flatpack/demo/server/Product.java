/*
 * #%L
 * FlatPack Demonstration Server
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
package com.getperka.flatpack.demo.server;

import java.math.BigDecimal;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;

import com.getperka.flatpack.BaseHasUuid;

public class Product extends BaseHasUuid {
  private String name;
  private String notes;
  private BigDecimal price;

  @PermitAll
  public String getName() {
    return name;
  }

  @PermitAll
  public String getNotes() {
    return notes;
  }

  @PermitAll
  @Min(0)
  public BigDecimal getPrice() {
    return price;
  }

  @RolesAllowed("ADMIN")
  public void setName(String name) {
    this.name = name;
  }

  @RolesAllowed("ADMIN")
  public void setNotes(String notes) {
    this.notes = notes;
  }

  @RolesAllowed("ADMIN")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
