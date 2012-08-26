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
package com.getperka.flatpack;

import java.util.UUID;

/**
 * Utility base class that provides lazy UUID generation.
 * <p>
 * <b>GWT Implementation : removed the isAssignableFrom which is not supported by GWT</b>
 * </p>
 */
public class BaseHasUuid
    implements HasUuid
{
    private UUID uuid;

    /**
     * Equal to any {@link HasUuid} type with the same UUID and where one type is assignable to another.
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( !( obj instanceof HasUuid ) )
        {
            return false;
        }
        return getUuid().equals( ( (HasUuid) obj ).getUuid() );
        // TODO isAssignableFrom isn't supported by GWT :
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=4663
        // && (getClass().isAssignableFrom(obj.getClass()) ||
        // obj.getClass().isAssignableFrom(getClass()));
    }

    /**
     * Generates a new UUID by calling {@link #defaultUuid()} if a value has not been previously set.
     */
    @Override
    public UUID getUuid()
    {
        if ( uuid == null )
        {
            uuid = defaultUuid();
        }
        return uuid;
    }

    /**
     * Calls {@code getUuid().hashCode()}.
     */
    @Override
    public int hashCode()
    {
        return getUuid().hashCode();
    }

    @Override
    public void setUuid( UUID uuid )
    {
        this.uuid = uuid;
    }

    /**
     * Returns a random UUID. Subclasses can override this method to change the value returned by {@link #getUuid()}.
     */
    protected UUID defaultUuid()
    {
        return UUID.randomUUID();
    }
}
