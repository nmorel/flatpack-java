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
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.Property;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.json.JsonWriter;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @param <T>
 */
public abstract class EntityCodex<T extends HasUuid>
    extends Codex<T>
{
    private List<Property<T, ?>> properties;

    /**
     * Initialize the codex
     */
    public void init()
    {
        this.properties = FlatPackCollections.listForAny();
        this.properties.add( new Property<T, UUID>( "uuid", new UUIDCodex() ) {

            @Override
            public UUID getValue( T object )
            {
                return object.getUuid();
            }

            @Override
            public void setValue( T object, UUID value )
            {
                object.setUuid( value );
            }
        } );
        initProperties( properties );
    }

    /**
     * Initialize the list of properties
     * 
     * @param properties the list of properties to fill
     */
    protected abstract void initProperties( List<Property<T, ?>> properties );

    /**
     * Performs a minimal amount of work to create an empty stub object to fill in later.
     * 
     * @param element a JsonObject containing a {@code uuid} property. If {@code null}, a randomly-generated UUID will
     * be assigned to the allocated object
     * @param context this method will call {@link DeserializationContext#putEntity} to store the newly-allocated entity
     */
    public T allocate( JavaScriptObject element, DeserializationContext context )
    {
        String uuidElement = getString( element, "uuid" );
        if ( uuidElement == null )
        {
            context.fail( new IllegalArgumentException( "Data entry missing uuid:\n" + element.toString() ) );
        }
        UUID uuid = UUID.fromString( uuidElement );
        T toReturn = allocate( uuid, context, true );

        return toReturn;
    }

    @Override
    public String getPropertySuffix()
    {
        return "Uuid";
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public T readNotNull( Object element, DeserializationContext context )
    {
        if ( !( element instanceof String ) )
        {
            throw new IllegalArgumentException( "element is not a String : " + element.getClass().getName() );
        }
        UUID uuid = UUID.fromString( (String) element );
        HasUuid entity = context.getEntity( uuid );
        /*
         * If the UUID is a reference to an entity that isn't in the data section, delegate to the allocate() method.
         * The entity will either be provided by an EntityResolver or a blank entity will be created if possible.
         */
        if ( entity == null )
        {
            entity = allocate( uuid, context, true );
        }

        return (T) entity;
    }

    public void readProperties( T object, JavaScriptObject element, DeserializationContext context )
    {
        context.pushPath( "(EntityCodex.readProperties())" + object.getUuid() );

        try
        {
            for ( Property<T, ?> prop : properties )
            {
                readProperty( object, element, context, prop );
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

    @SuppressWarnings( "unchecked" )
    private <V> void readProperty( T object, JavaScriptObject element, DeserializationContext context,
                                   Property<T, V> prop )
    {
        String simplePropertyName = prop.getName();
        context.pushPath( "." + simplePropertyName );
        try
        {
            V value;
            if ( prop.isEmbedded() )
            {
                /*
                 * Embedded objects are never referred to by uuid in the payload, so an instance will need to be
                 * allocated before reading in the properties.
                 */
                EntityCodex<HasUuid> codex = (EntityCodex<HasUuid>) prop.getCodex();
                HasUuid embedded = codex.allocate( UUID.randomUUID(), context, false );
                codex.readProperties( embedded, element, context );
                value = (V) embedded;
            }
            else
            {
                Codex<?> codex = prop.getCodex();

                // merchant would become merchantUuid
                String payloadPropertyName = simplePropertyName + codex.getPropertySuffix();

                // Ignore undefined property values, while allowing explicit nullification
                if ( !hasKey( element, payloadPropertyName ) )
                {
                    return;
                }

                value = (V) codex.read( getObject( element, payloadPropertyName ), context );
            }

            if ( value == null && prop.isPrimitive() )
            {
                return;
            }

            // Perhaps set the other side of a OneToMany relationship
            // TODO
            // Property impliedPropery = prop.getImpliedProperty();
            // if ( impliedPropery != null && value != null )
            // {
            // // Ensure that any linked property is also mutable
            // if ( !impliedPropery.maySet( roles ) || !checkAccess( value, context ) )
            // {
            // context.addWarning( object,
            // "Ignoring property %s because the inverse relationship (%s) may not be set", prop.getName(),
            // impliedPropery.getName() );
            // return;
            // }
            // context.addPostWork( new ImpliedPropertySetter( context, impliedPropery, value, object ) );
            // }

            // Set the value
            prop.setValue( object, value );

            // Record the value as having been set
            context.addModified( object, prop );
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
    public void scanNotNull( T object, SerializationContext context )
        throws Exception
    {
        if ( context.add( object ) )
        {
            traverse( object, false, context, null );
        }
    }

    /**
     * For debugging use only.
     */
    @Override
    public String toString()
    {
        return getName();
    }

    public abstract String getName();

    @Override
    public void writeNotNull( T object, SerializationContext context )
        throws IOException
    {
        JsonWriter writer = context.getWriter();
        writer.value( object.getUuid().toString() );
    }

    public void writeProperties( T object, SerializationContext context )
    {
        context.pushPath( "(EntityCodex.writeProperties())" + object.getUuid() );
        try
        {
            JsonWriter writer = context.getWriter();
            writer.beginObject();
            traverse( object, false, context, writer );
            writer.endObject();
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

    private T allocate( UUID uuid, DeserializationContext context, boolean useResolvers )
    {
        T toReturn = null;
        boolean resolved = false;

        // TODO ?
        // Possibly delegate to injected resolvers
        // if ( useResolvers )
        // {
        // try
        // {
        // toReturn = entityResolver.resolve( clazz, uuid );
        // }
        // catch ( Exception e )
        // {
        // context.fail( e );
        // }
        // if ( toReturn != null )
        // {
        // resolved = true;
        // }
        // }

        // Otherwise try to construct a new instance
        if ( toReturn == null )
        {
            toReturn = createInstance();
        }

        toReturn.setUuid( uuid );
        context.putEntity( uuid, toReturn, resolved );
        return toReturn;
    }

    protected abstract T createInstance();

    /**
     * A scuzzy method that either scans, writes, or reads based on whether or not {@code writer} is null
     */
    private void traverse( T object, boolean isEmbedded, SerializationContext context, JsonWriter writer )
        throws Exception
    {
        if ( object == null )
        {
            return;
        }

        Set<String> dirtyPropertyNames;
        if ( object instanceof PersistenceAware )
        {
            dirtyPropertyNames = FlatPackCollections.setForIteration();
            // Always write out uuid
            dirtyPropertyNames.add( "uuid" );
            dirtyPropertyNames.addAll( ( (PersistenceAware) object ).dirtyPropertyNames() );
        }
        else
        {
            dirtyPropertyNames = null;
        }

        // Write all properties
        for ( Property<T, ?> prop : properties )
        {
            // Ignore OneToMany type properties unless specifically requested
            if ( prop.isDeepTraversalOnly() && !context.getTraversalMode().writeAllProperties() )
            {
                continue;
            }
            // Don't emit a redundant uuid property
            if ( isEmbedded && "uuid".equals( prop.getName() ) )
            {
                continue;
            }
            // Skip clean properties
            if ( dirtyPropertyNames != null && !dirtyPropertyNames.contains( prop.getName() ) )
            {
                continue;
            }
            context.pushPath( "." + prop.getName() );
            try
            {
                // Extract the value
                Object value = prop.getValue( object );

                // Figure out how to interpret the value
                @SuppressWarnings( "unchecked" )
                Codex<Object> codex = (Codex<Object>) prop.getCodex();

                if ( prop.isEmbedded() )
                {
                    @SuppressWarnings( "unchecked" )
                    EntityCodex<HasUuid> embeddedCodex = (EntityCodex<HasUuid>) prop.getCodex();
                    embeddedCodex.traverse( (HasUuid) value, true, context, writer );
                }
                else if ( writer == null )
                {
                    // Either scan or write the property value
                    codex.scan( value, context );
                }
                else if ( !( prop.isSuppressDefaultValue() && codex.isDefaultValue( value ) ) )
                {
                    // Write the value of the property, optionally suppressing default values
                    writer.name( prop.getName() + codex.getPropertySuffix() );
                    codex.write( value, context );
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
    }
}