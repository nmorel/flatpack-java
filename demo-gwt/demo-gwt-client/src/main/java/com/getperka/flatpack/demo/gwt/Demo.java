package com.getperka.flatpack.demo.gwt;

import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.MultiplePropertiesBean;
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
        api.multiplePropertiesGet().execute( new FlatBack<FlatPackEntity<MultiplePropertiesBean>>() {

            @Override
            public void onSuccess( FlatPackEntity<MultiplePropertiesBean> result )
            {
                RootPanel.get().add( new Label( "MultiplePropertiesBean : " ) );
                RootPanel.get().add( new Label( result.toString() ) );
            }
        } );
    }
}
