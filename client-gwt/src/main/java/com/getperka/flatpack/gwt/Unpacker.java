package com.getperka.flatpack.gwt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.Codex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;

public class Unpacker
{
    private static Logger logger = Logger.getLogger( "Unpacker" );

    private TypeContext typeContext;

    public Unpacker( TypeContext typeContext )
    {
        this.typeContext = typeContext;
    }

    public <T> FlatPackEntity<T> unpack( String asJson, Codex<T> returnCodex )
    {
        logger.finest( "unpacking : " + asJson );

        // Hold temporary state for deserialization
        DeserializationContext context = new DeserializationContext();

        /*
         * Decoding is done as a two-pass operation since the runtime type of an allocated object cannot be swizzled.
         * The per-entity data is held as a semi-reified JavaScriptObject to be passed off to a Codex.
         */
        Map<HasUuid, JavaScriptObject> entityData = new LinkedHashMap<HasUuid, JavaScriptObject>();

        // The return value
        FlatPackEntity<T> toReturn = new FlatPackEntity<T>();

        if ( null == asJson || asJson.isEmpty() )
        {
            return toReturn;
        }

        // convert the json to JavaScriptObject
        JavaScriptObject asJso = JsonUtils.safeEval( asJson );
        if ( null == asJso )
        {
            return toReturn;
        }

        Object value = extractResponse( asJso, context, entityData, toReturn );

        for ( Map.Entry<HasUuid, JavaScriptObject> entry : entityData.entrySet() )
        {
            HasUuid entity = entry.getKey();
            EntityCodex<HasUuid> entityCodex = typeContext.getCodex( entity.getClass() );
            entityCodex.readProperties( entity, entry.getValue(), context );
        }

        toReturn.withValue( returnCodex.read( value, context ) );

        for ( Map.Entry<UUID, String> entry : context.getWarnings().entrySet() )
        {
            toReturn.addWarning( entry.getKey().toString(), entry.getValue() );
        }

        return toReturn;
    }

    private final native <T> Object extractResponse( JavaScriptObject object, DeserializationContext context,
                                                               Map<HasUuid, JavaScriptObject> entityData,
                                                               FlatPackEntity<T> toReturn )
    /*-{
		var returnValue = null;
		for ( var key in object) {
			if (object.hasOwnProperty(key)) {
				var value = object[key];
				if (key == "value") {
					returnValue = value;
				} else {
					this.@com.getperka.flatpack.gwt.Unpacker::extractResponse(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(key, value, context, entityData, toReturn);
				}
			}
		}
		return returnValue;
    }-*/;

    private final <T> void extractResponse( String key, JavaScriptObject value, DeserializationContext context,
                                            Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    {
        if ( "data".equals( key ) )
        {
            // data : { "fooEntity" : [ { ... }, { ... } ]
            extractData( value, context, entityData, toReturn );
        }
        else if ( "errors".equals( key ) )
        {
            // "errors" : { "path" : "problem", "path2" : "problem2" }
            extractErrors( value, context, entityData, toReturn );
        }
        else if ( isAString( value ) || isANumber( value ) )
        {
            // Save off any other simple properties
            toReturn.putExtraData( key, toString( value ) );
        }
    }

    private final native <T> void extractData( JavaScriptObject data, DeserializationContext context,
                                               Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    /*-{
		for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				var array = data[key];
				this.@com.getperka.flatpack.gwt.Unpacker::extractData(Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(key, array, context, entityData, toReturn);
			}
		}
    }-*/;

    private <T> void extractData( String simpleName, JsArray<JavaScriptObject> entityArray,
                                  DeserializationContext context, Map<HasUuid, JavaScriptObject> entityData,
                                  FlatPackEntity<T> toReturn )
    {
        context.pushPath( "allocating " + simpleName );

        try
        {
            // Find the Codex for the requested entity type
            Class<? extends HasUuid> clazz = typeContext.getClass( simpleName );
            if ( null == clazz )
            {
                logger.warning( "Could not find the class corresponding to '" + simpleName
                    + "'. Did you configure the TypeContext ?" );
                return;
            }

            EntityCodex<?> codex = typeContext.getCodex( clazz );
            if ( null == codex )
            {
                logger.warning( "Could not find the entity codex corresponding to the class '" + clazz.getName()
                    + "'. Did you configure the TypeContext ?" );
                return;
            }

            // Take the n-many property objects and stash them for later decoding
            for ( int i = 0; i < entityArray.length(); i++ )
            {
                JavaScriptObject entityAsJso = entityArray.get( i );
                HasUuid entity = codex.allocate( entityAsJso, context );
                toReturn.addExtraEntity( entity );
                entityData.put( entity, entityAsJso );
            }
        }
        finally
        {
            context.popPath();
        }
    }

    private final native <T> void extractErrors( JavaScriptObject errors, DeserializationContext context,
                                                 Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    /*-{
		for ( var key in errors) {
			if (errors.hasOwnProperty(key)) {
				var value = errors[key];
				if (this.@com.getperka.flatpack.gwt.Unpacker::isAString(Lcom/google/gwt/core/client/JavaScriptObject;)(value)
						|| this.@com.getperka.flatpack.gwt.Unpacker::isANumber(Lcom/google/gwt/core/client/JavaScriptObject;)(value)) {
					this.@com.getperka.flatpack.gwt.Unpacker::extractError(Ljava/lang/String;Ljava/lang/String;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(key, value, context, entityData, toReturn);
				}
			}
		}
    }-*/;

    private <T> void extractError( String key, String value, DeserializationContext context,
                                   Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    {
        toReturn.addError( key, value );
    }

    private final native boolean isAString( JavaScriptObject object )
    /*-{
		return (typeof object) == "string";
    }-*/;

    private final native boolean isANumber( JavaScriptObject object )
    /*-{
		return (typeof object) == "number";
    }-*/;

    private final native String toString( JavaScriptObject object )
    /*-{
		return object;
    }-*/;

}
