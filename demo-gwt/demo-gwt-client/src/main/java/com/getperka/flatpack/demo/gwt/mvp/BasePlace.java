package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;
import com.google.gwt.place.shared.Place;

public abstract class BasePlace
    extends Place
{
    public interface Visitor
    {
        void visitPlace( ErrorPlace place );

        void visitPlace( ListProductPlace place );

        void visitPlace( ConsultProductPlace place );

        void visitPlace( CreateProductPlace place );

        void visitPlace( EditProductPlace place );
    }

    public abstract void accept( Visitor visitor );

    public abstract TokenEnum getToken();

    public PlaceWithParameters hasParameters()
    {
        return null;
    }
}
