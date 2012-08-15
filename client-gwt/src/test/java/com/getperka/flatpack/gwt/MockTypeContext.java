package com.getperka.flatpack.gwt;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public class MockTypeContext
    implements TypeContext
{

    @Override
    public Class<? extends HasUuid> getClass( String simplePayloadName )
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
