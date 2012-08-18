package com.getperka.flatpack.gwt.codexes;

public class IntegerCodex
    extends NumberCodex<Integer>
{

    @Override
    protected boolean isZeroValue( Integer value )
    {
        return value == 0;
    }

    @Override
    protected Integer readDoubleNotNull( Double value )
    {
        return value.intValue();
    }
}
