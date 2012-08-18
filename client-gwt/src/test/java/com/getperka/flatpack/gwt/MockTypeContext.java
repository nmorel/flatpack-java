package com.getperka.flatpack.gwt;

import java.util.Map;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.BaseTypeContext;

public class MockTypeContext
    extends BaseTypeContext
{

    @Override
    protected void init( Map<String, Class<? extends HasUuid>> classes,
                         Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> codexes )
    {
        classes.put( "multiplePropertiesBean", MultiplePropertiesBean.class );
        codexes.put( MultiplePropertiesBean.class, new MultiplePropertiesBeanEntityCodex() );

        classes.put( "childBean", ChildBean.class );
        codexes.put( ChildBean.class, new ChildBeanEntityCodex() );
    }

}
