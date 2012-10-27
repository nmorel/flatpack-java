package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;

public class JsonElementCodexTestGwt
    extends FlatPackTestCase
{

    public void testJsonElementCodex()
    {
        Codex<JavaScriptObject> codex = TestCodexFactory.get().jsonCodex();

        JSONArray array = new JSONArray();
        array.set( 0, new JSONNumber( 485.1557d ) );
        array.set( 1, new JSONNumber( 15d ) );
        array.set( 2, new JSONString( "zerzerté'-" ) );
        JavaScriptObject value = array.getJavaScriptObject();

        StringBuilder out = new StringBuilder();
        SerializationContext serialization = serializationContext( out );

        codex.scan( value, serialization );
        codex.write( value, serialization );

        DeserializationContext deserialization = deserializationContext();
        JavaScriptObject deserializedValue = codex.read( value, deserialization );

        assertEquals( array, new JSONArray( deserializedValue ) );

        testCodex( codex, null );
    }

    public void testIncorrectType()
        throws Exception
    {
        Codex<JavaScriptObject> codex = TestCodexFactory.get().jsonCodex();

        try
        {
            codex.readNotNull( Collections.EMPTY_LIST, deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}
