package com.getperka.flatpack.demo.gwt.screens.product.edit;

import java.util.Collections;
import java.util.Map;

import com.getperka.flatpack.demo.gwt.mvp.BasePlaceWithParameters;
import com.getperka.flatpack.demo.gwt.mvp.TokenEnum;

public class EditProductPlace
    extends BasePlaceWithParameters
{
    private static final String PRODUCT_ID_KEY = "id";

    private String id;

    public EditProductPlace()
    {

    }

    public EditProductPlace( String id )
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    @Override
    public Map<String, String> getParameters()
    {
        if ( null == id )
        {
            return Collections.emptyMap();
        }
        else
        {
            return Collections.singletonMap( PRODUCT_ID_KEY, id );
        }
    }

    @Override
    public void setParameters( Map<String, String> parameters )
        throws MissingParametersException
    {
        id = parameters.get( PRODUCT_ID_KEY );
    }

    @Override
    public void accept( Visitor visitor )
    {
        visitor.visitPlace( this );
    }

    @Override
    public TokenEnum getToken()
    {
        return TokenEnum.PRODUCT_EDIT;
    }

}
