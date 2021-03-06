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

import java.util.Collection;

import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.json.JsonWriter;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A base type to serialize a collection of objects as an array.
 *
 * @param <T> the type of collection to be serialized
 * @param <V> the value type contained in the collection
 */
public abstract class CollectionCodex<T extends Collection<V>, V>
    extends Codex<T>
{
    private Codex<V> valueCodex;

    public CollectionCodex( Codex<V> valueCodex )
    {
        this.valueCodex = valueCodex;
    }

    @Override
    public String getPropertySuffix()
    {
        return valueCodex.getPropertySuffix();
    }

    @Override
    public T readNotNull( Object element, DeserializationContext context )
        throws Exception
    {
        if ( !( element instanceof JavaScriptObject ) )
        {
            throw new IllegalArgumentException( "element is not a JavaScriptObject : " + element.getClass().getName() );
        }

        T toReturn = newCollection();
        iterate( (JavaScriptObject) element, toReturn, context );
        return toReturn;
    }

    private final native void iterate( JavaScriptObject element, T toReturn, DeserializationContext context )
    /*-{
		for ( var i = 0, len = element.length; value = element[i], i < len; i++) {
			this.@com.getperka.flatpack.gwt.codexes.CollectionCodex::readValue(Ljava/lang/Object;ILjava/util/Collection;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;)(value, i, toReturn, context);
		}
    }-*/;

    private void readValue( Object value, int count, T toReturn, DeserializationContext context )
    {
        context.pushPath( "[" + count++ + "]" );
        toReturn.add( valueCodex.read( value, context ) );
        context.popPath();
    }

    @Override
    public void scanNotNull( T collection, SerializationContext context )
        throws Exception
    {
        int count = 0;
        for ( V t : collection )
        {
            context.pushPath( "[" + count++ + "]" );
            valueCodex.scan( t, context );
            context.popPath();
        }
    }

    @Override
    public void writeNotNull( T collection, SerializationContext context )
    {
        JsonWriter writer = context.getWriter();
        writer.beginArray();
        int count = 0;
        for ( V t : collection )
        {
            context.pushPath( "[" + count++ + "]" );
            valueCodex.write( t, context );
            context.popPath();
        }
        writer.endArray();
    }

    protected abstract T newCollection();

}