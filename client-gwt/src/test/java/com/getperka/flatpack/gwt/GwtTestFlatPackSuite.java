package com.getperka.flatpack.gwt;

import junit.framework.Test;
import junit.framework.TestCase;

import com.google.gwt.junit.tools.GWTTestSuite;

public class GwtTestFlatPackSuite
    extends TestCase
{
    public static Test suite()
    {
        GWTTestSuite suite = new GWTTestSuite();
        suite.addTestSuite( UnpackTestGwt.class );
        return suite;
    }
}
