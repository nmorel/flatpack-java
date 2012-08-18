package com.getperka.flatpack.gwt.codexes;

public class ShortCodex
    extends NumberCodex<Short>
{

    @Override
    protected boolean isZeroValue( Short value )
    {
        return value.intValue() == 0;
    }

    @Override
    protected Short readDoubleNotNull( Double value )
    {
        return value.shortValue();
    }

}
