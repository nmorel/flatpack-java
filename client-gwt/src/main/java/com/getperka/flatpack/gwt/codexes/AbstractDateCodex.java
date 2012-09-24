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

import java.io.IOException;
import java.util.Date;

import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * Support for date values.
 */
public abstract class AbstractDateCodex<D extends Date>
    extends ValueCodex<D>
{
    protected static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( PredefinedFormat.ISO_8601 );

    @Override
    public D readNotNull( Object element, DeserializationContext context )
    {
        if ( element instanceof String )
        {
            return readAsString( (String) element );
        }
        else if ( element instanceof Double )
        {
            return readAsDouble( (Double) element );
        }
        else
        {
            throw new IllegalArgumentException( "element is not a String or a Double : " + element.getClass().getName() );
        }
    }

    protected abstract D readAsString( String element );

    protected abstract D readAsDouble( Double element );

    @Override
    public void writeNotNull( D object, SerializationContext context )
        throws IOException
    {
        context.getWriter().value( DATE_FORMAT.format( object ) );
    }
}