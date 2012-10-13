package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.stub.ChildBean;
import com.getperka.flatpack.gwt.stub.MultiplePropertiesBean;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class EntityCodexTestGwt
    extends FlatPackTestCase
{
    public void testReadAndAllocate()
    {
        EntityCodex<MultiplePropertiesBean> codex = TestCodexFactory.get().multiplePropertiesBeanCodex();

        DeserializationContext deserialization = new DeserializationContext();

        MultiplePropertiesBean readValue = codex.read( "15a1bf22-f764-4e4e-abc2-81146df9f54f", deserialization );
        assertNotNull( readValue );
        assertEquals( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ), readValue.getUuid() );
        assertSame( deserialization.getEntity( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ) ), readValue );

        MultiplePropertiesBean reReadValue = codex.read( "15a1bf22-f764-4e4e-abc2-81146df9f54f", deserialization );
        assertSame( reReadValue, readValue );
    }

    public void testWrite()
    {
        EntityCodex<MultiplePropertiesBean> codex = TestCodexFactory.get().multiplePropertiesBeanCodex();

        MultiplePropertiesBean value = new MultiplePropertiesBean();
        value.setUuid( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ) );
        value.setString( "toto" );
        value.setBytePrimitive( new Integer( 34 ).byteValue() );
        value.setIntBoxed( 456 );
        value.setBigDecimal( new BigDecimal( "12345678987654.456789" ) );
        value.setBooleanPrimitive( true );
        value.setDate( new Date() );

        StringBuilder out = new StringBuilder();
        SerializationContext serialization = serializationContext( out );

        codex.write( value, serialization );

        assertEquals( "\"15a1bf22-f764-4e4e-abc2-81146df9f54f\"", out.toString() );
    }

    public void testReadWritePropertiesEntity()
    {
        EntityCodex<MultiplePropertiesBean> codex = TestCodexFactory.get().multiplePropertiesBeanCodex();

        MultiplePropertiesBean value = new MultiplePropertiesBean();
        value.setUuid( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ) );
        value.setString( "toto" );
        value.setBigDecimal( new BigDecimal( "12345678987654.456789" ) );
        value.setBooleanPrimitive( true );
        value.setDate( new Date() );

        StringBuilder out = new StringBuilder();
        SerializationContext serialization = serializationContext( out );

        codex.writeProperties( value, serialization );

        JSONObject obj = JSONParser.parseStrict( out.toString() ).isObject();
        // true because the property doesn't specify to suppress default value
        assertTrue( obj.containsKey( "intPrimitive" ) );
        assertFalse( obj.containsKey( "sqlDate" ) );
        assertTrue( obj.containsKey( "date" ) );

        DeserializationContext deserialization = new DeserializationContext();
        MultiplePropertiesBean deserializedValue = codex.allocate( obj.getJavaScriptObject(), deserialization );
        codex.readProperties( deserializedValue, obj.getJavaScriptObject(), deserialization );

        assertEquals( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ), deserializedValue.getUuid() );
        assertEquals( value.getString(), deserializedValue.getString() );
        assertEquals( value.getBigDecimal(), deserializedValue.getBigDecimal() );
        assertEquals( value.isBooleanPrimitive(), deserializedValue.isBooleanPrimitive() );
        assertEquals( value.getDate(), deserializedValue.getDate() );

        testCodex( codex, null );
    }

    public void testIncorrectType()
        throws Exception
    {
        Codex<ChildBean> codex = TestCodexFactory.get().childBeanCodex();

        try
        {
            codex.readNotNull( Collections.EMPTY_LIST, new DeserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}