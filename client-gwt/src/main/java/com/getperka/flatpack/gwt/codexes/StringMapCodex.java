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

import java.util.Map;

import com.getperka.flatpack.FlatPackCollections;
import com.getperka.flatpack.gwt.JsonWriter;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A map of String to a arbitrary value.
 *
 * @param <V> the map value type
 */
public class StringMapCodex<V>
    extends Codex<Map<String, V>>
{
    private final Codex<V> valueCodex;

    StringMapCodex( Codex<V> valueCodex )
    {
        this.valueCodex = valueCodex;
    }

    @Override
    public String getPropertySuffix()
    {
        return valueCodex.getPropertySuffix();
    }

    @Override
    public Map<String, V> readNotNull( JavaScriptObject element, DeserializationContext context )
    {
        Map<String, V> toReturn = FlatPackCollections.mapForIteration();
        iterate( element, toReturn, context );
        return toReturn;
    }

    private final native void iterate( JavaScriptObject element, Map<String, V> toReturn, DeserializationContext context )
    /*-{
		for ( var key in element) {
			if (data.hasOwnProperty(key)) {
				var object = data[key];
				this.@com.getperka.flatpack.gwt.codexes.StringMapCodex::readObject(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/util/Map;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;)(key, object, toReturn, context);
			}
		}
    }-*/;

    private void readObject( String key, JavaScriptObject object, Map<String, V> toReturn,
                             DeserializationContext context )
    {
        context.pushPath( "[" + key + "]" );
        try
        {
            V value = valueCodex.read( object, context );
            toReturn.put( key, value );
        }
        catch ( Exception e )
        {
            context.fail( e );
        }
        finally
        {
            context.popPath();
        }
    }

    @Override
    public void scanNotNull( Map<String, V> object, SerializationContext context )
    {
        for ( Map.Entry<String, V> entry : object.entrySet() )
        {
            context.pushPath( "[" + entry.getKey() + "]" );
            try
            {
                valueCodex.scan( entry.getValue(), context );
            }
            finally
            {
                context.popPath();
            }
        }
    }

    @Override
    public void writeNotNull( Map<String, V> object, SerializationContext context )
    {
        JsonWriter writer = context.getWriter();
        writer.beginObject();
        for ( Map.Entry<String, V> entry : object.entrySet() )
        {
            String key = entry.getKey().toString();
            context.pushPath( "[" + key + "]" );
            try
            {
                writer.name( key );
                valueCodex.write( entry.getValue(), context );
            }
            finally
            {
                context.popPath();
            }
        }
        writer.endObject();
    }
}