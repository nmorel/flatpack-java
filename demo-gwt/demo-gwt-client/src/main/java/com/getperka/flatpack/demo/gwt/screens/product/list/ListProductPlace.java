package com.getperka.flatpack.demo.gwt.screens.product.list;

import com.getperka.flatpack.demo.gwt.mvp.BasePlace;
import com.getperka.flatpack.demo.gwt.mvp.TokenEnum;

public class ListProductPlace
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
        return TokenEnum.PRODUCT_LIST;
    }

}
