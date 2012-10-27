package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.gen.GenTypeContext;
import com.getperka.flatpack.demo.gwt.screens.error.ErrorActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductViewImpl;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateAndEditProductViewImpl;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ProductActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ProductView;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductActivity;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductView;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductViewImpl;
import com.getperka.flatpack.gwt.FlatPack;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppFactory
{
    private static final AppFactory instance = new AppFactory();

    public static AppFactory get()
    {
        return instance;
    }

    private final EventBus eventBus;

    private final ActivityMapper activityMapper;

    private final PlaceHistoryMapper placeHistoryMapper;

    private final PlaceController placeController;

    private final PlaceHistoryHandler placeHistoryHandler;

    private final ActivityManager activityManager;

    private final SimplePanel display;

    private FlatPack flatPack;

    private GenApi requestApi;

    private ErrorActivity errorActivity;

    private ListProductView listProductView;

    private ProductView consultProductView;

    private ProductView createAndEditProductView;

    private AppFactory()
    {
        // Initialize the history handler and activity manager
        eventBus = new SimpleEventBus();

        activityMapper = new AppActivityMapper();
        placeHistoryMapper = new AppPlaceHistoryMapper();

        placeController = new PlaceController( eventBus );
        placeHistoryHandler = new PlaceHistoryHandler( placeHistoryMapper );
        placeHistoryHandler.register( placeController, eventBus, new ListProductPlace() );

        display = new SimplePanel();

        activityManager = new ActivityManager( activityMapper, eventBus );
        activityManager.setDisplay( display );
    }

    public PlaceHistoryHandler getPlaceHistoryHandler()
    {
        return placeHistoryHandler;
    }

    public Widget getDisplay()
    {
        return display;
    }

    public FlatPack getFlatPack()
    {
        if ( null == flatPack )
        {
            flatPack = FlatPack.builder( new GenTypeContext() ).withPrettyPrint( true ).withVerbose( true ).create();
        }
        return flatPack;
    }

    public GenApi getRequestApi()
    {
        if ( null == requestApi )
        {
            requestApi = new GenApi( getFlatPack() );
            requestApi.setServerBase( "/resources" );
        }
        return requestApi;
    }

    public ErrorActivity getErrorActivity()
    {
        // singleton
        if ( null == errorActivity )
        {
            errorActivity = new ErrorActivity();
        }
        return errorActivity;
    }

    public ListProductActivity getListProductActivity()
    {
        return new ListProductActivity( getListProductView(), getRequestApi(), placeController );
    }

    public ListProductView getListProductView()
    {
        if ( null == listProductView )
        {
            listProductView = new ListProductViewImpl();
        }
        return listProductView;
    }

    public ProductActivity getConsultProductActivity()
    {
        return new ProductActivity( getConsultProductView(), getRequestApi(), placeController );
    }

    public ProductActivity getCreateProductActivity()
    {
        return new ProductActivity( getCreateProductView(), getRequestApi(), placeController );
    }

    public ProductActivity getEditProductActivity()
    {
        return new ProductActivity( getEditProductView(), getRequestApi(), placeController );
    }

    public ProductView getConsultProductView()
    {
        if ( null == consultProductView )
        {
            consultProductView = new ConsultProductViewImpl();
        }
        return consultProductView;
    }

    public ProductView getCreateProductView()
    {
        if ( null == createAndEditProductView )
        {
            createAndEditProductView = new CreateAndEditProductViewImpl();
        }
        return createAndEditProductView;
    }

    public ProductView getEditProductView()
    {
        return getCreateProductView();
    }
}
