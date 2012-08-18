package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;

public class BigDecimalCodex
    extends ToStringCodex<BigDecimal>
{

    public BigDecimalCodex()
    {
    }

    @Override
    public boolean isDefaultValue( BigDecimal value )
    {
        if ( value == null )
        {
            return true;
        }
        return BigDecimal.ZERO.equals( value );
    }

    @Override
    protected BigDecimal createNewInstance( String value )
    {
        return new BigDecimal( value );
    }

}
