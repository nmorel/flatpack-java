package com.getperka.flatpack.demo.gwt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.getperka.flatpack.gwt.codexes.BigDecimalCodex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.codexes.StringCodex;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.Property;
import com.google.gwt.core.client.JavaScriptObject;

public class ProductEntityCodex
    extends EntityCodex<Product>
{

    ProductEntityCodex()
    {
    }

    @Override
    protected List<Property<Product, ?>> getProperties()
    {
        List<Property<Product, ?>> properties = new ArrayList<Property<Product, ?>>();
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
        return properties;
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

    @Override
    public void readProperties( Product entity, JavaScriptObject entityAsJso, DeserializationContext context )
    {
        entity.setName( getString( entityAsJso, "name" ) );
        entity.setNotes( getString( entityAsJso, "notes" ) );
        entity.setPrice( new BigDecimal( getString( entityAsJso, "price" ) ) );
    }

}
