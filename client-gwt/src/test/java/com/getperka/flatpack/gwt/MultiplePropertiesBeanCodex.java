package com.getperka.flatpack.gwt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.getperka.flatpack.gwt.codexes.BigDecimalCodex;
import com.getperka.flatpack.gwt.codexes.BigIntegerCodex;
import com.getperka.flatpack.gwt.codexes.BooleanCodex;
import com.getperka.flatpack.gwt.codexes.ByteCodex;
import com.getperka.flatpack.gwt.codexes.DoubleCodex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.codexes.EntityMapCodex;
import com.getperka.flatpack.gwt.codexes.EnumCodex;
import com.getperka.flatpack.gwt.codexes.FloatCodex;
import com.getperka.flatpack.gwt.codexes.IntegerCodex;
import com.getperka.flatpack.gwt.codexes.ListCodex;
import com.getperka.flatpack.gwt.codexes.LongCodex;
import com.getperka.flatpack.gwt.codexes.SetCodex;
import com.getperka.flatpack.gwt.codexes.ShortCodex;
import com.getperka.flatpack.gwt.codexes.StringCodex;
import com.getperka.flatpack.gwt.codexes.StringMapCodex;
import com.getperka.flatpack.gwt.codexes.UUIDCodex;
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
        properties.add( new Property<MultiplePropertiesBean, String>( "string", new StringCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Byte>( "bytePrimitive", new ByteCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Byte>( "byteBoxed", new ByteCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Short>( "shortPrimitive", new ShortCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Short>( "shortBoxed", new ShortCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Integer>( "intPrimitive", new IntegerCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Integer>( "intBoxed", new IntegerCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Long>( "longPrimitive", new LongCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Long>( "longBoxed", new LongCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Double>( "doublePrimitive", new DoubleCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Double>( "doubleBoxed", new DoubleCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Float>( "floatPrimitive", new FloatCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Float>( "floatBoxed", new FloatCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, Boolean>( "booleanPrimitive", new BooleanCodex(), true ) {

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
        properties.add( new Property<MultiplePropertiesBean, Boolean>( "booleanBoxed", new BooleanCodex() ) {

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
        // properties.add( new Property<MultiplePropertiesBean, Character>( "charPrimitive", new CharacterCodex(), true
        // ) {
        //
        // @Override
        // public Character getValue( MultiplePropertiesBean object )
        // {
        // return object.getCharPrimitive();
        // }
        //
        // @Override
        // public void setValue( MultiplePropertiesBean object, Character value )
        // {
        // object.setCharPrimitive( value );
        // }
        // } );
        // properties.add( new Property<MultiplePropertiesBean, Character>( "charBoxed", new CharacterCodex() ) {
        //
        // @Override
        // public Character getValue( MultiplePropertiesBean object )
        // {
        // return object.getCharBoxed();
        // }
        //
        // @Override
        // public void setValue( MultiplePropertiesBean object, Character value )
        // {
        // object.setCharBoxed( value );
        // }
        // } );
        properties.add( new Property<MultiplePropertiesBean, BigInteger>( "bigInteger", new BigIntegerCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, BigDecimal>( "bigDecimal", new BigDecimalCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, TestEnum>( "enumProperty", new EnumCodex<TestEnum>(
            TestEnum.class ) )
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
        properties.add( new Property<MultiplePropertiesBean, UUID>( "uuidProperty", new UUIDCodex() ) {

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
        properties.add( new Property<MultiplePropertiesBean, ChildBean>( "singleEntity", EntityCodexFactory.get()
            .getChildBeanCodex() )
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
        properties.add( new Property<MultiplePropertiesBean, List<ChildBean>>( "listEntity", new ListCodex<ChildBean>(
            EntityCodexFactory.get().getChildBeanCodex() ) )
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
        properties.add( new Property<MultiplePropertiesBean, Set<ChildBean>>( "setEntity", new SetCodex<ChildBean>(
            EntityCodexFactory.get().getChildBeanCodex() ) )
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
            new StringMapCodex<ChildBean>( EntityCodexFactory.get().getChildBeanCodex() ) )
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
            new EntityMapCodex<ChildBean, String>( EntityCodexFactory.get().getChildBeanCodex(), new StringCodex() ) )
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
        // properties.add( new Property<MultiplePropertiesBean, Date>( "dateJdk", new ChildBeanEntityCodex() ) {});
        // properties.add( new Property<MultiplePropertiesBean, Date>( "dateJoda", new ChildBeanEntityCodex() ) {});
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

}
