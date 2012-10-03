package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class BooleanCodexTestGwt
    extends FlatPackTestCase
{

    public void testBoolean()
    {
        Codex<Boolean> codex = TestCodexFactory.get().booleanCodex();

        testCodex( codex, true );
        testCodex( codex, false );
        testCodex( codex, Boolean.TRUE );
        testCodex( codex, Boolean.FALSE );
        testCodex( codex, null );
    }

    public void testBooleanNumberDeserialization()
        throws Exception
    {
        Codex<Boolean> codex = TestCodexFactory.get().booleanCodex();
        assertTrue( codex.readNotNull( Double.valueOf( 1 ), new DeserializationContext() ) );
        assertFalse( codex.readNotNull( Double.valueOf( 0 ), new DeserializationContext() ) );
    }

    public void testDefaultValue()
        throws Exception
    {
        Codex<Boolean> codex = TestCodexFactory.get().booleanCodex();

        assertTrue( codex.isDefaultValue( null ) );
        assertTrue( codex.isDefaultValue( false ) );
        assertTrue( codex.isDefaultValue( Boolean.FALSE ) );
        assertFalse( codex.isDefaultValue( Boolean.TRUE ) );
    }

    public void testIncorrectType() throws Exception
    {
        Codex<Boolean> codex = TestCodexFactory.get().booleanCodex();

        try
        {
            codex.readNotNull( Collections.EMPTY_LIST, new DeserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}
