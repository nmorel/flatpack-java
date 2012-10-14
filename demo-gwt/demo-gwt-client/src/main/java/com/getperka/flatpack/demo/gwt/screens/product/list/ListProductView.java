package com.getperka.flatpack.demo.gwt.screens.product.list;

import java.util.List;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.user.client.ui.IsWidget;

public interface ListProductView
    extends IsWidget
{
    interface Presenter
    {

        void onSelection( Product selectedProduct );

        void onAdd();

    }

    void setPresenter( Presenter presenter );

    void setProducts( List<Product> products );

}
