package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
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
    public Activity withPlace( BasePlace place )
    {
        place.accept( this );
        return this;
    }

    @Override
    public void visitPlace( EditProductPlace place )
    {
    }

    @Override
    public void visitPlace( ErrorPlace errorPlace )
    {
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
}
