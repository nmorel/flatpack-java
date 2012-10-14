package com.getperka.flatpack.demo.gwt.screens.product.edit;

import com.getperka.flatpack.demo.gwt.mvp.BasePlace;
import com.getperka.flatpack.demo.gwt.mvp.TokenEnum;

public class CreateProductPlace
    extends BasePlace
{

    @Override
    public void accept( Visitor visitor )
    {
        visitor.visitPlace( this );
    }

    @Override
    public TokenEnum getToken()
    {
        return TokenEnum.PRODUCT_CREATE;
    }

}
