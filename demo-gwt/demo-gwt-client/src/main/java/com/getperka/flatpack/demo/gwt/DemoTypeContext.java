package com.getperka.flatpack.demo.gwt;

import com.getperka.flatpack.gwt.ext.BaseTypeContext;

public class DemoTypeContext
    extends BaseTypeContext
{

    @Override
    protected void init()
    {
        add( "product", Product.class, new ProductEntityCodex() );
    }

}