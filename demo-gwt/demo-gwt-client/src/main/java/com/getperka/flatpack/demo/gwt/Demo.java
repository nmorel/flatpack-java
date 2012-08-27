package com.getperka.flatpack.demo.gwt;

import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.ApiDescription;
import com.getperka.flatpack.demo.gwt.gen.EntityCodexFactory;
import com.getperka.flatpack.demo.gwt.gen.GenTypeContext;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.demo.gwt.gen.ProductCodex;
import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.Unpacker;
import com.getperka.flatpack.gwt.codexes.ListCodex;
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
        RequestBuilder request = new RequestBuilder( RequestBuilder.GET, "/resources/products" );
        request.setCallback( new RequestCallback() {

            @Override
            public void onResponseReceived( Request request, Response response )
            {
                Unpacker unpacker = new Unpacker( new GenTypeContext() );
                FlatPackEntity<List<Product>> entity =
                    unpacker.unpack( response.getText(), new ListCodex<Product>( new ProductCodex() ) );
                // FlatPackEntity<Set<Product>> entity =
                // unpacker.unpack( response.getText(), new SetCodex<Product>( new ProductCodex() ) );
                // FlatPackEntity<Product[]> entity =
                // unpacker.unpack( response.getText(), new ArrayCodex<Product>( new ProductCodex() ) );

                // RootPanel.get().add( new Label( "response : " + response.getText() ) );
                RootPanel.get().add( new Label( "products : " ) );
                for ( Product product : entity.getValue() )
                {
                    RootPanel.get().add( new Label( product.toString() ) );
                }
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

        request = new RequestBuilder( RequestBuilder.GET, "/resources/describe" );
        request.setCallback( new RequestCallback() {

            @Override
            public void onResponseReceived( Request request, Response response )
            {
                Unpacker unpacker = new Unpacker( new GenTypeContext() );
                FlatPackEntity<ApiDescription> entity =
                    unpacker.unpack( response.getText(), EntityCodexFactory.get().getApiDescriptionCodex() );
                // FlatPackEntity<Set<Product>> entity =
                // unpacker.unpack( response.getText(), new SetCodex<Product>( new ProductCodex() ) );
                // FlatPackEntity<Product[]> entity =
                // unpacker.unpack( response.getText(), new ArrayCodex<Product>( new ProductCodex() ) );

                // RootPanel.get().add( new Label( "response : " + response.getText() ) );
                RootPanel.get().add( new Label( "apiDescription : " ) );
                RootPanel.get().add( new Label( entity.getValue().toString() ) );
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
