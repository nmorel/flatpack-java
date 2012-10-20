package com.getperka.flatpack.gwt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.client.impl.BasePersistenceAware;
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

        // Hold temporary state for deserialization
        DeserializationContext context = new DeserializationContext();

        /*
         * Decoding is done as a two-pass operation since the runtime type of an allocated object cannot be swizzled.
         * The per-entity data is held as a semi-reified JavaScriptObject to be passed off to a Codex.
         */
        Map<HasUuid, JavaScriptObject> entityData = new LinkedHashMap<HasUuid, JavaScriptObject>();

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

        // Process metadata
        for ( EntityMetadata meta : toReturn.getMetadata() )
        {
            if ( meta.isPersistent() )
            {
                HasUuid entity = context.getEntity( meta.getUuid() );
                if ( entity instanceof BasePersistenceAware )
                {
                    ( (BasePersistenceAware) entity ).markPersistent();
                    // TODO find a better way to achieve this without casting to the implementation. Maybe an event bus created for the unpack operation.
                    ( (BasePersistenceAware) entity ).postUnpack();
                }
                else if ( entity instanceof PersistenceAware )
                {
                    ( (PersistenceAware) entity ).markPersistent();
                }
            }
        }

        context.runPostWork();

        return toReturn;
    }

    private final native <T> Object extractResponse( JavaScriptObject object, DeserializationContext context,
                                                     Map<HasUuid, JavaScriptObject> entityData,
                                                     FlatPackEntity<T> toReturn )
    /*-{
        var returnValue = null;
        for ( var key in object) {
            if (object.hasOwnProperty(key)) {
                if (key == "value") {

                    returnValue = object[key];

                } else if (key == "data") {

                    // "data" : { "fooEntity" : [ { ... }, { ... } ]
                    this.@com.getperka.flatpack.gwt.Unpacker::extractData(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(object[key], context, entityData, toReturn);

                } else if (key == "metadata") {

                    // "metadata" : { "uuid" : "5ade6c3d-8ee4-4cc0-8077-80f46af2bb00", "persistent" : true } ]
                    this.@com.getperka.flatpack.gwt.Unpacker::extractMetadata(Lcom/google/gwt/core/client/JsArray;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(object[key], context, entityData, toReturn);

                } else if (key == "errors") {

                    // "errors" : { "path" : "problem", "path2" : "problem2" }
                    this.@com.getperka.flatpack.gwt.Unpacker::extractErrors(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(object[key], context, entityData, toReturn);

                } else if (key == "warnings") {

                    // "warnings" : { "path" : "problem", "path2" : "problem2" }
                    this.@com.getperka.flatpack.gwt.Unpacker::extractWarnings(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(object[key], context, entityData, toReturn);

                } else {

                    // Save off any other simple properties
                    var value = jso[key];
                    if (value != null) {
                        var extraData;
                        var typeValue = typeof (value);
                        if (typeValue == "number") {
                            extraData = @java.lang.Double::valueOf(D)(value);
                        } else if (typeValue == "boolean") {
                            extraData = @java.lang.Boolean::valueOf(Z)(value);
                        } else {
                            extraData = value;
                        }
                        this.@com.getperka.flatpack.gwt.Unpacker::extractExtraData(Ljava/lang/String;Ljava/lang/Object;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(key, extraData, context, entityData, toReturn);
                    }

                }
            }
        }
        return returnValue;
    }-*/;

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

    private <T> void extractMetadata( JsArray<JavaScriptObject> metadataArray, DeserializationContext context,
                                      Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    {
        if ( metadataArray.length() == 0 )
        {
            return;
        }

        EntityCodex<EntityMetadata> metaCodex = EntityMetadata.getCodex();

        for ( int i = 0; i < metadataArray.length(); i++ )
        {
            EntityMetadata meta = new EntityMetadata();
            JavaScriptObject entityAsJso = metadataArray.get( i );
            metaCodex.readProperties( meta, entityAsJso, context );
            toReturn.addMetadata( meta );
        }
    }

    private final native <T> void extractErrors( JavaScriptObject errors, DeserializationContext context,
                                                 Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    /*-{
        for ( var key in errors) {
            if (errors.hasOwnProperty(key)) {
                var value = errors[key];
                if ((typeof value) == "string") {
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

    private final native <T> void extractWarnings( JavaScriptObject errors, DeserializationContext context,
                                                   Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    /*-{
        for ( var key in errors) {
            if (errors.hasOwnProperty(key)) {
                var value = errors[key];
                if ((typeof value) == "string") {
                    this.@com.getperka.flatpack.gwt.Unpacker::extractWarning(Ljava/lang/String;Ljava/lang/String;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;Ljava/util/Map;Lcom/getperka/flatpack/gwt/FlatPackEntity;)(key, value, context, entityData, toReturn);
                }
            }
        }
    }-*/;

    private <T> void extractWarning( String key, String value, DeserializationContext context,
                                     Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    {
        toReturn.addWarning( key, value );
    }

    private <T> void extractExtraData( String key, Object value, DeserializationContext context,
                                       Map<HasUuid, JavaScriptObject> entityData, FlatPackEntity<T> toReturn )
    {
        toReturn.putExtraData( key, value.toString() );
    }

}
