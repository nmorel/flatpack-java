package com.getperka.flatpack.demo.gwt.screens.product.list;

import java.util.Comparator;
import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.demo.gwt.utils.UUIDKeyProvider;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class ListProductViewImpl
    extends Composite
    implements ListProductView, Editor<Product>
{
    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, ListProductViewImpl>
    {
    }

    @UiField( provided = true )
    CellTable<Product> table;

    @UiField( provided = true )
    SimplePager pager;

    private ListDataProvider<Product> dataProvider;

    private SingleSelectionModel<Product> selectionModel;

    private Presenter presenter;

    public ListProductViewImpl()
    {
        UUIDKeyProvider<Product> keyProvider = new UUIDKeyProvider<Product>();

        table = new CellTable<Product>( 20, keyProvider );

        dataProvider = new ListDataProvider<Product>( keyProvider );
        dataProvider.addDataDisplay( table );

        ListHandler<Product> sortHandler = new ListHandler<Product>( dataProvider.getList() );
        table.addColumnSortHandler( sortHandler );

        // Name column
        Column<Product, ?> nameColumn = new TextColumn<Product>() {

            @Override
            public String getValue( Product object )
            {
                return object.getName();
            }
        };
        nameColumn.setSortable( true );
        sortHandler.setComparator( nameColumn, new Comparator<Product>() {

            @Override
            public int compare( Product o1, Product o2 )
            {
                return o1.getName().compareTo( o2.getName() );
            }
        } );
        table.addColumn( nameColumn, "Name" );
        // sorted on name by default
        table.getColumnSortList().push( nameColumn );

        // Price column
        Column<Product, ?> priceColumn =
            new Column<Product, Number>( new NumberCell( NumberFormat.getSimpleCurrencyFormat() ) ) {

                @Override
                public Number getValue( Product object )
                {
                    return object.getPrice();
                }
            };
        priceColumn.setSortable( true );
        sortHandler.setComparator( priceColumn, new Comparator<Product>() {

            @Override
            public int compare( Product o1, Product o2 )
            {
                return o1.getPrice().compareTo( o2.getPrice() );
            }
        } );
        table.addColumn( priceColumn, "Price" );

        selectionModel = new SingleSelectionModel<Product>( keyProvider );
        selectionModel.addSelectionChangeHandler( new Handler() {

            @Override
            public void onSelectionChange( SelectionChangeEvent event )
            {
                if ( null != presenter )
                {
                    presenter.onSelection( selectionModel.getSelectedObject() );
                }
            }
        } );
        table.setSelectionModel( selectionModel );

        pager = new SimplePager();
        pager.setDisplay( table );

        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public void setPresenter( Presenter presenter )
    {
        this.presenter = presenter;
    }

    @Override
    public void setProducts( List<Product> products )
    {
        selectionModel.clear();
        dataProvider.getList().clear();
        dataProvider.getList().addAll( products );
    }

    @UiHandler( "add" )
    void onClickAdd( ClickEvent event )
    {
        presenter.onAdd();
    }

}
