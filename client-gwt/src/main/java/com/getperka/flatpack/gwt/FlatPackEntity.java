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
package com.getperka.flatpack.gwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * Encapsulates a return value and an optional collection of extra entities to include in the payload.
 *
 * @param <T> the type of value being returned
 */
public class FlatPackEntity<T>
{

    private Date lastModifiedTime;
    private Map<String, String> extraData;
    private Set<HasUuid> extraEntities;
    private Map<String, String> extraErrors;
    private Map<String, String> extraWarnings;
    private TraversalMode traversalMode = TraversalMode.SIMPLE;
    private T value;
    private Set<ConstraintViolation<?>> violations;

    /**
     * Provide type information from implicit parameterization.
     */
    public FlatPackEntity()
    {
    }

    /**
     * Populate the {@code errors} segment of the payload from {@link ConstraintViolation}.
     */
    public FlatPackEntity<T> addConstraintViolations( Set<? extends ConstraintViolation<?>> violations )
    {
        if ( this.violations == null )
        {
            this.violations = FlatPackCollections.setForIteration();
        }
        this.violations.addAll( violations );
        return this;
    }

    /**
     * Add a key-value pair to the {@code errors} segment of the payload.
     */
    public FlatPackEntity<T> addError( String key, String message )
    {
        if ( extraErrors == null )
        {
            extraErrors = FlatPackCollections.mapForIteration();
        }
        extraErrors.put( key, message );
        return this;
    }

    /**
     * Add entities not normally reachable from {@link #getValue()} into the payload.
     */
    public FlatPackEntity<T> addExtraEntities( Collection<? extends HasUuid> entities )
    {
        if ( extraEntities == null )
        {
            extraEntities = FlatPackCollections.setForIteration();
        }
        extraEntities.addAll( entities );
        return this;
    }

    /**
     * Add an entity not normally reachable from {@link #getValue()} into the payload.
     */
    public FlatPackEntity<T> addExtraEntity( HasUuid entity )
    {
        if ( extraEntities == null )
        {
            extraEntities = FlatPackCollections.setForIteration();
        }
        extraEntities.add( entity );
        return this;
    }

    /**
     * Add a key-value pair to the {@code warnings} segment of the payload.
     */
    public FlatPackEntity<T> addWarning( String key, String message )
    {
        if ( extraWarnings == null )
        {
            extraWarnings = FlatPackCollections.mapForIteration();
        }
        extraWarnings.put( key, message );
        return this;
    }

    /**
     * Returns an immutable view of the ConstraintViolations that have been added to the FlatPackEntity.
     */
    public Set<ConstraintViolation<?>> getConstraintViolations()
    {
        return violations == null ? Collections.<ConstraintViolation<?>> emptySet() : Collections
            .unmodifiableSet( violations );
    }

    /**
     * Returns an immutable view of the extra side-channel entries to include at the root of the payload.
     */
    public Map<String, String> getExtraData()
    {
        return extraData == null ? Collections.<String, String> emptyMap() : Collections.unmodifiableMap( extraData );
    }

    /**
     * Returns an immutable view of the extra entities to include in the returned payload.
     */
    public Set<HasUuid> getExtraEntities()
    {
        return extraEntities == null ? Collections.<HasUuid> emptySet() : Collections.unmodifiableSet( extraEntities );
    }

    /**
     * Returns an immutable view of the extra errors to include in the returned payload.
     */
    public Map<String, String> getExtraErrors()
    {
        return extraErrors == null ? Collections.<String, String> emptyMap() : Collections
            .unmodifiableMap( extraErrors );
    }

    /**
     * Returns an immutable view of the extra warnings to include in the returned payload.
     */
    public Map<String, String> getExtraWarnings()
    {
        return extraWarnings == null ? Collections.<String, String> emptyMap() : Collections
            .unmodifiableMap( extraWarnings );
    }

    /**
     * Clients with some amount of temporary storage may request that entities that were created or last modified before
     * a particular instant in time be excluded from the data section.
     */
    public Date getLastModifiedTime()
    {
        return lastModifiedTime;
    }

    public TraversalMode getTraversalMode()
    {
        return traversalMode;
    }

    /**
     * Returns the data previous passed to {@link #withValue(Object)}.
     */
    public T getValue()
    {
        return value;
    }

    /**
     * Allows extra string data to be returned at the top level of the payload.
     */
    public void putExtraData( String key, String value )
    {
        if ( extraData == null )
        {
            extraData = new TreeMap<String, String>();
        }
        extraData.put( key, value );
    }

    public FlatPackEntity<T> withLastModifiedTime( Date lastModified )
    {
        this.lastModifiedTime = lastModified;
        return this;
    }

    public FlatPackEntity<T> withTraversalMode( TraversalMode traversalMode )
    {
        this.traversalMode = traversalMode;
        return this;
    }

    public FlatPackEntity<T> withValue( T value )
    {
        this.value = value;
        return this;
    }
}
