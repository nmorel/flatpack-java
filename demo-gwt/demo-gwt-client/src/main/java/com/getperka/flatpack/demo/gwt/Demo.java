package com.getperka.flatpack.demo.gwt;

import java.util.logging.Handler;
import java.util.logging.Logger;

import com.getperka.flatpack.demo.gwt.mvp.AppActivityMapper;
import com.getperka.flatpack.demo.gwt.mvp.AppPlaceHistoryMapper;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.getperka.flatpack.demo.gwt.utils.CustomLogFormatter;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class Demo
    implements EntryPoint
{

    @Override
    public void onModuleLoad()
    {
        initLog();

        // Initialize the history handler and activity manager
        EventBus eventBus = new SimpleEventBus();

        ActivityMapper activityMapper = new AppActivityMapper();
        PlaceHistoryMapper placeHistoryMapper = new AppPlaceHistoryMapper();

        PlaceController placeController = new PlaceController( eventBus );
        PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler( placeHistoryMapper );
        placeHistoryHandler.register( placeController, eventBus, new EditProductPlace() );

        SimplePanel panel = new SimplePanel();
        RootPanel.get().add( panel );

        ActivityManager activityManager = new ActivityManager( activityMapper, eventBus );
        activityManager.setDisplay( panel );

        placeHistoryHandler.handleCurrentHistory();

        // GenApi api = new GenApi();
        // api.setServerBase( "/resources" );
        // api.productsGet().execute( new FlatBack<FlatPackEntity<List<Product>>>() {
        //
        // @Override
        // public void onSuccess( FlatPackEntity<List<Product>> result )
        // {
        // RootPanel.get().add( new Label( "products : " ) );
        // for ( Product product : result.getValue() )
        // {
        // RootPanel.get().add( new Label( product.toString() ) );
        // }
        // }
        // } );
        // api.multiplePropertiesGet().execute( new FlatBack<FlatPackEntity<MultiplePropertiesBean>>() {
        //
        // @Override
        // public void onSuccess( FlatPackEntity<MultiplePropertiesBean> result )
        // {
        // RootPanel.get().add( new Label( "MultiplePropertiesBean : " ) );
        // RootPanel.get().add( new Label( result.toString() ) );
        // }
        // } );
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
