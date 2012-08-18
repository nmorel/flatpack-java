package com.getperka.flatpack.gwt.codexes;

public class DoubleCodex
    extends NumberCodex<Double>
{

    @Override
    protected boolean isZeroValue( Double value )
    {
        return value == 0.0;
    }

    @Override
    protected Double readDoubleNotNull( Double value )
    {
        return value;
    }
}
