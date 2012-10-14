package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.mvp.BasePlace.Visitor;
import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;

public class NoOpVisitor
    implements Visitor
{

    @Override
    public void visitPlace( ErrorPlace place )
    {
    }

    @Override
    public void visitPlace( ListProductPlace place )
    {
    }

    @Override
    public void visitPlace( ConsultProductPlace place )
    {
    }

    @Override
    public void visitPlace( CreateProductPlace place )
    {
    }

    @Override
    public void visitPlace( EditProductPlace place )
    {
    }

}
