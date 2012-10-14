package com.getperka.flatpack.demo.gwt.screens.product.edit;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.getperka.flatpack.demo.gwt.mvp.BasePlaceWithParameters;
import com.getperka.flatpack.demo.gwt.mvp.TokenEnum;

public class ConsultProductPlace
    extends BasePlaceWithParameters
{
    private static final String PRODUCT_ID_KEY = "id";

    private UUID id;

    public ConsultProductPlace()
    {

    }

    public ConsultProductPlace( UUID id )
    {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId( UUID id )
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
            return Collections.singletonMap( PRODUCT_ID_KEY, id.toString() );
        }
    }

    @Override
    public void setParameters( Map<String, String> parameters )
        throws MissingParametersException
    {
        id = UUID.fromString( parameters.get( PRODUCT_ID_KEY ) );
    }

    @Override
    public void accept( Visitor visitor )
    {
        visitor.visitPlace( this );
    }

    @Override
    public TokenEnum getToken()
    {
        return TokenEnum.PRODUCT_CONSULT;
    }

}
