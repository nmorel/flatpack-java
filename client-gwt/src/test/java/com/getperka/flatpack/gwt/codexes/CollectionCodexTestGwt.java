package com.getperka.flatpack.gwt.codexes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.stub.ChildBean;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;
import com.getperka.flatpack.util.FlatPackCollections;

public class CollectionCodexTestGwt
    extends FlatPackTestCase
{

    public void testListString()
    {
        Codex<List<String>> codex = TestCodexFactory.get().listCodex( TestCodexFactory.get().stringCodex() );

        testCodex( codex, Arrays.asList( "toto", "Flatpack", null, "GWT" ) );
        testCodex( codex, Collections.<String> emptyList() );
        testCodex( codex, Collections.singletonList( "toto" ) );
        testCodex( codex, null );
    }

    public void testListEntity()
    {
        Codex<List<ChildBean>> codex = TestCodexFactory.get().listCodex( TestCodexFactory.get().childBeanCodex() );

        ChildBean entity1 = new ChildBean( "toto" );
        ChildBean entity2 = new ChildBean( "Flatpack" );
        ChildBean entity3 = new ChildBean( "GWT" );

        Set<HasUuid> scanned = FlatPackCollections.setForIteration();
        testCodex( codex, Arrays.asList( entity1, entity2, null, entity3, entity2 ), scanned );
        assertEquals( new HashSet<ChildBean>( Arrays.asList( entity1, entity2, entity3 ) ), scanned );

        scanned = FlatPackCollections.setForIteration();
        testCodex( codex, Collections.<ChildBean> emptyList(), scanned );
        assertEquals( Collections.emptySet(), scanned );

        scanned = FlatPackCollections.setForIteration();
        testCodex( codex, Collections.singletonList( entity1 ), scanned );
        assertEquals( Collections.singleton( entity1 ), scanned );

        testCodex( codex, null );
    }

    public void testSetString()
    {
        Codex<Set<String>> codex = TestCodexFactory.get().setCodex( TestCodexFactory.get().stringCodex() );

        testCodex( codex, new HashSet<String>( Arrays.asList( "toto", "Flatpack", null, "GWT" ) ) );
        testCodex( codex, Collections.<String> emptySet() );
        testCodex( codex, Collections.singleton( "toto" ) );
        testCodex( codex, null );
    }

    public void testSetEntity()
    {
        Codex<Set<ChildBean>> codex = TestCodexFactory.get().setCodex( TestCodexFactory.get().childBeanCodex() );

        ChildBean entity1 = new ChildBean( "toto" );
        ChildBean entity2 = new ChildBean( "Flatpack" );
        ChildBean entity3 = new ChildBean( "GWT" );

        Set<HasUuid> scanned = FlatPackCollections.setForIteration();
        testCodex( codex, new HashSet<ChildBean>(Arrays.asList( entity1, entity2, null, entity3, entity2 )), scanned );
        assertEquals( new HashSet<ChildBean>( Arrays.asList( entity1, entity2, entity3 ) ), scanned );

        scanned = FlatPackCollections.setForIteration();
        testCodex( codex, Collections.<ChildBean> emptySet(), scanned );
        assertEquals( Collections.emptySet(), scanned );

        scanned = FlatPackCollections.setForIteration();
        testCodex( codex, Collections.singleton( entity1 ), scanned );
        assertEquals( Collections.singleton( entity1 ), scanned );

        testCodex( codex, null );
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

    public void testIncorrectType()
        throws Exception
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
