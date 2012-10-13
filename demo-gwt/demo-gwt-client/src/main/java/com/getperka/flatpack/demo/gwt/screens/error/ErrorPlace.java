package com.getperka.flatpack.demo.gwt.screens.error;

import com.getperka.flatpack.demo.gwt.mvp.BasePlace;
import com.getperka.flatpack.demo.gwt.mvp.TokenEnum;

public class ErrorPlace
    extends BasePlace
{
    private String message;

    public ErrorPlace( String message )
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    @Override
    public void accept( Visitor visitor )
    {
        visitor.visitPlace( this );
    }

    @Override
    public TokenEnum getToken()
    {
        return TokenEnum.ERROR;
    }

}
