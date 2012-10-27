/*
 * #%L
 * FlatPack serialization code
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
package com.getperka.flatpack.gwt.ext;

import java.util.List;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.EntityResolver;

/**
 * Aggregates all user-provided resolvers into a single instance.
 */
class CompositeEntityResolver
    implements EntityResolver
{

    private final List<EntityResolver> resolvers;

    CompositeEntityResolver( List<EntityResolver> resolvers )
    {
        this.resolvers = resolvers;
    }

    /**
     * Add an additional {@link EntityResolver} which will be queried before any previously-added resolvers.
     */
    void addEntityResolver( EntityResolver resolver )
    {
        resolvers.add( 0, resolver );
    }

    @Override
    public <T extends HasUuid> T resolve( Class<T> clazz, UUID uuid )
        throws Exception
    {
        for ( EntityResolver resolver : resolvers )
        {
            T toReturn = resolver.resolve( clazz, uuid );
            if ( toReturn != null )
            {
                return toReturn;
            }
        }
        return null;
    }
}
