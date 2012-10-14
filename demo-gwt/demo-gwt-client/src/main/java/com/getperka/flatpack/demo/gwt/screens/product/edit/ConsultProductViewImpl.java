package com.getperka.flatpack.demo.gwt.screens.product.edit;

import java.math.BigDecimal;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;

public class ConsultProductViewImpl
    extends Composite
    implements ProductView, Editor<Product>
{

    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, ConsultProductViewImpl>
    {
    }

    interface Driver
        extends SimpleBeanEditorDriver<Product, ConsultProductViewImpl>
    {
    }

    @UiField
    Label name;

    @UiField
    Label notes;

    @UiField( provided = true )
    NumberLabel<BigDecimal> price;

    private Presenter presenter;

    public ConsultProductViewImpl()
    {
        price = new NumberLabel<BigDecimal>( NumberFormat.getSimpleCurrencyFormat() );

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

    @UiHandler( "modify" )
    void onClickModify( ClickEvent event )
    {
        presenter.modify();
    }

    @UiHandler( "back" )
    void onClickBack( ClickEvent event )
    {
        presenter.cancel();
    }

}
