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

import com.getperka.flatpack.gwt.JsonWriter;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Serializes Java arrays as a json array.
 *
 * @param <T> the type of data contained in the array
 */
public class ArrayCodex<T>
    extends Codex<T[]>
{
    private final Codex<T> valueCodex;

    ArrayCodex( Codex<T> valueCodex )
    {
        this.valueCodex = valueCodex;
    }

    @Override
    public String getPropertySuffix()
    {
        return valueCodex.getPropertySuffix();
    }

    @Override
    public T[] readNotNull( JavaScriptObject element, DeserializationContext context )
        throws Exception
    {
        @SuppressWarnings( "unchecked" )
        T[] toReturn = (T[]) new Object[arraySize( element )];
        iterate( element, toReturn, context );
        return toReturn;
    }

    private final native int arraySize( JavaScriptObject obj )
    /*-{
		return obj.length;
    }-*/;

    private final native void iterate( JavaScriptObject element, T[] toReturn, DeserializationContext context )
    /*-{
		var count = 0;
		for ( var object in element) {
			this.@com.getperka.flatpack.gwt.codexes.ArrayCodex::readValue(Lcom/google/gwt/core/client/JavaScriptObject;I[Ljava/lang/Object;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;)(object, count, toReturn, context);
			count++;
		}
    }-*/;

    private void readValue( JavaScriptObject value, int count, T[] toReturn, DeserializationContext context )
    {
        context.pushPath( "[" + count + "]" );
        toReturn[count] = valueCodex.read( value, context );
        context.popPath();
    }

    @Override
    public void scanNotNull( T[] object, SerializationContext context )
    {
        int count = 0;
        for ( T t : object )
        {
            context.pushPath( "[" + count++ + "]" );
            valueCodex.scan( t, context );
            context.popPath();
        }
    }

    @Override
    public void writeNotNull( T[] object, SerializationContext context )
        throws IOException
    {
        JsonWriter writer = context.getWriter();
        writer.beginArray();
        int count = 0;
        for ( T t : object )
        {
            context.pushPath( "[" + count++ + "]" );
            valueCodex.write( t, context );
            context.popPath();
        }
        writer.endArray();
    }
}