package com.getperka.flatpack.gwt;

import com.google.gwt.junit.client.GWTTestCase;

public abstract class FlatPackTestCase
    extends GWTTestCase
{

    @Override
    public String getModuleName()
    {
        return "com.getperka.flatpack.gwt.FlatPackTest";
    }

}
