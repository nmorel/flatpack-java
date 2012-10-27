package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class VoidCodexTestGwt
    extends FlatPackTestCase
{

    public void testVoidCodex()
    {
        Codex<Void> codex = TestCodexFactory.get().voidCodex();

        testCodex( codex, null );
    }

    public void testVoidIncorrectType()
        throws Exception
    {
        Codex<Void> codex = TestCodexFactory.get().voidCodex();

        try
        {
            codex.readNotNull( Collections.EMPTY_LIST, deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}
