// Generated File - DO NOT EDIT
package com.getperka.flatpack.gwt;

import com.google.gwt.core.client.GWT;

/**
 * EntityCodexFactory used to get instance of all the EntityCodex. It's main purpose is to avoid infinite loop.
 */
public class EntityCodexFactory
{

    private static EntityCodexFactory INSTANCE;

    public static EntityCodexFactory get()
    {
        if ( null == INSTANCE )
        {
            INSTANCE = GWT.create( EntityCodexFactory.class );
        }
        return INSTANCE;
    }

    private MultiplePropertiesBeanCodex MultiplePropertiesBeanCodex;
    private ChildBeanCodex ChildBeanCodex;

    /**
     * @return a MultiplePropertiesBeanCodex
     */
    public MultiplePropertiesBeanCodex getMultiplePropertiesBeanCodex()
    {
        if ( null == MultiplePropertiesBeanCodex )
        {
            MultiplePropertiesBeanCodex = new MultiplePropertiesBeanCodex();
            MultiplePropertiesBeanCodex.init();
        }
        return MultiplePropertiesBeanCodex;
    }

    /**
     * @return a ChildBeanCodex
     */
    public ChildBeanCodex getChildBeanCodex()
    {
        if ( null == ChildBeanCodex )
        {
            ChildBeanCodex = new ChildBeanCodex();
            ChildBeanCodex.init();
        }
        return ChildBeanCodex;
    }
}