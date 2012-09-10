package com.getperka.flatpack.gwt.client.impl;

import com.getperka.flatpack.gwt.Packer;
import com.getperka.flatpack.gwt.Unpacker;
import com.getperka.flatpack.gwt.client.Api;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.google.gwt.http.client.RequestBuilder;

public class ApiBase
    implements Api
{
    private final Packer packer;

    private final Unpacker unpacker;

    public ApiBase( TypeContext typeContext )
    {
        super();
        this.packer = new Packer( typeContext );
        this.unpacker = new Unpacker( typeContext );
    }

    /**
     * Hook point for altering a {@link RequestBuilder} before it is sent.
     */
    protected RequestBuilder filter( RequestBuilder requestBuilder )
    {
        return requestBuilder;
    }

    @Override
    public String getServerBase()
    {
        return "/resources";
    }

    @Override
    public void setServerBase( String serverBase )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVerbose( boolean verbose )
    {
        // TODO Auto-generated method stub

    }

    public Packer getPacker()
    {
        return packer;
    }

    public Unpacker getUnpacker()
    {
        return unpacker;
    }

}
