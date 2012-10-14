package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.gen.Product;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;

public interface ProductView
    extends IsWidget
{
    interface Presenter
    {
        void validate();

        void cancel();

        void modify();
    }

    void setPresenter( Presenter presenter );

    SimpleBeanEditorDriver<Product, ? extends Editor<Product>> createDriver();

}
