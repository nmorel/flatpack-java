package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;

public enum TokenEnum
{
    PRODUCT_EDIT( "product/edit", true )
    {
        @Override
        public BasePlace createNewPlace()
        {
            return new EditProductPlace();
        }
    },
    ERROR( "error", false )
    {
        @Override
        public BasePlace createNewPlace()
        {
            return new ErrorPlace( "Oops, something went wrong!" );
        }
    };

    private String token;
    private boolean hasParameters;

    private TokenEnum( String token, boolean hasParameters )
    {
        this.token = token;
        this.hasParameters = hasParameters;
    }

    public String token()
    {
        return token;
    }

    public boolean hasParameters()
    {
        return hasParameters;
    }

    public abstract BasePlace createNewPlace();

    public static TokenEnum fromToken( String token )
    {
        if ( null == token )
        {
            return null;
        }
        for ( TokenEnum tokenEnum : values() )
        {
            if ( tokenEnum.hasParameters() )
            {
                if ( token.startsWith( tokenEnum.token() + AppPlaceHistoryMapper.SEPARATOR_TOKEN_PARAMETERS ) )
                {
                    return tokenEnum;
                }
            }
            if ( token.equals( tokenEnum.token() ) )
            {
                return tokenEnum;
            }
        }
        return null;
    }
}
