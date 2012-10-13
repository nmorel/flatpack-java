package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.google.gwt.place.shared.Place;

public abstract class BasePlace
    extends Place
{
    public interface Visitor
    {
        void visitPlace( EditProductPlace place );

        void visitPlace( ErrorPlace place );
    }

    public abstract void accept( Visitor visitor );

    public abstract TokenEnum getToken();

    public PlaceWithParameters hasParameters()
    {
        return null;
    }
}
