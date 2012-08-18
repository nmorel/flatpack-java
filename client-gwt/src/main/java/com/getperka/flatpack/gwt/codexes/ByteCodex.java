package com.getperka.flatpack.gwt.codexes;

public class ByteCodex
    extends NumberCodex<Byte>
{

    @Override
    protected boolean isZeroValue( Byte value )
    {
        return value.intValue() == 0;
    }

    @Override
    protected Byte readDoubleNotNull( Double value )
    {
        return value.byteValue();
    }

}
