package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EditProductViewImpl
    extends Composite
    implements EditProductView, Editor<Product>
{
    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, EditProductViewImpl>
    {
    }

    interface Driver
        extends SimpleBeanEditorDriver<Product, EditProductViewImpl>
    {
    }

    @UiField
    Label name;

    private Presenter presenter;

    public EditProductViewImpl()
    {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public void setPresenter( Presenter presenter )
    {
        this.presenter = presenter;
    }

    @Override
    public SimpleBeanEditorDriver<Product, ? extends Editor<Product>> createDriver()
    {
        Driver driver = GWT.create( Driver.class );
        driver.initialize( this );
        return driver;
    }

}
