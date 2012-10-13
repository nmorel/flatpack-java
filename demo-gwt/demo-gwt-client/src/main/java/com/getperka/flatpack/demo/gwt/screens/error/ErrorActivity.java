package com.getperka.flatpack.demo.gwt.screens.error;

import com.getperka.flatpack.demo.gwt.mvp.ActivityWithPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ErrorActivity
    extends ActivityWithPlace
{
    private static Binder uiBinder = GWT.create( Binder.class );

    interface Binder
        extends UiBinder<Widget, ErrorActivity>
    {
    }

    private ErrorPlace currentPlace;

    private Widget widget;

    @UiField
    Label errorLabel;

    @Override
    public void visitPlace( ErrorPlace errorPlace )
    {
        this.currentPlace = errorPlace;
    }

    @Override
    public void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        if ( null == widget )
        {
            widget = uiBinder.createAndBindUi( this );
        }
        errorLabel.setText( currentPlace.getMessage() );
        panel.setWidget( widget );
    }

}
