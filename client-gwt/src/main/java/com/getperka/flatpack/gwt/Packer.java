package com.getperka.flatpack.gwt;

import java.util.logging.Logger;

import com.getperka.flatpack.gwt.ext.TypeContext;

public class Packer
{
    private static Logger logger = Logger.getLogger( "Packer" );

    private TypeContext typeContext;

    public Packer( TypeContext typeContext )
    {
        this.typeContext = typeContext;
    }

}
