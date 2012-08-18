package com.getperka.flatpack.demo.gwt;

import java.util.Map;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.BaseTypeContext;

public class DemoTypeContext
    extends BaseTypeContext
{

    @Override
    protected void init( Map<String, Class<? extends HasUuid>> classes,
                         Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> codexes )
    {
        classes.put( "product", Product.class );
        codexes.put( Product.class, new ProductEntityCodex() );
    }

}