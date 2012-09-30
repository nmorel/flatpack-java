package com.getperka.flatpack.gwt.stub;

import com.getperka.flatpack.gwt.ext.BaseTypeContext;

public class TestTypeContext
    extends BaseTypeContext
{

    @Override
    protected void init()
    {
        add( "multiplePropertiesBean", MultiplePropertiesBean.class, TestCodexFactory.get()
            .multiplePropertiesBeanCodex() );
        add( "childBean", ChildBean.class, TestCodexFactory.get().childBeanCodex() );
    }

}
