package com.getperka.flatpack.gwt.client.impl;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.client.Api;
import com.getperka.flatpack.gwt.client.Request;
import com.getperka.flatpack.gwt.client.StatusCodeException;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.Response;

public class RequestBaseTestGwt
    extends FlatPackTestCase
{
    private static class StubRequestBase
        extends RequestBase<StubRequestBase, String, String>
        implements Request<StubRequestBase, String, String>
    {
        private final Api api = new StubApi();

        StubRequestBase( Method method, String path, Object... args )
        {
            super( method, path, args );
        }

        @Override
        protected String decodeResponse( Response response )
            throws StatusCodeException
        {
            return response.getText();
        }

        @Override
        protected void writeEntity( RequestBuilder requestBuilder )
        {
        }

        @Override
        protected Api getApi()
        {
            return api;
        }
    }

    private static class StubApi
        implements Api
    {

        @Override
        public String getServerBase()
        {
            return null;
        }

        @Override
        public RequestBuilder filter( RequestBuilder requestBuilder )
        {
            return requestBuilder;
        }

    }

    public void testReplaceArgs()
    {
        // no arguments given
        StubRequestBase request = new StubRequestBase( RequestBuilder.GET, "/products" );
        StringBuilder sb = new StringBuilder();
        request.replaceArgs( "/products/{id}", sb );
        assertEquals( "/products/{id}", sb.toString() );

        // 1 argument given but none in the path
        request = new StubRequestBase( RequestBuilder.GET, "/products", "g4df8g7f8h" );
        sb = new StringBuilder();
        request.replaceArgs( "/products", sb );
        assertEquals( "/products", sb.toString() );

        // 1 argument given and one in path
        request = new StubRequestBase( RequestBuilder.GET, "/products", "g4df8g7f8h" );
        sb = new StringBuilder();
        request.replaceArgs( "/products/{id}", sb );
        assertEquals( "/products/g4df8g7f8h", sb.toString() );

        // 3 argument given and 3 in path
        request = new StubRequestBase( RequestBuilder.GET, "/products", "g4df8g7f8h", "e123", "er6709" );
        sb = new StringBuilder();
        request.replaceArgs( "/products/{id}/{name}/{other}", sb );
        assertEquals( "/products/g4df8g7f8h/e123/er6709", sb.toString() );

        // 3 argument given and 4 in path
        request = new StubRequestBase( RequestBuilder.GET, "/products", "g4df8g7f8h", "e123", "er6709" );
        sb = new StringBuilder();
        request.replaceArgs( "/products/{id}/{name}/{other}/{last}", sb );
        assertEquals( "/products/g4df8g7f8h/e123/er6709/{last}", sb.toString() );

        // 4 argument given and 3 in path
        request = new StubRequestBase( RequestBuilder.GET, "/products", "g4df8g7f8h", "e123", "er6709", "dfgd21" );
        sb = new StringBuilder();
        request.replaceArgs( "/products/{id}/{name}/{other}", sb );
        assertEquals( "/products/g4df8g7f8h/e123/er6709", sb.toString() );
    }

    public void testAddQueryParameters()
    {
        // no parameters
        StubRequestBase request = new StubRequestBase( RequestBuilder.GET, "/products" );
        StringBuilder sb = new StringBuilder( "/products" );
        request.addQueryParameters( sb );
        assertEquals( "/products", sb.toString() );

        // one parameter
        request.queryParameter( "first", 145.4 );
        request.addQueryParameters( sb );
        assertEquals( "/products?first=145.4", sb.toString() );

        // second parameter
        sb = new StringBuilder( "/products" );
        request.queryParameter( "second", "a&z !:;");
        request.addQueryParameters( sb );
        assertEquals( "/products?first=145.4&second=a%26z+!%3A%3B", sb.toString() );
    }
}
