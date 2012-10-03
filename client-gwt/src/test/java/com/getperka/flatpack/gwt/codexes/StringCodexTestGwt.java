package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class StringCodexTestGwt
    extends FlatPackTestCase
{

    public void testString()
    {
        Codex<String> codex = TestCodexFactory.get().stringCodex();

        testCodex( codex, "toto" );
        testCodex( codex, "Azé" );
        testCodex( codex, null );
    }

    public void testStringIncorrectType()
        throws Exception
    {
        Codex<String> codex = TestCodexFactory.get().stringCodex();

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
