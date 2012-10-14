package com.getperka.flatpack.demo.gwt;

import java.util.logging.Handler;
import java.util.logging.Logger;

import com.getperka.flatpack.demo.gwt.mvp.AppFactory;
import com.getperka.flatpack.demo.gwt.utils.CustomLogFormatter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Demo
    implements EntryPoint
{

    @Override
    public void onModuleLoad()
    {
        initLog();

        RootPanel.get().add( AppFactory.get().getDisplay() );

        AppFactory.get().getPlaceHistoryHandler().handleCurrentHistory();
    }

    private void initLog()
    {
        // initialize logger with custom formatter
        Handler handlers[] = Logger.getLogger( "" ).getHandlers();
        for ( Handler handler : handlers )
        {
            handler.setFormatter( new CustomLogFormatter( true ) );
        }
    }
}
