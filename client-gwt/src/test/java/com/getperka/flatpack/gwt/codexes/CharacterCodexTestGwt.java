package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class CharacterCodexTestGwt
    extends FlatPackTestCase
{

    public void testCharacter()
    {
        Codex<Character> codex = TestCodexFactory.get().characterCodex();

        testCodex( codex, 'e' );
        testCodex( codex, '\u00e9' );
        testCodex( codex, '#' );
        testCodex( codex, ' ' );
        testCodex( codex, null );
    }

    public void testCharacterNumberDeserialization()
        throws Exception
    {
        Codex<Character> codex = TestCodexFactory.get().characterCodex();
        assertEquals( 'e', codex.readNotNull( Double.valueOf( (int) 'e' ), deserializationContext() ).charValue() );
    }

    public void testCharacterDefaultValue()
        throws Exception
    {
        Codex<Character> codex = TestCodexFactory.get().characterCodex();

        assertTrue( codex.isDefaultValue( null ) );
        assertTrue( codex.isDefaultValue( '\u0000' ) );
        assertFalse( codex.isDefaultValue( 'e' ) );
    }

    public void testCharacterIncorrectType()
        throws Exception
    {
        Codex<Character> codex = TestCodexFactory.get().characterCodex();

        try
        {
            codex.readNotNull( "er", deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }

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
