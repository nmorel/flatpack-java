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
 * Primitive numeric support.
 * 
 * @param <N> the boxed Number type
 */
public abstract class NumberCodex<N extends Number>
    extends ValueCodex<N>
{
    protected NumberCodex()
    {
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
        return isZeroValue( value );
    }

    protected abstract boolean isZeroValue( N value );

    @Override
    public N readNotNull( Object element, DeserializationContext context )
        throws Exception
    {
        if ( !( element instanceof Double ) )
        {
            throw new IllegalArgumentException( "element is not a Double : " + element.getClass().getName() );
        }
        return readDoubleNotNull( (Double) element );
    }

    protected abstract N readDoubleNotNull( Double value );

    @Override
    public void writeNotNull( N object, SerializationContext context )
    {
        context.getWriter().value( object );
    }
}