package com.getperka.flatpack.gwt.codexes;

public class FloatCodex
    extends NumberCodex<Float>
{

    @Override
    protected boolean isZeroValue( Float value )
    {
        return value.doubleValue() == 0.0;
    }

    @Override
    protected Float readDoubleNotNull( Double value )
    {
        return value.floatValue();
    }
}
