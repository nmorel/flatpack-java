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
package com.getperka.flatpack.demo.gwt;

import java.math.BigDecimal;

import com.getperka.flatpack.gwt.BaseHasUuid;

/**
 * A simple entity type. All properties are globally-readable, however the setters are restricted to just the admin
 * role.
 */
public class Product
    extends BaseHasUuid
{
    private String name;
    private String notes;
    private BigDecimal price;

    public String getName()
    {
        return name;
    }

    public String getNotes()
    {
        return notes;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    // If a setter lacks any security settings, the values applied to the getter will be used
    public void setNotes( String notes )
    {
        this.notes = notes;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Product [name=" + name + ", notes=" + notes + ", price=" + price + "]";
    }
}
