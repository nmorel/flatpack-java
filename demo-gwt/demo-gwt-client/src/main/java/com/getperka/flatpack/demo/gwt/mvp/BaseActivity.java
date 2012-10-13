package com.getperka.flatpack.demo.gwt.mvp;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class BaseActivity extends ActivityWithPlace
{
    @Override
    protected abstract void start( AcceptsOneWidget panel, EventBus eventBus );
}
