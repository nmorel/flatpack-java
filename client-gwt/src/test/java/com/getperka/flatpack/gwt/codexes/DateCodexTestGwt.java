package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;
import java.util.Date;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

@SuppressWarnings( "deprecation" )
public class DateCodexTestGwt
    extends FlatPackTestCase
{

    public void testDate()
        throws Exception
    {
        Codex<Date> codex = TestCodexFactory.get().dateCodex();

        Date now = new Date();

        testCodex( codex, new Date( 112, 8, 9, 4, 0, 1 ) );
        testCodex( codex, now );
        testCodex( codex, null );

        assertEquals( now, codex.readNotNull( Double.valueOf( now.getTime() ), deserializationContext() ) );
    }

    public void testSqlDate()
        throws Exception
    {
        Codex<java.sql.Date> codex = TestCodexFactory.get().sqlDateCodex();

        Date now = new Date();

        testCodex( codex, new java.sql.Date( 112, 8, 9 ) );
        testCodex( codex, new java.sql.Date( now.getTime() ) );
        testCodex( codex, null );

        assertEquals( new java.sql.Date( now.getTime() ),
            codex.readNotNull( Double.valueOf( now.getTime() ), deserializationContext() ) );
    }

    public void testSqlTime()
        throws Exception
    {
        Codex<java.sql.Time> codex = TestCodexFactory.get().sqlTimeCodex();

        Date now = new Date();

        testCodex( codex, new java.sql.Time( 22, 8, 9 ) );
        testCodex( codex, new java.sql.Time( now.getTime() ) );
        testCodex( codex, null );

        assertEquals( new java.sql.Time( now.getTime() ),
            codex.readNotNull( Double.valueOf( now.getTime() ), deserializationContext() ) );
    }

    public void testSqlTimestamp()
        throws Exception
    {
        Codex<java.sql.Timestamp> codex = TestCodexFactory.get().sqlTimestampCodex();

        Date now = new Date();

        testCodex( codex, new java.sql.Timestamp( 112, 8, 9, 4, 0, 14, 0 ) );
        testCodex( codex, new java.sql.Timestamp( now.getTime() ) );
        testCodex( codex, null );

        assertEquals( new java.sql.Timestamp( now.getTime() ),
            codex.readNotNull( Double.valueOf( now.getTime() ), deserializationContext() ) );
    }

    public void testIncorrectType()
        throws Exception
    {
        Codex<Date> codex = TestCodexFactory.get().dateCodex();

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
