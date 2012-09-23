package com.getperka.flatpack.demo.gwt;

import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.client.FlatBack;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Demo
    implements EntryPoint
{
    @Override
    public void onModuleLoad()
    {
        GenApi api = new GenApi();
        api.productsGet().execute( new FlatBack<FlatPackEntity<List<Product>>>() {

            @Override
            public void onSuccess( FlatPackEntity<List<Product>> result )
            {
                RootPanel.get().add( new Label( "products : " ) );
                for ( Product product : result.getValue() )
                {
                    RootPanel.get().add( new Label( product.toString() ) );
                }
            }
        } );

        // RequestBuilder request = new RequestBuilder( RequestBuilder.GET, "/resources/products" );
        // request.setCallback( new RequestCallback() {
        //
        // @Override
        // public void onResponseReceived( Request request, Response response )
        // {
        // Unpacker unpacker = new Unpacker( new GenTypeContext() );
        // FlatPackEntity<List<Product>> entity =
        // unpacker.unpack( response.getText(), new ListCodex<Product>( new ProductCodex() ) );
        // // FlatPackEntity<Set<Product>> entity =
        // // unpacker.unpack( response.getText(), new SetCodex<Product>( new ProductCodex() ) );
        // // FlatPackEntity<Product[]> entity =
        // // unpacker.unpack( response.getText(), new ArrayCodex<Product>( new ProductCodex() ) );
        //
        // // RootPanel.get().add( new Label( "response : " + response.getText() ) );
        // RootPanel.get().add( new Label( "products : " ) );
        // for ( Product product : entity.getValue() )
        // {
        // RootPanel.get().add( new Label( product.toString() ) );
        // }
        // }
        //
        // @Override
        // public void onError( Request request, Throwable exception )
        // {
        // exception.printStackTrace();
        // Window.alert( "Error" );
        // }
        // } );
        // try
        // {
        // request.send();
        // }
        // catch ( RequestException e )
        // {
        // e.printStackTrace();
        // Window.alert( "Error" );
        // }
        //
        // request = new RequestBuilder( RequestBuilder.GET, "/resources/describe" );
        // request.setCallback( new RequestCallback() {
        //
        // @Override
        // public void onResponseReceived( Request request, Response response )
        // {
        // Unpacker unpacker = new Unpacker( new GenTypeContext() );
        // FlatPackEntity<ApiDescription> entity =
        // unpacker.unpack( response.getText(), EntityCodexFactory.get().getApiDescriptionCodex() );
        // // FlatPackEntity<Set<Product>> entity =
        // // unpacker.unpack( response.getText(), new SetCodex<Product>( new ProductCodex() ) );
        // // FlatPackEntity<Product[]> entity =
        // // unpacker.unpack( response.getText(), new ArrayCodex<Product>( new ProductCodex() ) );
        //
        // // RootPanel.get().add( new Label( "response : " + response.getText() ) );
        // RootPanel.get().add( new Label( "apiDescription : " ) );
        // RootPanel.get().add( new Label( entity.getValue().toString() ) );
        // }
        //
        // @Override
        // public void onError( Request request, Throwable exception )
        // {
        // exception.printStackTrace();
        // Window.alert( "Error" );
        // }
        // } );
        // try
        // {
        // Request req = request.send();
        // req.cancel();
        // }
        // catch ( RequestException e )
        // {
        // e.printStackTrace();
        // Window.alert( "Error" );
        // }
    }
}
