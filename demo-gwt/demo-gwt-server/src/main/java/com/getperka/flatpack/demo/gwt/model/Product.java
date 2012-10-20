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
package com.getperka.flatpack.demo.gwt.model;

import java.math.BigDecimal;

import javax.annotation.security.PermitAll;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A simple entity type. All properties are globally-readable, however the setters are restricted to just the admin
 * role.
 */
@Entity( name = "Product" )
public class Product
    extends BaseEntity
{
    private String name;
    private String notes;
    private BigDecimal price;
    private ChildBean child;

    @Column( name = "name" )
    @PermitAll
    @NotNull
    @Size( min = 1 )
    public String getName()
    {
        return name;
    }

    @Column( name = "notes" )
    public String getNotes()
    {
        return notes;
    }

    @Column( name = "price" )
    @PermitAll
    @Min( 0 )
    @NotNull
    public BigDecimal getPrice()
    {
        return price;
    }

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn( name = "childId" )
    public ChildBean getChild()
    {
        return child;
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

    public void setChild( ChildBean child )
    {
        this.child = child;
    }

}
