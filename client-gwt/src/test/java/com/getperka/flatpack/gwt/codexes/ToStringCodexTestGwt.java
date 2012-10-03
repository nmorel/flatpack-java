package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;
import java.util.Collections;

import com.getperka.flatpack.ext.TypeHint;
import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class ToStringCodexTestGwt
    extends FlatPackTestCase
{

    public void testTypeHintCodex()
    {
        Codex<TypeHint> codex = TestCodexFactory.get().typeHintCodex();

        testCodex( codex, new TypeHint( "toto" ) );
        testCodex( codex, new TypeHint( BigDecimal.class.getName() ) );
        testCodex( codex, null );
    }

    public void testToStringIncorrectType()
        throws Exception
    {
        Codex<TypeHint> codex = TestCodexFactory.get().typeHintCodex();

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
