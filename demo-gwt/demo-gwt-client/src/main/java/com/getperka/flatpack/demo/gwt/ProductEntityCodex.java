package com.getperka.flatpack.demo.gwt;

import java.math.BigDecimal;
import java.util.List;

import com.getperka.flatpack.gwt.codexes.BigDecimalCodex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.codexes.StringCodex;
import com.getperka.flatpack.gwt.ext.Property;

public class ProductEntityCodex
    extends EntityCodex<Product>
{

    ProductEntityCodex()
    {
    }

    @Override
    protected void initProperties( List<Property<Product, ?>> properties )
    {
        Property<Product, String> name = new Property<Product, String>( "name", new StringCodex() ) {

            @Override
            public String getValue( Product object )
            {
                return object.getName();
            }

            @Override
            public void setValue( Product object, String value )
            {
                object.setName( value );
            }
        };
        properties.add( name );

        Property<Product, String> notes = new Property<Product, String>( "notes", new StringCodex() ) {

            @Override
            public String getValue( Product object )
            {
                return object.getNotes();
            }

            @Override
            public void setValue( Product object, String value )
            {
                object.setNotes( value );
            }
        };
        properties.add( notes );

        Property<Product, BigDecimal> price = new Property<Product, BigDecimal>( "price", new BigDecimalCodex() ) {

            @Override
            public BigDecimal getValue( Product object )
            {
                return object.getPrice();
            }

            @Override
            public void setValue( Product object, BigDecimal value )
            {
                object.setPrice( value );
            }
        };
        properties.add( price );
    }

    @Override
    public String getName()
    {
        return "product";
    }

    @Override
    protected Product createInstance()
    {
        return new Product();
    }

}
