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
package com.getperka.flatpack.demo.gwt.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.demo.gwt.model.Product;

/**
 * This is just a simple, in-memory datastructure to hold the sample data. In reality, you would likely use a JPA
 * implementation.
 */
public class FakeDatabase
    implements Database
{
    // Retain order
    private final Map<UUID, HasUuid> allEntities = new LinkedHashMap<UUID, HasUuid>();

    public FakeDatabase()
    {
        // TODO: initialize from fake data
        Product p1 = new Product();
        p1.setName( "Name1" );
        p1.setNotes( "Notes1" );
        p1.setPrice( new BigDecimal( 2346 ) );
        persist( p1 );

        Product p2 = new Product();
        p2.setName( "Name2" );
        p2.setNotes( "Notes2" );
        p2.setPrice( new BigDecimal( 23672346 ) );
        persist( p2 );
    }

    /*
     * (non-Javadoc)
     * @see com.getperka.flatpack.demo.gwt.persistence.Db#clear()
     */
    @Override
    public void clear()
    {
        allEntities.clear();
    }

    /*
     * (non-Javadoc)
     * @see com.getperka.flatpack.demo.gwt.persistence.Db#get(java.lang.Class)
     */
    @Override
    public <T extends HasUuid> List<T> get( Class<T> clazz )
    {
        List<T> toReturn = new ArrayList<T>();
        for ( HasUuid entity : allEntities.values() )
        {
            if ( clazz.isInstance( entity ) )
            {
                toReturn.add( clazz.cast( entity ) );
            }
        }
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * @see com.getperka.flatpack.demo.gwt.persistence.Db#get(java.lang.Class, java.util.UUID)
     */
    @Override
    public <T extends HasUuid> T get( Class<T> clazz, UUID uuid )
    {
        return clazz.cast( allEntities.get( uuid ) );
    }

    @Override
    public <T extends HasUuid> T get( Class<T> clazz, String id )
    {
        return get( clazz, UUID.fromString( id ) );
    }

    /*
     * (non-Javadoc)
     * @see com.getperka.flatpack.demo.gwt.persistence.Db#persist(com.getperka.flatpack.HasUuid)
     */
    @Override
    public void persist( HasUuid entity )
    {
        allEntities.put( entity.getUuid(), entity );
    }
}
