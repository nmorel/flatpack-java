package com.getperka.flatpack.gwt.client.impl;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.FlatPack;
import com.getperka.flatpack.gwt.Packer;
import com.getperka.flatpack.gwt.Unpacker;
import com.getperka.flatpack.gwt.client.FlatPackApi;
import com.getperka.flatpack.gwt.codexes.DynamicEntityCodex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.google.gwt.http.client.RequestBuilder;

public class ApiBase
    implements FlatPackApi
{
    private final Packer packer;

    private final Unpacker unpacker;

    private final DynamicEntityCodex<HasUuid> dynamicEntityCodex;

    private String serverBase;

    public ApiBase( FlatPack flatPack )
    {
        super();
        this.packer = flatPack.getPacker();
        this.unpacker = flatPack.getUnpacker();
        this.dynamicEntityCodex = new DynamicEntityCodex<HasUuid>( flatPack.getTypeContext() );
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

    @SuppressWarnings( "unchecked" )
    public <T extends HasUuid> EntityCodex<T> getDynamicEntityCodex()
    {
        return (DynamicEntityCodex<T>) dynamicEntityCodex;
    }

}
