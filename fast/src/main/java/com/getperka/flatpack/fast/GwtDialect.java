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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.getperka.flatpack.fast.gwt.CodexDescription;
import com.getperka.flatpack.fast.gwt.PropertyCodexDescription;
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
            List<PropertyCodexDescription> propertiesCodex = new ArrayList<PropertyCodexDescription>();
            for ( Property property : entity.getProperties() )
            {
                propertiesCodex.add( new PropertyCodexDescription( property ) );
            }

            ST entityCodexST =
                group.getInstanceOf( "entityCodex" ).add( "entity", entity ).add( "properties", propertiesCodex )
                    .add( "packageName", packageName );
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
        // TODO Api GWT
        // ST apiST =
        // group.getInstanceOf( "api" ).add( "api", api ).add( "packageName", packageName )
        // .add( "namePrefix", namePrefix ).add( "apiIsPublic", apiIsPublic );
        // render( apiST, packageDir, namePrefix + "Api" );

        // Render the TypeContext
        ST typeContextST =
            group.getInstanceOf( "typeContext" ).add( "allEntities", allEntities.values() )
                .add( "packageName", packageName ).add( "namePrefix", namePrefix );
        render( typeContextST, packageDir, namePrefix + "TypeContext" );

        // Render the EntityCodexFactory
        ST entityCodexFactoryST =
            group.getInstanceOf( "entityCodexFactory" ).add( "allEntities", allEntities.values() )
                .add( "packageName", packageName );
        render( entityCodexFactoryST, packageDir, "EntityCodexFactory" );
    }

    @Override
    protected STGroup loadGroup( String template )
    {
        STGroup group = super.loadGroup( template );

        // CodexDescription is used to render the instantiation of codex in the properties
        group.registerRenderer( CodexDescription.class, new AttributeRenderer() {
            @Override
            public String toString( Object o, String formatString, Locale locale )
            {
                return toString( ( (CodexDescription) o ).getType() );
            }

            protected String toString( Type type )
            {

                switch ( type.getJsonKind() )
                {
                    case ANY:
                        return "";
                    case BOOLEAN:
                        return "new BooleanCodex()";
                    case DOUBLE:
                        return "new DoubleCodex()";
                    case INTEGER:
                        return "new IntegerCodex()";
                    case LIST:
                        return "new ListCodex<" + toStringType( type.getListElement() ) + ">("
                            + toString( type.getListElement() ) + ")";
                    case MAP:
                        String stringKeyType = toStringType( type.getMapKey() );
                        String stringValueType = toStringType( type.getMapValue() );
                        if ( String.class.getCanonicalName().equals( stringKeyType ) )
                        {
                            return "new StringMapCodex<" + stringValueType + ">(" + toString( type.getMapValue() )
                                + ")";
                        }
                        else
                        {
                            // EntityMapCodex
                            return "new EntityMapCodex<" + stringKeyType + ", " + stringValueType + ">("
                                + toString( type.getMapKey() ) + "," + toString( type.getMapValue() ) + ")";
                        }
                    case NULL:
                        return "new VoidCodex()";
                    case STRING:
                    {
                        // Look for the presence of enum values
                        if ( type.getEnumValues() != null )
                        {
                            return "new EnumCodex<" + simpleName( type.getName() ) + ">(" + simpleName( type.getName() )
                                + ".class)";
                        }

                        // Any other named type must be an entity type
                        if ( type.getName() != null )
                        {
                            return "EntityCodexFactory.get().get" + simpleName( type.getName() ) + "Codex()";
                        }

                        // Look for a type hint
                        TypeHint hint = type.getTypeHint();
                        if ( hint != null )
                        {
                            // ToStringCodex like BigDecimalCodex, BigIntegerCodex, TypeHintCodex
                            String value = hint.getValue();
                            return "new " + value.substring( value.lastIndexOf( '.' ) + 1 ) + "Codex()";
                        }

                        // Otherwise it must be a plain string
                        return "new StringCodex()";
                    }
                }
                throw new UnsupportedOperationException( "Unknown JsonKind " + type.getJsonKind() );
            }

            private String simpleName( String typeName )
            {
                return typePrefix + upcase( typeName );
            }

            protected String toStringType( Type type )
            {
                switch ( type.getJsonKind() )
                {
                    case ANY:
                        return Object.class.getCanonicalName();
                    case BOOLEAN:
                        return Boolean.class.getCanonicalName();
                    case DOUBLE:
                        return Double.class.getCanonicalName();
                    case INTEGER:
                        return Integer.class.getCanonicalName();
                    case LIST:
                        return List.class.getCanonicalName() + "<" + toStringType( type.getListElement() ) + ">";
                    case MAP:
                        return Map.class.getCanonicalName() + "<" + toStringType( type.getMapKey() ) + ","
                            + toStringType( type.getMapValue() ) + ">";
                    case NULL:
                        return Void.class.getCanonicalName();
                    case STRING:
                    {
                        // Look for the presence of enum values
                        if ( type.getEnumValues() != null )
                        {
                            return simpleName( type.getName() );
                        }
                        // Any other named type must be an entity type
                        if ( type.getName() != null )
                        {
                            // Allow type to be overridden
                            return simpleName( type.getName() );
                        }
                        // Look for a type hint
                        TypeHint hint = type.getTypeHint();
                        if ( hint != null )
                        {
                            return hint.getValue();
                        }
                        // Otherwise it must be a plain string
                        return String.class.getCanonicalName();
                    }
                }
                throw new UnsupportedOperationException( "Unknown JsonKind " + type.getJsonKind() );
            }
        } );

        return group;
    }

    @Override
    public String getDialectName()
    {
        return "gwt";
    }
}
