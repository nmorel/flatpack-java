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
import java.util.Map;

import com.getperka.flatpack.FlatPackCollections;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.JsonWriter;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provides a mapping of entities to arbitrary values.
 */
public class EntityMapCodex<K extends HasUuid, V>
    extends Codex<Map<K, V>>
{
    private final EntityCodex<K> keyCodex;
    private final Codex<V> valueCodex;

    public EntityMapCodex( EntityCodex<K> keyCodex, Codex<V> valueCodex )
    {
        this.keyCodex = keyCodex;
        this.valueCodex = valueCodex;
    }

    @Override
    public String getPropertySuffix()
    {
        return valueCodex.getPropertySuffix();
    }

    @Override
    public Map<K, V> readNotNull( Object element, DeserializationContext context )
        throws IOException
    {
        if ( !( element instanceof JavaScriptObject ) )
        {
            throw new IllegalArgumentException( "element is not a JavaScriptObject : " + element.getClass().getName() );
        }

        Map<K, V> toReturn = FlatPackCollections.mapForIteration();
        iterate( (JavaScriptObject) element, toReturn, context );
        return toReturn;
    }

    private final native void iterate( JavaScriptObject element, Map<K, V> toReturn, DeserializationContext context )
    /*-{
		for ( var key in element) {
			if (element.hasOwnProperty(key)) {
				var object = element[key];
				this.@com.getperka.flatpack.gwt.codexes.EntityMapCodex::readObject(Ljava/lang/String;Ljava/lang/Object;Ljava/util/Map;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;)(key, object, toReturn, context);
			}
		}
    }-*/;

    private void readObject( String keyAsString, Object valueObject, Map<K, V> toReturn, DeserializationContext context )
    {
        context.pushPath( "." + keyAsString );
        try
        {
            K key = keyCodex.read( keyAsString, context );
            V value = valueCodex.read( valueObject, context );
            toReturn.put( key, value );
        }
        finally
        {
            context.popPath();
        }
    }

    @Override
    public void scanNotNull( Map<K, V> object, SerializationContext context )
    {
        for ( Map.Entry<K, V> entry : object.entrySet() )
        {
            context.pushPath( "." + entry.getKey().getUuid() );
            try
            {
                keyCodex.scan( entry.getKey(), context );
                valueCodex.scan( entry.getValue(), context );
            }
            finally
            {
                context.popPath();
            }
        }
    }

    @Override
    public void writeNotNull( Map<K, V> object, SerializationContext context )
        throws IOException
    {
        JsonWriter writer = context.getWriter();
        writer.beginObject();
        for ( Map.Entry<K, V> entry : object.entrySet() )
        {
            String key = entry.getKey().getUuid().toString();
            context.pushPath( "." + key );
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