package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.getperka.flatpack.gwt.stub.TestEnum;

public class EnumCodexTestGwt
    extends FlatPackTestCase
{

    public void testEnum()
    {
        Codex<TestEnum> codex = TestCodexFactory.get().enumCodex( TestEnum.class );

        testCodex( codex, TestEnum.FOUR );
        testCodex( codex, TestEnum.ONE );
        testCodex( codex, null );
    }

    public void testEnumIncorrectType()
        throws Exception
    {
        Codex<TestEnum> codex = TestCodexFactory.get().enumCodex( TestEnum.class );

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
