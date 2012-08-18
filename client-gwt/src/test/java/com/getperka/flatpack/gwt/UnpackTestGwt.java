package com.getperka.flatpack.gwt;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.getperka.flatpack.gwt.codexes.VoidCodex;
import com.getperka.flatpack.gwt.ext.TypeContext;

public class UnpackTestGwt
    extends FlatPackTestCase
{
    private static final String singleMultiplePropertiesBeanResponse =
        "{\"value\":\"028be872-ae61-4435-8155-bcd0cce22293\",\"data\":{\"multiplePropertiesBean\":[{\"uuid\":\"028be872-ae61-4435-8155-bcd0cce22293\",\"bytePrimitive\":34,\"byteBoxed\":87,\"shortPrimitive\":12,\"shortBoxed\":15,\"intPrimitive\":234,\"intBoxed\":456,\"longPrimitive\":1234,\"longBoxed\":34567,\"doublePrimitive\":126.23,\"doubleBoxed\":1256.98,\"floatPrimitive\":12.89,\"floatBoxed\":67.3,\"booleanPrimitive\":true,\"booleanBoxed\":false,\"string\":\"toto\",\"bigDecimal\":\"12345678987654.456789\",\"bigInteger\":\"123456789098765432345678987654\"}]}}";

    private Unpacker unpacker;

    private TypeContext typeContextMock;

    @Override
    protected void gwtSetUp()
        throws Exception
    {
        typeContextMock = new MockTypeContext();
        unpacker = new Unpacker( typeContextMock );
    }

    public void testUnpackVoid()
    {
        FlatPackEntity<Void> entity = unpacker.unpack( "", new VoidCodex() );
        assertNull( "value should be null", entity.getValue() );

        entity = unpacker.unpack( null, new VoidCodex() );
        assertNull( "value should be null", entity.getValue() );
    }

    public void testUnpackSingleMultiplePropertiesBean()
    {
        FlatPackEntity<MultiplePropertiesBean> entity =
            unpacker.unpack( singleMultiplePropertiesBeanResponse, new MultiplePropertiesBeanEntityCodex() );
        MultiplePropertiesBean bean = entity.getValue();
        assertNotNull( "bean is null", entity.getValue() );
        assertEquals( "toto", bean.getString() );
        assertEquals( 34, bean.getBytePrimitive() );
        assertEquals( (Byte) new Integer( 87 ).byteValue(), bean.getByteBoxed() );
        assertEquals( 12, bean.getShortPrimitive() );
        assertEquals( (Short) new Integer( 15 ).shortValue(), bean.getShortBoxed() );
        assertEquals( 234, bean.getIntPrimitive() );
        assertEquals( (Integer) 456, bean.getIntBoxed() );
        assertEquals( 1234l, bean.getLongPrimitive() );
        assertEquals( (Long) 34567l, bean.getLongBoxed() );
        assertEquals( 126.23, bean.getDoublePrimitive() );
        assertEquals( 1256.98, bean.getDoubleBoxed() );
        assertEquals( 12.89f, bean.getFloatPrimitive() );
        assertEquals( 67.3f, bean.getFloatBoxed() );
        assertTrue( bean.isBooleanPrimitive() );
        assertFalse( bean.getBooleanBoxed() );
        // assertEquals( '\u00e7', bean.getCharPrimitive() );
        // assertEquals( '\u00e8', bean.getCharBoxed() );
        assertEquals( '\u0000', bean.getCharPrimitive() );
        assertNull( bean.getCharBoxed() );
        assertEquals( new BigInteger( "123456789098765432345678987654" ), bean.getBigInteger() );
        assertEquals( new BigDecimal( "12345678987654.456789" ), bean.getBigDecimal() );
    }
}
