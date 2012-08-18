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
package com.getperka.flatpack.gwt.codexes;

import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;

/**
 * Attempts to handle any value-like object that has a public, one-arg constructor that accepts a String or an Object.
 */
public abstract class ToStringCodex<T>
    extends ValueCodex<T>
{
    protected ToStringCodex()
    {
    }

    @Override
    public T readNotNull( Object element, DeserializationContext context )
        throws Exception
    {
        if ( !( element instanceof String ) )
        {
            throw new IllegalArgumentException( "element is not a String : " + element.getClass().getName() );
        }
        return createNewInstance( (String) element );
    }

    /**
     * Create a new instance
     *
     * @param value String value
     * @return the new instance
     */
    protected abstract T createNewInstance( String value );

    @Override
    public void writeNotNull( T object, SerializationContext context )
    {
        context.getWriter().value( object.toString() );
    }
}