package com.getperka.flatpack.gwt.stub;

import com.getperka.flatpack.gwt.codexes.AbstractCodexFactory;
import com.google.gwt.core.client.GWT;

public class TestCodexFactory
    extends AbstractCodexFactory
{
    private static TestCodexFactory INSTANCE;

    public static TestCodexFactory get()
    {
        if ( null == INSTANCE )
        {
            INSTANCE = GWT.create( TestCodexFactory.class );
        }
        return INSTANCE;
    }

    private MultiplePropertiesBeanCodex MultiplePropertiesBeanCodex;
    private ChildBeanCodex ChildBeanCodex;

    /**
     * @return a {@link MultiplePropertiesBeanCodex}
     */
    public MultiplePropertiesBeanCodex multiplePropertiesBeanCodex()
    {
        if ( null == MultiplePropertiesBeanCodex )
        {
            MultiplePropertiesBeanCodex = new MultiplePropertiesBeanCodex();
            MultiplePropertiesBeanCodex.init();
        }
        return MultiplePropertiesBeanCodex;
    }

    /**
     * @return a {@link ChildBeanCodex}
     */
    public ChildBeanCodex childBeanCodex()
    {
        if ( null == ChildBeanCodex )
        {
            ChildBeanCodex = new ChildBeanCodex();
            ChildBeanCodex.init();
        }
        return ChildBeanCodex;
    }
}
