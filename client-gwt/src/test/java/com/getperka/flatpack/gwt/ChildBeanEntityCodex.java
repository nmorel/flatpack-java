package com.getperka.flatpack.gwt;

import java.util.List;

import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.codexes.StringCodex;
import com.getperka.flatpack.gwt.ext.Property;

public class ChildBeanEntityCodex
    extends EntityCodex<ChildBean>
{

    @Override
    protected void initProperties( List<Property<ChildBean, ?>> properties )
    {
        properties.add( new Property<ChildBean, String>( "child", new StringCodex() ) {

            @Override
            public String getValue( ChildBean object )
            {
                return object.getChild();
            }

            @Override
            public void setValue( ChildBean object, String value )
            {
                object.setChild( value );
            }
        } );
    }

    @Override
    public String getName()
    {
        return "childBean";
    }

    @Override
    protected ChildBean createInstance()
    {
        return new ChildBean();
    }

}
