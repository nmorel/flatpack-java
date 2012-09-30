package com.getperka.flatpack.fast;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FastTest
{
    @Test
    public void test()
        throws IOException
    {
        JavaDialect.apiIsPublic = true;
        JavaDialect.stripPathSegments = 0;
        JavaDialect.typePrefix = "";
        JavaDialect.packageName = "test";
        FastTool tool = new FastTool();
        tool.generate( new File( "src/test/resources/api.js" ).toURI(), "gwt", new File( "target/test-output/flatpack" ) );
    }
}
