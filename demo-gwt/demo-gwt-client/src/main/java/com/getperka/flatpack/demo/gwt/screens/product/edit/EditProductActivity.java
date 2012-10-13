package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.demo.gwt.mvp.BaseActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductView.Presenter;
import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.client.FlatBack;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class EditProductActivity
    extends BaseActivity
    implements Presenter
{
    private final EditProductView view;

    private final GenApi requestApi;

    private final SimpleBeanEditorDriver<Product, ? extends Editor<Product>> driver;

    private EditProductPlace currentPlace;

    public EditProductActivity( EditProductView view, GenApi requestApi )
    {
        this.view = view;
        this.requestApi = requestApi;
        this.driver = view.createDriver();
    }

    @Override
    public void visitPlace( EditProductPlace errorPlace )
    {
        this.currentPlace = errorPlace;
    }

    @Override
    protected void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        view.setPresenter( this );

        requestApi.productsIdGet( currentPlace.getId() ).execute( new FlatBack<FlatPackEntity<Product>>() {

            @Override
            public void onSuccess( FlatPackEntity<Product> result )
            {
                driver.edit( result.getValue() );
            }
        } );

        panel.setWidget( view );
    }

}
