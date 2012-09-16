package com.getperka.flatpack.gwt;

import com.getperka.flatpack.gwt.ext.BaseTypeContext;

public class MockTypeContext
    extends BaseTypeContext
{

    @Override
    protected void init()
    {
        add( "multiplePropertiesBean", MultiplePropertiesBean.class, EntityCodexFactory.get()
            .getMultiplePropertiesBeanCodex() );
        add( "childBean", ChildBean.class, EntityCodexFactory.get().getChildBeanCodex() );
    }

}
