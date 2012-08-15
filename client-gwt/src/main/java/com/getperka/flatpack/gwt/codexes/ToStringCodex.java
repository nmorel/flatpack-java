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
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Attempts to handle any value-like object that has a public, one-arg constructor that accepts a String or an Object.
 */
public class ToStringCodex<T>
    extends ValueCodex<T>
{
    public interface Constructor<T>
    {
        T newInstance( String value );
    }

    private final Constructor<T> constructor;

    public ToStringCodex( Constructor<T> constructor )
    {
        this.constructor = constructor;
    }

    @Override
    public T readNotNull( Object element, DeserializationContext context )
        throws Exception
    {
        // TODO
        return constructor.newInstance( readString( (JavaScriptObject) element ) );
    }

    @Override
    public void writeNotNull( T object, SerializationContext context )
    {
        context.getWriter().value( object.toString() );
    }
}