package com.getperka.flatpack.gwt.codexes;

import java.math.BigInteger;

import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;

public class BigIntegerCodex
    extends NumberCodex<BigInteger>
{

    @Override
    protected boolean isZeroValue( BigInteger value )
    {
        return BigInteger.ZERO.compareTo( (BigInteger) value ) == 0;
    }

    @Override
    public BigInteger readNotNull( Object element, DeserializationContext context )
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
    protected BigInteger readDoubleNotNull( Double value )
    {
        return new BigInteger( Integer.toString( value.intValue() ) );
    }

    protected BigInteger readStringNotNull( String value )
    {
        return new BigInteger( value );
    }

    @Override
    public void writeNotNull( BigInteger object, SerializationContext context )
    {
        context.getWriter().value( object.toString() );
    }

}
