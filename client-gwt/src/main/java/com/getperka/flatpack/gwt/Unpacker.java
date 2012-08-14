package com.getperka.flatpack.gwt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
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

    public <T> T unpack( String asJson )
    {
        logger.finest( "unpacking : " + asJson );

        DeserializationContext context = new DeserializationContext();

        FlatPackJso asJso = JsonUtils.safeEval( asJson );
        JavaScriptObject data = asJso.getData();
        JavaScriptObject value = asJso.getValue();
        JavaScriptObject errors = asJso.getErrors();

        Map<HasUuid, JavaScriptObject> entityData = new LinkedHashMap<HasUuid, JavaScriptObject>();

        extractData( data, entityData, context );

        for ( Map.Entry<HasUuid, JavaScriptObject> entry : entityData.entrySet() )
        {
            EntityCodex<HasUuid> codex = typeContext.getCodex( entry.getKey().getClass() );
            codex.readProperties( entry.getKey(), entry.getValue(), context );
        }

        for ( HasUuid entity : entityData.keySet() )
        {
            System.out.println( entity );
        }

        return (T) null;
    }

    private final native void extractData( JavaScriptObject data, Map<HasUuid, JavaScriptObject> entityData,
                                           DeserializationContext context )
    /*-{
		for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				var array = data[key];
				this.@com.getperka.flatpack.gwt.Unpacker::extractData(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;Ljava/util/Map;Lcom/getperka/flatpack/gwt/ext/DeserializationContext;)(data, key, array, entityData, context);
			}
		}
    }-*/;

    private void extractData( JavaScriptObject data, String entityType, JsArray<JavaScriptObject> entityArray,
                              Map<HasUuid, JavaScriptObject> entityData, DeserializationContext context )
    {
        for ( int i = 0; i < entityArray.length(); i++ )
        {
            JavaScriptObject entityAsJso = entityArray.get( i );
            EntityCodex<?> codex = typeContext.getCodex( typeContext.getClass( entityType ) );

            HasUuid entity = codex.allocate( entityAsJso, context );
            entityData.put( entity, entityAsJso );
        }
    }
}
