package com.getperka.flatpack.gwt.codexes;

public class LongCodex
    extends NumberCodex<Long>
{

    @Override
    protected boolean isZeroValue( Long value )
    {
        return value.intValue() == 0;
    }

    @Override
    protected Long readDoubleNotNull( Double value )
    {
        return value.longValue();
    }
}
