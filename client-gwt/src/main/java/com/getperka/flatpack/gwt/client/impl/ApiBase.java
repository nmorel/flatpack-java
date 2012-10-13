package com.getperka.flatpack.gwt.client.impl;

import com.getperka.flatpack.gwt.Packer;
import com.getperka.flatpack.gwt.Unpacker;
import com.getperka.flatpack.gwt.client.FlatPackApi;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.google.gwt.http.client.RequestBuilder;

public class ApiBase
    implements FlatPackApi
{
    private final Packer packer;

    private final Unpacker unpacker;

    private String serverBase;

    public ApiBase( TypeContext typeContext )
    {
        super();
        this.packer = new Packer( typeContext );
        this.unpacker = new Unpacker( typeContext );
    }

    /**
     * Hook point for altering a {@link RequestBuilder} before it is sent.
     */
    @Override
    public RequestBuilder filter( RequestBuilder requestBuilder )
    {
        return requestBuilder;
    }

    @Override
    public String getServerBase()
    {
        return serverBase;
    }

    public void setServerBase( String serverBase )
    {
        this.serverBase = serverBase;
    }

    @Override
    public Packer getPacker()
    {
        return packer;
    }

    @Override
    public Unpacker getUnpacker()
    {
        return unpacker;
    }

}
