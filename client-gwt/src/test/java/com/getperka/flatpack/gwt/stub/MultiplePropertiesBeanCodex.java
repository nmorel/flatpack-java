package com.getperka.flatpack.gwt.stub;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.Property;

public class MultiplePropertiesBeanCodex
    extends EntityCodex<MultiplePropertiesBean>
{

    MultiplePropertiesBeanCodex()
    {
    }

    @Override
    protected void initProperties( List<Property<MultiplePropertiesBean, ?>> properties )
    {
        properties.add( new Property<MultiplePropertiesBean, String>( "string", TestCodexFactory.get().stringCodex() ) {

            @Override
            public String getValue( MultiplePropertiesBean object )
            {
                return object.getString();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, String value )
            {
                object.setString( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Byte>( "bytePrimitive",
            TestCodexFactory.get().byteCodex(), true )
        {

            @Override
            public Byte getValue( MultiplePropertiesBean object )
            {
                return object.getBytePrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Byte value )
            {
                object.setBytePrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Byte>( "byteBoxed", TestCodexFactory.get().byteCodex() ) {

            @Override
            public Byte getValue( MultiplePropertiesBean object )
            {
                return object.getByteBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Byte value )
            {
                object.setByteBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Short>( "shortPrimitive", TestCodexFactory.get()
            .shortCodex(), true )
        {

            @Override
            public Short getValue( MultiplePropertiesBean object )
            {
                return object.getShortPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Short value )
            {
                object.setShortPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Short>( "shortBoxed", TestCodexFactory.get().shortCodex() )
        {

            @Override
            public Short getValue( MultiplePropertiesBean object )
            {
                return object.getShortBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Short value )
            {
                object.setShortBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Integer>( "intPrimitive", TestCodexFactory.get()
            .integerCodex(), true )
        {

            @Override
            public Integer getValue( MultiplePropertiesBean object )
            {
                return object.getIntPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Integer value )
            {
                object.setIntPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Integer>( "intBoxed", TestCodexFactory.get()
            .integerCodex() )
        {

            @Override
            public Integer getValue( MultiplePropertiesBean object )
            {
                return object.getIntBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Integer value )
            {
                object.setIntBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Long>( "longPrimitive",
            TestCodexFactory.get().longCodex(), true )
        {

            @Override
            public Long getValue( MultiplePropertiesBean object )
            {
                return object.getLongPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Long value )
            {
                object.setLongPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Long>( "longBoxed", TestCodexFactory.get().longCodex() ) {

            @Override
            public Long getValue( MultiplePropertiesBean object )
            {
                return object.getLongBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Long value )
            {
                object.setLongBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Double>( "doublePrimitive", TestCodexFactory.get()
            .doubleCodex(), true )
        {

            @Override
            public Double getValue( MultiplePropertiesBean object )
            {
                return object.getDoublePrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Double value )
            {
                object.setDoublePrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Double>( "doubleBoxed", TestCodexFactory.get()
            .doubleCodex() )
        {

            @Override
            public Double getValue( MultiplePropertiesBean object )
            {
                return object.getDoubleBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Double value )
            {
                object.setDoubleBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Float>( "floatPrimitive", TestCodexFactory.get()
            .floatCodex(), true )
        {

            @Override
            public Float getValue( MultiplePropertiesBean object )
            {
                return object.getFloatPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Float value )
            {
                object.setFloatPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Float>( "floatBoxed", TestCodexFactory.get().floatCodex() )
        {

            @Override
            public Float getValue( MultiplePropertiesBean object )
            {
                return object.getFloatBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Float value )
            {
                object.setFloatBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Boolean>( "booleanPrimitive", TestCodexFactory.get()
            .booleanCodex(), true )
        {

            @Override
            public Boolean getValue( MultiplePropertiesBean object )
            {
                return object.isBooleanPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Boolean value )
            {
                object.setBooleanPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Boolean>( "booleanBoxed", TestCodexFactory.get()
            .booleanCodex() )
        {

            @Override
            public Boolean getValue( MultiplePropertiesBean object )
            {
                return object.getBooleanBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Boolean value )
            {
                object.setBooleanBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Character>( "charPrimitive", TestCodexFactory.get()
            .characterCodex(), true )
        {

            @Override
            public Character getValue( MultiplePropertiesBean object )
            {
                return object.getCharPrimitive();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Character value )
            {
                object.setCharPrimitive( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Character>( "charBoxed", TestCodexFactory.get()
            .characterCodex() )
        {

            @Override
            public Character getValue( MultiplePropertiesBean object )
            {
                return object.getCharBoxed();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Character value )
            {
                object.setCharBoxed( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, BigInteger>( "bigInteger", TestCodexFactory.get()
            .bigIntegerCodex() )
        {

            @Override
            public BigInteger getValue( MultiplePropertiesBean object )
            {
                return object.getBigInteger();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, BigInteger value )
            {
                object.setBigInteger( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, BigDecimal>( "bigDecimal", TestCodexFactory.get()
            .bigDecimalCodex() )
        {

            @Override
            public BigDecimal getValue( MultiplePropertiesBean object )
            {
                return object.getBigDecimal();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, BigDecimal value )
            {
                object.setBigDecimal( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, TestEnum>( "enumProperty", TestCodexFactory.get()
            .enumCodex( TestEnum.class ) )
        {
            @Override
            public TestEnum getValue( MultiplePropertiesBean object )
            {
                return object.getEnumProperty();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, TestEnum value )
            {
                object.setEnumProperty( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, UUID>( "uuidProperty", TestCodexFactory.get().uuidCodex() )
        {

            @Override
            public UUID getValue( MultiplePropertiesBean object )
            {
                return object.getUuidProperty();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, UUID value )
            {
                object.setUuidProperty( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, ChildBean>( "singleEntity", TestCodexFactory.get()
            .childBeanCodex() )
        {

            @Override
            public ChildBean getValue( MultiplePropertiesBean object )
            {
                return object.getSingleEntity();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, ChildBean value )
            {
                object.setSingleEntity( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, List<ChildBean>>( "listEntity", TestCodexFactory.get()
            .listCodex( TestCodexFactory.get().childBeanCodex() ) )
        {

            @Override
            public List<ChildBean> getValue( MultiplePropertiesBean object )
            {
                return object.getListEntity();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, List<ChildBean> value )
            {
                object.setListEntity( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Set<ChildBean>>( "setEntity", TestCodexFactory.get()
            .setCodex( TestCodexFactory.get().childBeanCodex() ) )
        {

            @Override
            public Set<ChildBean> getValue( MultiplePropertiesBean object )
            {
                return object.getSetEntity();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Set<ChildBean> value )
            {
                object.setSetEntity( value );
            }
        } );
        // properties.add( new Property<MultiplePropertiesBean, ChildBean[]>( "arrayEntity",new ArrayCodex<ChildBean>(
        // EntityCodexFactory.get().getChildBeanCodex() ) ) {} );
        properties.add( new Property<MultiplePropertiesBean, Map<String, ChildBean>>( "mapStringToEntity",
            TestCodexFactory.get().stringMapCodex( TestCodexFactory.get().childBeanCodex() ) )
        {
            @Override
            public Map<String, ChildBean> getValue( MultiplePropertiesBean object )
            {
                return object.getMapStringToEntity();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Map<String, ChildBean> value )
            {
                object.setMapStringToEntity( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Map<ChildBean, String>>( "mapEntityToString",
            TestCodexFactory.get().entityMapCodex( TestCodexFactory.get().childBeanCodex(),
                TestCodexFactory.get().stringCodex() ) )
        {
            @Override
            public Map<ChildBean, String> getValue( MultiplePropertiesBean object )
            {
                return object.getMapEntityToString();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Map<ChildBean, String> value )
            {
                object.setMapEntityToString( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Date>( "date", TestCodexFactory.get().dateCodex() ) {

            @Override
            public Date getValue( MultiplePropertiesBean object )
            {
                return object.getDate();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Date value )
            {
                object.setDate( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, java.sql.Date>( "sqlDate", TestCodexFactory.get()
            .sqlDateCodex() )
        {

            @Override
            public java.sql.Date getValue( MultiplePropertiesBean object )
            {
                return object.getSqlDate();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, java.sql.Date value )
            {
                object.setSqlDate( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Time>( "sqlTime", TestCodexFactory.get().sqlTimeCodex() ) {

            @Override
            public Time getValue( MultiplePropertiesBean object )
            {
                return object.getSqlTime();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Time value )
            {
                object.setSqlTime( value );
            }
        } );
        properties.add( new Property<MultiplePropertiesBean, Timestamp>( "sqlTimestamp", TestCodexFactory.get()
            .sqlTimestampCodex() )
        {

            @Override
            public Timestamp getValue( MultiplePropertiesBean object )
            {
                return object.getSqlTimestamp();
            }

            @Override
            public void setValue( MultiplePropertiesBean object, Timestamp value )
            {
                object.setSqlTimestamp( value );
            }
        } );
    }

    @Override
    public String getName()
    {
        return "multiplePropertiesBean";
    }

    @Override
    protected MultiplePropertiesBean createInstance()
    {
        return new MultiplePropertiesBean();
    }

    @Override
    protected Class<MultiplePropertiesBean> getEntityClass()
    {
        return MultiplePropertiesBean.class;
    }

}
