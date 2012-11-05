package com.getperka.flatpack.demo.gwt;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.getperka.flatpack.demo.gwt.mvp.AppFactory;
import com.getperka.flatpack.demo.gwt.utils.CustomLogFormatter;
import com.getperka.flatpack.demo.gwt.utils.RemoteLoggingHandler;
import com.google.common.collect.Lists;
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
        Logger rootLogger = Logger.getLogger( "" );
        rootLogger.addHandler( new RemoteLoggingHandler( Level.INFO, Lists.newArrayList( "Packer", "Unpacker" ) ) );
        Handler handlers[] = rootLogger.getHandlers();
        for ( Handler handler : handlers )
        {
            handler.setFormatter( new CustomLogFormatter( true ) );
        }
    }
}
