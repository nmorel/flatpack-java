package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.gen.GenApi;
import com.getperka.flatpack.demo.gwt.screens.error.ErrorActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductActivity;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductView;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductViewImpl;

public class AppFactory
{
    private static final AppFactory instance = new AppFactory();

    public static AppFactory get()
    {
        return instance;
    }

    private GenApi requestApi;

    private ErrorActivity errorActivity;

    private EditProductView editProductView;

    public GenApi getRequestApi()
    {
        if ( null == requestApi )
        {
            requestApi = new GenApi();
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

    public EditProductActivity getEditProductActivity()
    {
        return new EditProductActivity( getEditProductView(), getRequestApi() );
    }

    public EditProductView getEditProductView()
    {
        if ( null == editProductView )
        {
            editProductView = new EditProductViewImpl();
        }
        return editProductView;
    }
}
