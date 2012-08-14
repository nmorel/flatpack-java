package com.getperka.flatpack.gwt;

import java.util.logging.Logger;

public class Packer
{
    private static Logger logger = Logger.getLogger( "Packer" );

    private TypeContext typeContext;

    public Packer( TypeContext typeContext )
    {
        this.typeContext = typeContext;
    }

}
