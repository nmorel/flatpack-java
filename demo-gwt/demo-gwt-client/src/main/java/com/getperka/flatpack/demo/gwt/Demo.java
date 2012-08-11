package com.getperka.flatpack.demo.gwt;

import com.getperka.flatpack.gwt.BaseHasUuid;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Demo
    implements EntryPoint
{
    private static class TestUuid
        extends BaseHasUuid
    {

    }

    @Override
    public void onModuleLoad()
    {
        RequestBuilder request = new RequestBuilder( RequestBuilder.GET, "/resources/hello" );
        request.setCallback( new RequestCallback() {

            @Override
            public void onResponseReceived( Request request, Response response )
            {
                RootPanel.get().add( new Label( response.getText() ) );
            }

            @Override
            public void onError( Request request, Throwable exception )
            {
                exception.printStackTrace();
                Window.alert( "Error" );
            }
        } );
        try
        {
            request.send();
        }
        catch ( RequestException e )
        {
            e.printStackTrace();
            Window.alert( "Error" );
        }

        TestUuid test1 = new TestUuid();
        System.out.println( test1.getUuid() );

        TestUuid test2 = new TestUuid();
        System.out.println( test2.getUuid() );

        System.out.println( "equals : " + ( test1.equals( test2 ) ) );

        test2.setUuid( test1.getUuid() );
        System.out.println( "equals : " + ( test1.equals( test2 ) ) );
    }

}
