package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Nicolas Morel
 */
public class ActivityWithPlace
    extends AbstractActivity
    implements BasePlace.Visitor
{
    private BasePlace currentPlace;

    public Activity withPlace( BasePlace place )
    {
        currentPlace = place;
        return this;
    }

    protected BasePlace getCurrentPlace()
    {
        return currentPlace;
    }

    @Override
    public void start( AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus )
    {
        start( panel, (EventBus) eventBus );
    }

    protected void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        // To implements by children
    }

    @Override
    public void visitPlace( ErrorPlace errorPlace )
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
