package com.getperka.flatpack.gwt;

import java.util.Set;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.Codex;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.json.JsonWriter;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.junit.client.GWTTestCase;

public abstract class FlatPackTestCase
    extends GWTTestCase
{

    @Override
    public String getModuleName()
    {
        return "com.getperka.flatpack.gwt.FlatPackTest";
    }

    protected SerializationContext serializationContext(StringBuilder out) {
        JsonWriter json = new JsonWriter(out);
        json.setLenient(true);
        json.setIndent("  ");
        json.setSerializeNulls(false);
        return new SerializationContext( json );
      }

    protected <T> T testCodex( Codex<T> codex, T value )
    {
        return testCodex( codex, value, null );
    }

    protected <T> T testCodex( Codex<T> codex, T value, Set<HasUuid> scanned )
    {
        StringBuilder out = new StringBuilder();
        SerializationContext serialization = serializationContext( out );

        codex.scan( value, serialization );
        codex.write( value, serialization );
        if ( scanned != null )
        {
            scanned.addAll( serialization.getEntities() );
        }

        DeserializationContext deserialization = new DeserializationContext();
        T deserializedValue = codex.read( getObjectFromSerializedString( out.toString() ), deserialization );
        if (value == null) {
          assertNull(deserializedValue);
        } else {
          assertEquals(value, deserializedValue);
        }
        return deserializedValue;
    }

    private Object getObjectFromSerializedString( String serialized )
    {
        JSONValue json = JSONParser.parseStrict( serialized );
        if ( null != json.isArray() )
        {
            return json.isArray().getJavaScriptObject();
        }
        if ( null != json.isObject() )
        {
            return json.isObject().getJavaScriptObject();
        }
        if ( null != json.isBoolean() )
        {
            return Boolean.valueOf( json.isBoolean().booleanValue() );
        }
        if ( null != json.isNumber() )
        {
            return Double.valueOf( json.isNumber().doubleValue() );
        }
        if ( null != json.isString() )
        {
            return json.isString().stringValue();
        }
        return null;
    }

}
