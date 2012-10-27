package com.getperka.flatpack.gwt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;

import com.getperka.flatpack.gwt.ext.TypeContext;
import com.getperka.flatpack.gwt.stub.ChildBean;
import com.getperka.flatpack.gwt.stub.MultiplePropertiesBean;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.getperka.flatpack.gwt.stub.TestEnum;
import com.getperka.flatpack.gwt.stub.TestTypeContext;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class PackerTestGwt
    extends FlatPackTestCase
{
    private static final String singleMultiplePropertiesBeanResponse =
        "{\"value\":\"15a1bf22-f764-4e4e-abc2-81146df9f54f\",\"data\":{\"multiplePropertiesBean\":[{\"uuid\":\"15a1bf22-f764-4e4e-abc2-81146df9f54f\",\"string\":\"toto\",\"bytePrimitive\":34,\"byteBoxed\":87,\"shortPrimitive\":12,\"shortBoxed\":15,\"intPrimitive\":234,\"intBoxed\":456,\"longPrimitive\":1234,\"longBoxed\":34567,\"doublePrimitive\":126.23,\"doubleBoxed\":1256.98,\"floatPrimitive\":12.89,\"floatBoxed\":67.3,\"booleanPrimitive\":true,\"booleanBoxed\":false,\"charPrimitive\":\"ç\",\"charBoxed\":\"è\",\"bigInteger\":\"123456789098765432345678987654\",\"bigDecimal\":\"12345678987654.456789\",\"enumProperty\":\"FOUR\",\"uuidProperty\":\"99999999-9999-9999-9999-999999999999\",\"singleEntityUuid\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"listEntityUuid\":[\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"4de76f27-2bed-49d3-b624-4b0697cfc53f\"],\"setEntityUuid\":[\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\"],\"mapStringToEntityUuid\":{\"child1\":\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"child2\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\"},\"mapEntityToString\":{\"4de76f27-2bed-49d3-b624-4b0697cfc53f\":\"child1\",\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\":\"child2\"},\"date\":\"dateReplace\",\"sqlDate\":\"sqlDateReplace\",\"sqlTime\":\"sqlTimeReplace\",\"sqlTimestamp\":\"sqlTimestampReplace\"}],\"childBean\":[{\"uuid\":\"e3aa7750-21f0-410c-a228-e48bd1ee6ff9\",\"child\":\"child2\"},{\"uuid\":\"4de76f27-2bed-49d3-b624-4b0697cfc53f\",\"child\":\"child1\"}]}}";

    private Packer packer;

    private TypeContext typeContextMock;

    @Override
    protected void gwtSetUp()
        throws Exception
    {
        typeContextMock = new TestTypeContext();
        packer = FlatPack.builder( typeContextMock ).create().getPacker();
    }

    public void testPackSingleMultiplePropertiesBean()
    {
        MultiplePropertiesBean bean = new MultiplePropertiesBean();
        bean.setUuid( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ) );
        bean.setString( "toto" );
        bean.setBytePrimitive( new Integer( 34 ).byteValue() );
        bean.setByteBoxed( new Integer( 87 ).byteValue() );
        bean.setShortPrimitive( new Integer( 12 ).shortValue() );
        bean.setShortBoxed( new Integer( 15 ).shortValue() );
        bean.setIntPrimitive( 234 );
        bean.setIntBoxed( 456 );
        bean.setLongPrimitive( 1234l );
        bean.setLongBoxed( 34567l );
        bean.setDoublePrimitive( 126.23 );
        bean.setDoubleBoxed( 1256.98 );
        bean.setFloatPrimitive( new Double( 12.89 ).floatValue() );
        bean.setFloatBoxed( new Double( 67.3 ).floatValue() );
        bean.setBigDecimal( new BigDecimal( "12345678987654.456789" ) );
        bean.setBigInteger( new BigInteger( "123456789098765432345678987654" ) );
        bean.setBooleanPrimitive( true );
        bean.setBooleanBoxed( false );
        bean.setEnumProperty( TestEnum.FOUR );
        bean.setUuidProperty( UUID.fromString( "99999999-9999-9999-9999-999999999999" ) );
        bean.setCharPrimitive( '\u00e7' );
        bean.setCharBoxed( '\u00e8' );

        ChildBean child1 = new ChildBean();
        child1.setUuid( UUID.fromString( "4de76f27-2bed-49d3-b624-4b0697cfc53f" ) );
        child1.setChild( "child1" );

        ChildBean child2 = new ChildBean();
        child2.setUuid( UUID.fromString( "e3aa7750-21f0-410c-a228-e48bd1ee6ff9" ) );
        child2.setChild( "child2" );

        bean.setSingleEntity( child2 );
        bean.setListEntity( Arrays.asList( child2, child1 ) );
        bean.setSetEntity( new LinkedHashSet<ChildBean>( Arrays.asList( child1, child2 ) ) );
        // bean.setArrayEntity( new ChildBean[] { child1, child2 } );

        Map<String, ChildBean> mapStringToEntity = new LinkedHashMap<String, ChildBean>();
        mapStringToEntity.put( child1.getChild(), child1 );
        mapStringToEntity.put( child2.getChild(), child2 );
        bean.setMapStringToEntity( mapStringToEntity );

        Map<ChildBean, String> mapEntityToString = new LinkedHashMap<ChildBean, String>();
        mapEntityToString.put( child1, child1.getChild() );
        mapEntityToString.put( child2, child2.getChild() );
        bean.setMapEntityToString( mapEntityToString );

        Date expectedDate = new Date( Date.UTC( 112, 7, 18, 15, 45, 56 ) + 543 );
        java.sql.Date expectedSqlDate = new java.sql.Date( Date.UTC( 112, 7, 18, 15, 45, 56 ) + 544 );
        Time expectedSqlTime = new Time( Date.UTC( 112, 7, 18, 15, 45, 56 ) + 545 );
        Timestamp expectedSqlTimestamp = new Timestamp( Date.UTC( 112, 7, 18, 15, 45, 56 ) + 546 );

        bean.setDate( expectedDate );
        bean.setSqlDate( expectedSqlDate );
        bean.setSqlTime( expectedSqlTime );
        bean.setSqlTimestamp( expectedSqlTimestamp );

        FlatPackEntity<MultiplePropertiesBean> entity = new FlatPackEntity<MultiplePropertiesBean>();
        entity.withValue( bean );

        String out = packer.pack( entity, TestCodexFactory.get().multiplePropertiesBeanCodex() );

        // Have to replace the date in the expected result because it depends on the timezone which can change between
        // dev and jenkins box
        DateTimeFormat format = DateTimeFormat.getFormat( PredefinedFormat.ISO_8601 );
        String expectedResult = singleMultiplePropertiesBeanResponse;
        expectedResult = expectedResult.replace( "dateReplace", format.format( expectedDate ) );
        expectedResult = expectedResult.replace( "sqlDateReplace", format.format( expectedSqlDate ) );
        expectedResult = expectedResult.replace( "sqlTimeReplace", format.format( expectedSqlTime ) );
        expectedResult = expectedResult.replace( "sqlTimestampReplace", format.format( expectedSqlTimestamp ) );
        assertEquals( expectedResult, out );
    }
}
