package com.getperka.flatpack.gwt;

import com.getperka.flatpack.gwt.codexes.VoidCodex;

public class UnpackTestGwt
    extends FlatPackTestCase
{
    private Unpacker unpacker;

    private TypeContext typeContextMock;

    @Override
    protected void gwtSetUp()
        throws Exception
    {
        typeContextMock = new MockTypeContext();
        unpacker = new Unpacker( typeContextMock );
    }

    public void testUnpackVoid()
    {
        FlatPackEntity<Void> entity = unpacker.unpack( "", new VoidCodex() );
        assertNull( "value should be null", entity.getValue() );

        entity = unpacker.unpack( null, new VoidCodex() );
        assertNull( "value should be null", entity.getValue() );
    }
}
