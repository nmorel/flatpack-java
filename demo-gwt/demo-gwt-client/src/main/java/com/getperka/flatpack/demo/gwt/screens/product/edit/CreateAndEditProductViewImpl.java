package com.getperka.flatpack.demo.gwt.screens.product.edit;

import java.math.BigDecimal;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateAndEditProductViewImpl
    extends Composite
    implements ProductView, Editor<Product>
{

    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, CreateAndEditProductViewImpl>
    {
    }

    interface Driver
        extends SimpleBeanEditorDriver<Product, CreateAndEditProductViewImpl>
    {
    }

    @UiField
    ValueBoxEditorDecorator<String> name;

    @UiField
    ValueBoxEditorDecorator<String> notes;

    @UiField
    ValueBoxEditorDecorator<BigDecimal> price;

    private Presenter presenter;

    public CreateAndEditProductViewImpl()
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

    @UiHandler( "validate" )
    void onClickValidate( ClickEvent event )
    {
        presenter.validate();
    }

    @UiHandler( "cancel" )
    void onClickCancel( ClickEvent event )
    {
        presenter.cancel();
    }

}
