package com.getperka.flatpack.demo.client;
/*
 * #%L
 * FlatPack Demonstration Client
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

import java.math.BigDecimal;

/**
 * This class demonstrates how a user-provided concrete type can be swapped in for a fully-generated
 * DTO. By specifying the {@code product} types as a base type, the code generator will emit an
 * abstract, package-protected {@code ProductBase} type.
 */
public class Product extends ProductBase {

  public BigDecimal getDiscountedPrice() {
    return getPrice().divide(BigDecimal.valueOf(2));
  }

  @Override
  public String toString() {
    return getName() + " @ $" + getPrice();
  }
}
