package com.getperka.flatpack.demo.gwt.mvp;


public abstract class BasePlaceWithParameters
    extends BasePlace
    implements PlaceWithParameters
{
    @Override
    public PlaceWithParameters hasParameters()
    {
        return this;
    }
}
