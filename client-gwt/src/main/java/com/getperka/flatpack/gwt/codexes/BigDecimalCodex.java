package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;

import com.getperka.flatpack.gwt.ext.DeserializationContext;

public class BigDecimalCodex
    extends NumberCodex<BigDecimal>
{

    @Override
    protected boolean isZeroValue( BigDecimal value )
    {
        return BigDecimal.ZERO.compareTo( (BigDecimal) value ) == 0;
    }

    @Override
    public BigDecimal readNotNull( Object element, DeserializationContext context )
        throws Exception
    {
        if ( element instanceof Double )
        {
            return readDoubleNotNull( (Double) element );
        }
        else if ( element instanceof String )
        {
            return readStringNotNull( (String) element );
        }
        else
        {
            throw new IllegalArgumentException( "element is not a Double or String : " + element.getClass().getName() );
        }
    }

    @Override
    protected BigDecimal readDoubleNotNull( Double value )
    {
        return new BigDecimal( value );
    }

    protected BigDecimal readStringNotNull( String value )
    {
        return new BigDecimal( value );
    }
}
