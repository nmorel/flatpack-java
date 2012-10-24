package com.getperka.flatpack.demo.gwt.screens.product.list;

import java.util.ArrayList;
import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.BaseEntity;
import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.demo.gwt.mvp.BaseActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductView.Presenter;
import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.client.FlatBack;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class ListProductActivity
    extends BaseActivity
    implements Presenter
{
    private final ListProductView view;

    private final GenApi requestApi;

    private final PlaceController placeController;

    public ListProductActivity( ListProductView view, GenApi requestApi, PlaceController placeController )
    {
        this.view = view;
        this.requestApi = requestApi;
        this.placeController = placeController;
    }

    @Override
    protected void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        view.setPresenter( this );

        // Should use productsGet but it is to demonstrate the inheritance support      
        requestApi.entitiesGet().execute( new FlatBack<FlatPackEntity<List<BaseEntity>>>() {

            @Override
            public void onSuccess( FlatPackEntity<List<BaseEntity>> result )
            {
                List<Product> products = new ArrayList<Product>();
                for ( BaseEntity entity : result.getValue() )
                {
                    if ( entity instanceof Product )
                    {
                        products.add( (Product) entity );
                    }
                }
                view.setProducts( products );
            }
        } );

        panel.setWidget( view );
    }

    @Override
    public void onSelection( Product selectedProduct )
    {
        if ( null != selectedProduct )
        {
            placeController.goTo( new ConsultProductPlace( selectedProduct.getUuid() ) );
        }
    }

    @Override
    public void onAdd()
    {
        placeController.goTo( new CreateProductPlace() );
    }

}
