package com.getperka.flatpack.demo.gwt;

import com.getperka.flatpack.gwt.Unpacker;
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
    @Override
    public void onModuleLoad()
    {
        RequestBuilder request = new RequestBuilder( RequestBuilder.GET, "/resources/map" );
        request.setCallback( new RequestCallback() {

            @Override
            public void onResponseReceived( Request request, Response response )
            {
                Unpacker unpacker = new Unpacker( new DemoTypeContext() );
                unpacker.unpack( response.getText() );
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
    }
}
