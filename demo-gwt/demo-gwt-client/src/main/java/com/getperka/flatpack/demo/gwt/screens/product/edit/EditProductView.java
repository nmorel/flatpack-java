package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;

public interface EditProductView
    extends IsWidget
{
    interface Presenter
    {

    }

    void setPresenter( Presenter presenter );

    SimpleBeanEditorDriver<Product, ? extends Editor<Product>> createDriver();

}
