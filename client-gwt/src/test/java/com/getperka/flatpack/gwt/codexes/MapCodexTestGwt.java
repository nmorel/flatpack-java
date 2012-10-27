package com.getperka.flatpack.gwt.codexes;

import java.util.Collections;
import java.util.Map;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.ChildBean;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.getperka.flatpack.util.FlatPackCollections;

public class MapCodexTestGwt
    extends FlatPackTestCase
{

    public void testStringIntegerMap()
    {
        Codex<Map<String, Integer>> codex =
            TestCodexFactory.get().stringMapCodex( TestCodexFactory.get().integerCodex() );

        Map<String, Integer> map = FlatPackCollections.mapForIteration();
        map.put( "toto", 1575 );
        map.put( "Tutu", 140 );
        map.put( "TITI", 98 );

        testCodex( codex, map );
        testCodex( codex, null );
    }

    public void testStringStringMap()
    {
        Codex<Map<String, String>> codex = TestCodexFactory.get().stringMapCodex( TestCodexFactory.get().stringCodex() );

        Map<String, String> map = FlatPackCollections.mapForIteration();
        map.put( "toto", "sdf&é" );
        map.put( "Tutu", "ghjui" );
        map.put( "TITI", "bgfjyiu" );

        testCodex( codex, map );
        testCodex( codex, null );
    }

    public void testEntityIntegerMap()
    {
        Codex<Map<ChildBean, Integer>> codex =
            TestCodexFactory.get().entityMapCodex( TestCodexFactory.get().childBeanCodex(),
                TestCodexFactory.get().integerCodex() );

        Map<ChildBean, Integer> map = FlatPackCollections.mapForIteration();
        map.put( new ChildBean( "toto" ), 1575 );
        map.put( new ChildBean( "Tutu" ), 140 );
        map.put( new ChildBean( "TITI" ), 98 );

        testCodex( codex, map );
        testCodex( codex, null );
    }

    public void testEntityStringMap()
    {
        Codex<Map<ChildBean, String>> codex =
            TestCodexFactory.get().entityMapCodex( TestCodexFactory.get().childBeanCodex(),
                TestCodexFactory.get().stringCodex() );

        Map<ChildBean, String> map = FlatPackCollections.mapForIteration();
        map.put( new ChildBean( "toto" ), "sdf&é" );
        map.put( new ChildBean( "Tutu" ), "ghjui" );
        map.put( new ChildBean( "TITI" ), "bgfjyiu" );

        testCodex( codex, map );
        testCodex( codex, null );
    }

    public void testIncorrectType()
        throws Exception
    {
        Codex<Map<String, String>> codexString = TestCodexFactory.get().stringMapCodex( TestCodexFactory.get().stringCodex() );

        try
        {
            codexString.readNotNull( Collections.EMPTY_LIST, deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }

        Codex<Map<ChildBean, String>> codexEntity =
            TestCodexFactory.get().entityMapCodex( TestCodexFactory.get().childBeanCodex(),
                TestCodexFactory.get().stringCodex() );

        try
        {
            codexEntity.readNotNull( Collections.EMPTY_LIST, deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}
