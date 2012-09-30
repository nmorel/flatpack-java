/*
 * #%L
 * FlatPack Automatic Source Tool
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
package com.getperka.flatpack.fast;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * Generates simple GWT POJO representations of a FlatPack API.
 */
public class GwtDialect
    extends JavaDialect
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public void generate( ApiDescription api, File outputDir )
        throws IOException
    {
        STGroup group = loadGroup( "gwt.stg" );
        loadConcreteTypeMap();

        File packageDir = new File( outputDir, packageName.replace( '.', '/' ) );
        if ( !packageDir.isDirectory() && !packageDir.mkdirs() )
        {
            logger.error( "Could not create output directory {}", packageDir.getPath() );
            return;
        }

        Map<String, EntityDescription> allEntities = FlatPackCollections.mapForIteration();
        for ( EntityDescription entity : api.getEntities() )
        {
            allEntities.put( entity.getTypeName(), entity );
            for ( Iterator<Property> it = entity.getProperties().iterator(); it.hasNext(); )
            {
                Property prop = it.next();
                if ( "uuid".equals( prop.getName() ) )
                {
                    // Crop the UUID property
                    it.remove();
                }
                else if ( !prop.getEnclosingTypeName().equals( entity.getTypeName() ) )
                {
                    // Remove properties not declared in the current type
                    it.remove();
                }
            }
        }
        // Ensure that the "real" implementations are used
        allEntities.remove( "baseHasUuid" );
        allEntities.remove( "hasUuid" );

        // Render entities + codex
        for ( EntityDescription entity : allEntities.values() )
        {
            // Render entity
            ST entityST = group.getInstanceOf( "entity" ).add( "entity", entity ).add( "packageName", packageName );

            String simpleName = typePrefix + upcase( entity.getTypeName() );
            if ( baseTypes.contains( entity.getTypeName() ) )
            {
                simpleName += "Base";
            }
            render( entityST, packageDir, simpleName );

            // Render EntityCodex
            ST entityCodexST =
                group.getInstanceOf( "entityCodex" ).add( "entity", entity ).add( "packageName", packageName );
            render( entityCodexST, packageDir, simpleName + "Codex" );
        }

        // Render referenced enumerations
        for ( Type enumType : usedEnums )
        {
            ST enumST = group.getInstanceOf( "enum" ).add( "enum", enumType ).add( "packageName", packageName );

            render( enumST, packageDir, typePrefix + upcase( enumType.getName() ) );
        }

        String namePrefix = upcase( packageName.substring( packageName.lastIndexOf( '.' ) + 1 ) );

        // Render the Api convenience class
        ST apiST =
            group.getInstanceOf( "api" ).add( "api", api ).add( "packageName", packageName )
                .add( "namePrefix", namePrefix ).add( "apiIsPublic", apiIsPublic );
        render( apiST, packageDir, namePrefix + "Api" );

        // Render the TypeContext
        ST typeContextST =
            group.getInstanceOf( "typeContext" ).add( "allEntities", allEntities.values() )
                .add( "packageName", packageName ).add( "namePrefix", namePrefix );
        render( typeContextST, packageDir, namePrefix + "TypeContext" );

        // Render the BaseCodexFactory
        ST baseCodexFactoryST =
            group.getInstanceOf( "codexFactory" ).add( "allEntities", allEntities.values() )
                .add( "packageName", packageName );
        render( baseCodexFactoryST, packageDir, "BaseCodexFactory" );
    }

    @Override
    protected STGroup loadGroup( String template )
    {
        STGroup group = super.loadGroup( template );

        group.registerModelAdaptor( Type.class, new ObjectModelAdaptor() {
            public Object getProperty( Interpreter interp, ST self, Object o, Object property, String propertyName )
                throws STNoSuchPropertyException
            {
                Type type = (Type) o;
                if ( "codex".equals( propertyName ) )
                {
                    return toCodexString( type );
                }
                return super.getProperty( interp, self, o, property, propertyName );
            };
        } );

        Map<String, Object> namesMap = group.rawGetDictionary( "names" );
        namesMap.put( "ApiBase", "com.getperka.flatpack.gwt.client.impl.ApiBase" );
        namesMap.put( "FlatPackRequest", "com.getperka.flatpack.gwt.client.FlatPackRequest" );
        namesMap.put( "FlatPackRequestBase", "com.getperka.flatpack.gwt.client.impl.FlatPackRequestBase" );
        namesMap.put( "AbstractCodexFactory", "com.getperka.flatpack.gwt.codexes.AbstractCodexFactory" );

        return group;
    }

    @Override
    public String getDialectName()
    {
        return "gwt";
    }

    protected String toCodexString( Type type )
    {
        // Allow a TypeHint to override any interpretation of the Type
        if ( type.getTypeHint() != null )
        {
            String typeHint = type.getTypeHint().getValue();

            StringBuilder builder = new StringBuilder( "BaseCodexFactory.get()." );

            if ( java.util.Date.class.getCanonicalName().equals( typeHint ) )
            {
                builder.append( "date" );
            }
            else if ( java.sql.Date.class.getCanonicalName().equals( typeHint ) )
            {
                builder.append( "sqlDate" );
            }
            else if ( Time.class.getCanonicalName().equals( typeHint ) )
            {
                builder.append( "sqlTime" );
            }
            else if ( Timestamp.class.getCanonicalName().equals( typeHint ) )
            {
                builder.append( "sqlTimestamp" );
            }
            else if ( UUID.class.getCanonicalName().equals( typeHint ) )
            {
                builder.append( "uuid" );
            }
            else
            {
                // all other types like byte, integer, bigdecimal, etc.
                builder.append( toLowerCamel( typeHint.substring( typeHint.lastIndexOf( '.' ) + 1 ) ) );
            }
            builder.append( "Codex()" );
            return builder.toString();
        }

        switch ( type.getJsonKind() )
        {
            case ANY:
                return "BaseCodexFactory.get().jsonCodex()";
            case BOOLEAN:
                return "BaseCodexFactory.get().booleanCodex()";
            case DOUBLE:
                return "BaseCodexFactory.get().doubleCodex()";
            case INTEGER:
                return "BaseCodexFactory.get().integerCodex()";
            case LIST:
                return "BaseCodexFactory.get().listCodex(" + toCodexString( type.getListElement() ) + ")";
            case MAP:
                if ( null == type.getMapKey().getTypeHint() && null == type.getMapKey().getEnumValues()
                    && null == type.getMapKey().getName() )
                {
                    return "BaseCodexFactory.get().stringMapCodex(" + toCodexString( type.getMapValue() ) + ")";
                }
                else
                {
                    // EntityMapCodex
                    return "BaseCodexFactory.get().entityMapCodex(" + toCodexString( type.getMapKey() ) + ","
                        + toCodexString( type.getMapValue() ) + ")";
                }
            case NULL:
                return "BaseCodexFactory.get().voidCodex()";
            case STRING:
            {
                // Look for the presence of enum values
                if ( type.getEnumValues() != null )
                {
                    return "BaseCodexFactory.get().enumCodex(" + typeToClassName( type.getName() ) + ".class)";
                }

                // Any other named type must be an entity type
                if ( type.getName() != null )
                {
                    return "BaseCodexFactory.get()." + type.getName() + "Codex()";
                }

                // Otherwise it must be a plain string
                return "BaseCodexFactory.get().stringCodex()";
            }
        }
        throw new UnsupportedOperationException( "Unknown JsonKind " + type.getJsonKind() );
    }

    private String typeToClassName( String typeName )
    {
        return toUpperCamel( typePrefix + toUpperCamel( typeName ) );
    }

    /**
     * @return the string to Java and C++ class naming convention, e.g., "UpperCamel".
     */
    private String toUpperCamel( String string )
    {
        if ( null == string || string.isEmpty() )
        {
            return string;
        }
        else if ( string.length() == 1 )
        {
            return string.toLowerCase();
        }
        else
        {
            return string.substring( 0, 1 ).toUpperCase() + string.substring( 1 );
        }
    }

    /**
     * @return the string to Java variable naming convention, e.g., "lowerCamel".
     */
    private String toLowerCamel( String string )
    {
        if ( null == string || string.isEmpty() )
        {
            return string;
        }
        else if ( string.length() == 1 )
        {
            return string.toLowerCase();
        }
        else
        {
            return string.substring( 0, 1 ).toLowerCase() + string.substring( 1 );
        }
    }

}
