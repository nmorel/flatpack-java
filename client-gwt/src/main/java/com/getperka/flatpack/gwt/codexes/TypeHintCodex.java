package com.getperka.flatpack.gwt.codexes;

import com.getperka.flatpack.ext.TypeHint;

public class TypeHintCodex
    extends ToStringCodex<TypeHint>
{

    @Override
    protected TypeHint createNewInstance( String value )
    {
        return new TypeHint( value );
    }

}
