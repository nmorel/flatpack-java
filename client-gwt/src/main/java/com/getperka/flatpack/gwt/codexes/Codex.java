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

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.JsonWriter;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Implements type-specific encoding and decoding mechanisms.
 *
 * @param <T> the type of data that the instance operates on
 */
public abstract class Codex<T>
{

    /**
     * Returns a suffix to append to properties of the Codex's type.
     */
    public String getPropertySuffix()
    {
        return "";
    }

    /**
     * Returns {@code true} if the value is the default value for an uninitialized instance of a property with that
     * value.
     */
    public boolean isDefaultValue( T value )
    {
        return value == null;
    }

    /**
     * Reify the given {@link Object} into a Java value. If {@code} element is {@null} or represents a
     * Json {@code null} value, this method will return {@code null}, otherwise this method will delegate to
     * {@link #readNotNull(Object, DeserializationContext)}.
     *
     * @param element the element that contains the value to reify
     * @param context contextual information for the deserialization process
     * @return the requested value
     */
    public T read( Object element, DeserializationContext context )
    {
        if ( element == null )
        {
            return null;
        }
        context.pushPath( "(" + getClass().getName() + ".read())" );
        try
        {
            return readNotNull( element, context );
        }
        catch ( Exception e )
        {
            context.fail( e );
            return null;
        }
        finally
        {
            context.popPath();
        }
    }

    /**
     * Reify the given {@link Object} into a Java value.
     *
     * @param element the element that contains the value to reify
     * @param context contextual information for the deserialization process
     * @return the requested value
     * @throws Exception implementations of this method may throw arbitrary exceptions which will be reported by
     * {@link #read(Object, DeserializationContext)}
     */
    public abstract T readNotNull( Object element, DeserializationContext context )
        throws Exception;

    /**
     * Analyze a composite value to find additional entities to serialize. If {@code object} is non-null, this method
     * will call {@link #scanNotNull(Object, SerializationContext)}.
     *
     * @param object the value to scan
     * @param context the current serialization context
     */
    public void scan( T object, SerializationContext context )
    {
        if ( object == null )
        {
            return;
        }
        context.pushPath( "(" + getClass().getName() + ".scan())" );
        try
        {
            scanNotNull( object, context );
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

    /**
     * Analyze a composite value to find additional entities to serialize. Implementations of this method should call
     * {@link SerializationContext#add(HasUuid)} to enqueue the related entity for serialization.
     *
     * @param object the object to scan, which is guaranteed to be non-null
     * @param context the serialization context
     * @throws Exception implementations of this method may throw arbitrary exceptions which will be reported by
     * {@link #scan(Object, SerializationContext)}
     */
    public abstract void scanNotNull( T object, SerializationContext context )
        throws Exception;

    /**
     * Write a value into the serialization context. If object is {@code null}, writes a null into
     * {@link SerializationContext#getWriter()}, otherwise delegates to
     * {@link #writeNotNull(Object, SerializationContext)}.
     *
     * @param object a value to write into {@link SerializationContext#getWriter()}
     * @param context the serialization context
     */
    public void write( T object, SerializationContext context )
    {
        context.pushPath( "(" + getClass().getName() + ".write())" );
        try
        {
            JsonWriter writer = context.getWriter();
            if ( object == null )
            {
                writer.nullValue();
            }
            else
            {
                writeNotNull( object, context );
            }
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

    /**
     * Write a value into the serialization context.
     *
     * @param object a value to write into {@link SerializationContext#getWriter()}
     * @param context the serialization context
     * @throws Exception implementations of this method may throw arbitrary exceptions which will be reported by
     * {@link #write(Object, SerializationContext)}
     */
    public abstract void writeNotNull( T object, SerializationContext context )
        throws Exception;

    protected final native boolean readBoolean( JavaScriptObject jso )
    /*-{
		return jso === 1 || jso === "true";
    }-*/;

    protected final native String readString( JavaScriptObject jso )
    /*-{
		return jso;
    }-*/;

    protected final native double readNumber( JavaScriptObject jso )
    /*-{
		return jso;
    }-*/;

    protected final native JavaScriptObject getObject( JavaScriptObject jso, String key )
    /*-{
		return jso[key];
    }-*/;

    protected final native String getString( JavaScriptObject jso, String key )
    /*-{
		return jso[key];
    }-*/;

    protected final native boolean hasKey( JavaScriptObject jso, String key )
    /*-{
		return jso.hasOwnProperty(key);
    }-*/;
}
