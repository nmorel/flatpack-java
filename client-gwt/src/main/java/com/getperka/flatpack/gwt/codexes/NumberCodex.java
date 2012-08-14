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
 * Primitive numberic support.
 *
 * @param <N> the boxed Number type
 */
public class NumberCodex<N extends Number>
    extends ValueCodex<N>
{
    private final Class<N> clazz;

    NumberCodex( Class<N> clazz )
    {
        this.clazz = clazz;
    }

    /**
     * Returns {@code true} for {@code null} or {@code 0} values.
     */
    @Override
    public boolean isDefaultValue( N value )
    {
        if ( value == null )
        {
            return true;
        }
        if ( Float.class.equals( clazz ) || Double.class.equals( clazz ) )
        {
            return value.doubleValue() == 0.0;
        }
        return value.intValue() == 0;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public N readNotNull( JavaScriptObject element, DeserializationContext context )
    {
        Object toReturn;
        double value = readNumber( element );
        if ( Byte.class.equals( clazz ) )
        {
            toReturn = new Double( value ).byteValue();
        }
        else if ( Double.class.equals( clazz ) )
        {
            toReturn = value;
        }
        else if ( Float.class.equals( clazz ) )
        {
            toReturn = new Float( value );
        }
        else if ( Integer.class.equals( clazz ) )
        {
            toReturn = new Integer( new Double( value ).intValue() );
        }
        else if ( Long.class.equals( clazz ) )
        {
            toReturn = new Long( new Double( value ).longValue() );
        }
        else if ( Short.class.equals( clazz ) )
        {
            toReturn = new Short( new Double( value ).shortValue() );
        }
        else
        {
            throw new UnsupportedOperationException( "Unimplemented Number type " + clazz.getName() );
        }
        return (N) toReturn;
    }

    @Override
    public void writeNotNull( N object, SerializationContext context )
    {
        context.getWriter().value( object );
    }
}