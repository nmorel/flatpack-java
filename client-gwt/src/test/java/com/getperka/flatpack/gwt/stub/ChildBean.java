package com.getperka.flatpack.gwt.stub;

import com.getperka.flatpack.BaseHasUuid;

public class ChildBean
    extends BaseHasUuid
{
    private String child;

    public ChildBean()
    {

    }

    public ChildBean( String child )
    {
        this.child = child;
    }

    public String getChild()
    {
        return child;
    }

    public void setChild( String child )
    {
        this.child = child;
    }
}
