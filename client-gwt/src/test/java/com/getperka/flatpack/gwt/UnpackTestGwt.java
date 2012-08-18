package com.getperka.flatpack.gwt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map.Entry;
import java.util.UUID;

import com.getperka.flatpack.gwt.codexes.VoidCodex;
import com.getperka.flatpack.gwt.ext.TypeContext;

public class UnpackTestGwt
    extends FlatPackTestCase
{
    private static final String singleMultiplePropertiesBeanResponse =
        "{\"value\":\"15a1bf22-f764-4e4e-abc2-81146df9f54f\",\"data\":{\"multiplePropertiesBean\":[{\"uuid\":\"15a1bf22-f764-4e4e-abc2-81146df9f54f\",\"bytePrimitive\":34,\"byteBoxed\":87,\"shortPrimitive\":12,\"shortBoxed\":15,\"intPrimitive\":234,\"intBoxed\":456,\"longPrimitive\":1234,\"longBoxed\":34567,\"doublePrimitive\":126.23,\"doubleBoxed\":1256.98,\"floatPrimitive\":12.89,\"floatBoxed\":67.3,\"booleanPrimitive\":true,\"booleanBoxed\":false,\"enumProperty\":\"FOUR\",\"uuidProperty\":\"99999999-9999-9999-9999-999999999999\",\"singleEntityUuid\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"setEntityUuid\":[\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\"],\"mapStringToEntityUuid\":{\"child1\":\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"child2\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\"},\"mapEntityToString\":{\"4de76f27-2bed-49d3-b624-4b0697cfc53f\":\"child1\",\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\":\"child2\"},\"arrayEntityUuid\":[\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\"],\"dateJdk\":\"Sat Aug 18 15:45:56 CEST 2012\",\"dateJoda\":\"2011-03-14T21:56:23.996\",\"string\":\"toto\",\"bigDecimal\":\"12345678987654.456789\",\"bigInteger\":\"123456789098765432345678987654\",\"listEntityUuid\":[\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"4de76f27-2bed-49d3-b624-4b0697cfc53f\"]}],\"childBean\":[{\"uuid\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"child\":\"child2\"},{\"uuid\":\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"child\":\"child1\"}]}}";

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

    @SuppressWarnings( "unchecked" )
    public void testUnpackSingleMultiplePropertiesBean()
    {
        FlatPackEntity<MultiplePropertiesBean> entity =
            unpacker.unpack( singleMultiplePropertiesBeanResponse, new MultiplePropertiesBeanEntityCodex() );

        assertEquals( 3, entity.getExtraEntities().size() );

        MultiplePropertiesBean bean = entity.getValue();
        assertNotNull( "bean is null", entity.getValue() );
        assertEquals( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ), bean.getUuid() );
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
        assertEquals( new Double( 12.89 ).floatValue(), bean.getFloatPrimitive() );
        assertEquals( new Double( 67.3 ).floatValue(), bean.getFloatBoxed() );
        assertTrue( bean.isBooleanPrimitive() );
        assertFalse( bean.getBooleanBoxed() );
        // assertEquals( '\u00e7', bean.getCharPrimitive() );
        // assertEquals( '\u00e8', bean.getCharBoxed() );
        assertEquals( '\u0000', bean.getCharPrimitive() );
        assertNull( bean.getCharBoxed() );
        assertEquals( new BigInteger( "123456789098765432345678987654" ), bean.getBigInteger() );
        assertEquals( new BigDecimal( "12345678987654.456789" ), bean.getBigDecimal() );
        assertEquals( TestEnum.FOUR, bean.getEnumProperty() );
        assertEquals( UUID.fromString( "99999999-9999-9999-9999-999999999999" ), bean.getUuidProperty() );

        assertEquals( 2, bean.getListEntity().size() );
        assertEquals( UUID.fromString( "4de76f27-2bed-49d3-b624-4b0697cfc53f" ), bean.getListEntity().get( 1 )
            .getUuid() );
        assertEquals( "child1", bean.getListEntity().get( 1 ).getChild() );
        assertEquals( UUID.fromString( "e3aa7750-21f0-410c-a228-e48bd1ee6ff9" ), bean.getListEntity().get( 0 )
            .getUuid() );
        assertEquals( "child2", bean.getListEntity().get( 0 ).getChild() );

        ChildBean child1Expected = bean.getListEntity().get( 1 );
        ChildBean child2Expected = bean.getListEntity().get( 0 );

        assertEquals( 2, bean.getSetEntity().size() );
        assertTrue( child1Expected == bean.getSetEntity().toArray()[0] );
        assertTrue( child2Expected == bean.getSetEntity().toArray()[1] );

        assertEquals( 2, bean.getMapEntityToString().size() );
        assertEquals( child1Expected,
            ( (Entry<ChildBean, String>) bean.getMapEntityToString().entrySet().toArray()[0] ).getKey() );
        assertEquals( "child1",
            ( (Entry<ChildBean, String>) bean.getMapEntityToString().entrySet().toArray()[0] ).getValue() );
        assertEquals( child2Expected,
            ( (Entry<ChildBean, String>) bean.getMapEntityToString().entrySet().toArray()[1] ).getKey() );
        assertEquals( "child2",
            ( (Entry<ChildBean, String>) bean.getMapEntityToString().entrySet().toArray()[1] ).getValue() );

        assertEquals( 2, bean.getMapStringToEntity().size() );
        assertEquals( child1Expected,
            ( (Entry<String, ChildBean>) bean.getMapStringToEntity().entrySet().toArray()[0] ).getValue() );
        assertEquals( "child1",
            ( (Entry<String, ChildBean>) bean.getMapStringToEntity().entrySet().toArray()[0] ).getKey() );
        assertEquals( child2Expected,
            ( (Entry<String, ChildBean>) bean.getMapStringToEntity().entrySet().toArray()[1] ).getValue() );
        assertEquals( "child2",
            ( (Entry<String, ChildBean>) bean.getMapStringToEntity().entrySet().toArray()[1] ).getKey() );

        assertEquals( child2Expected, bean.getSingleEntity() );

        // TODO once the date codex are ready
        assertNull( bean.getDateJdk() );
        assertNull( bean.getDateJoda() );
    }
}
