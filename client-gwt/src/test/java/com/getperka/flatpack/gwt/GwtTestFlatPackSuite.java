package com.getperka.flatpack.gwt;

import junit.framework.Test;
import junit.framework.TestCase;

import com.getperka.flatpack.gwt.codexes.BooleanCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.CharacterCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.CollectionCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.DateCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.EntityCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.EnumCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.JsonElementCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.MapCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.NumberCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.StringCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.ToStringCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.UUIDCodexTestGwt;
import com.getperka.flatpack.gwt.codexes.VoidCodexTestGwt;
import com.google.gwt.junit.tools.GWTTestSuite;

public class GwtTestFlatPackSuite
    extends TestCase
{
    public static Test suite()
    {
        GWTTestSuite suite = new GWTTestSuite();

        // Codex
        suite.addTestSuite( NumberCodexTestGwt.class );
        suite.addTestSuite( CharacterCodexTestGwt.class );
        suite.addTestSuite( DateCodexTestGwt.class );
        suite.addTestSuite( BooleanCodexTestGwt.class );
        suite.addTestSuite( CollectionCodexTestGwt.class );
        suite.addTestSuite( EnumCodexTestGwt.class );
        suite.addTestSuite( StringCodexTestGwt.class );
        suite.addTestSuite( ToStringCodexTestGwt.class );
        suite.addTestSuite( UUIDCodexTestGwt.class );
        suite.addTestSuite( VoidCodexTestGwt.class );
        suite.addTestSuite( JsonElementCodexTestGwt.class );
        suite.addTestSuite( MapCodexTestGwt.class );
        suite.addTestSuite( EntityCodexTestGwt.class );

        // Packer/Unpacker
        suite.addTestSuite( PackerTestGwt.class );
        suite.addTestSuite( UnpackerTestGwt.class );

        return suite;
    }
}
