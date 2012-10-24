package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.demo.gwt.mvp.BaseActivity;
import com.getperka.flatpack.demo.gwt.mvp.NoOpVisitor;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ProductView.Presenter;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;
import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.client.FlatBack;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class ProductActivity
    extends BaseActivity
    implements Presenter
{
    private class BackToPreviousPlaceVisitor
        extends NoOpVisitor
    {
        @Override
        public void visitPlace( ConsultProductPlace place )
        {
            placeController.goTo( new ListProductPlace() );
        }

        @Override
        public void visitPlace( CreateProductPlace place )
        {
            placeController.goTo( new ListProductPlace() );
        }

        @Override
        public void visitPlace( EditProductPlace place )
        {
            placeController.goTo( new ConsultProductPlace( place.getId() ) );
        }
    }

    private final ProductView view;

    private final GenApi requestApi;

    private final SimpleBeanEditorDriver<Product, ? extends Editor<Product>> driver;

    private final PlaceController placeController;

    public ProductActivity( ProductView view, GenApi requestApi, PlaceController placeController )
    {
        this.view = view;
        this.requestApi = requestApi;
        this.placeController = placeController;
        this.driver = view.createDriver();
    }

    @Override
    protected void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        view.setPresenter( this );

        getCurrentPlace().accept( this );

        panel.setWidget( view );
    }

    @Override
    public void visitPlace( ConsultProductPlace place )
    {
        requestApi.productsIdGet( place.getId().toString() ).execute( new FlatBack<FlatPackEntity<Product>>() {

            @Override
            public void onSuccess( FlatPackEntity<Product> result )
            {
                driver.edit( result.getValue() );
            }
        } );
    }

    @Override
    public void visitPlace( CreateProductPlace place )
    {
        driver.edit( new Product() );
    }

    @Override
    public void visitPlace( EditProductPlace place )
    {
        requestApi.productsIdGet( place.getId().toString() ).execute( new FlatBack<FlatPackEntity<Product>>() {

            @Override
            public void onSuccess( FlatPackEntity<Product> result )
            {
                driver.edit( result.getValue() );
            }
        } );
    }

    @Override
    public void validate()
    {
        // Problem with the flush here which set all the properties even if they didn't change. The current
        // implementation of dirty properties doesn't look if the value has really changed and set the property as
        // dirty
        Product product = driver.flush();
        if ( !driver.hasErrors() )
        {
            requestApi.entitiesPut( product ).execute(
                new FlatBack<FlatPackEntity<Void>>() {

                    @Override
                    public void onSuccess( FlatPackEntity<Void> result )
                    {
                        getCurrentPlace().accept( new BackToPreviousPlaceVisitor() );
                    }

                    public void onConstraintViolation( java.util.Set<javax.validation.ConstraintViolation<?>> violations )
                    {
                        driver.setConstraintViolations( violations );
                    };
                } );
        }
    }

    @Override
    public void cancel()
    {
        getCurrentPlace().accept( new BackToPreviousPlaceVisitor() );
    }

    @Override
    public void modify()
    {
        placeController.goTo( new EditProductPlace( ( (ConsultProductPlace) getCurrentPlace() ).getId() ) );
    }

}
