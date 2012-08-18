package com.getperka.flatpack.gwt.codexes;

import java.math.BigInteger;

public class BigIntegerCodex
    extends ToStringCodex<BigInteger>
{

    public BigIntegerCodex()
    {
    }

    @Override
    public boolean isDefaultValue( BigInteger value )
    {
        if ( value == null )
        {
            return true;
        }
        return BigInteger.ZERO.equals( value );
    }

    @Override
    protected BigInteger createNewInstance( String value )
    {
        return new BigInteger( value );
    }

}
