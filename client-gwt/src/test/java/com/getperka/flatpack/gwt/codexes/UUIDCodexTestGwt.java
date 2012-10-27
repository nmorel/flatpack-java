package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;
import java.util.UUID;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class UUIDCodexTestGwt
    extends FlatPackTestCase
{

    public void testUUIDCodex()
    {
        Codex<UUID> codex = TestCodexFactory.get().uuidCodex();

        testCodex( codex, UUID.fromString( "4de76f27-2bed-49d3-b624-4b0697cfc53f" ) );
        testCodex( codex, null );
    }

    public void testUUIDIncorrectType()
        throws Exception
    {
        Codex<UUID> codex = TestCodexFactory.get().uuidCodex();

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
