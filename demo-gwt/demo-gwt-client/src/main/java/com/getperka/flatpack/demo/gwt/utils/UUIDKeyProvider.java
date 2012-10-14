package com.getperka.flatpack.demo.gwt.utils;

import com.getperka.flatpack.HasUuid;
import com.google.gwt.view.client.ProvidesKey;

public class UUIDKeyProvider<T extends HasUuid>
    implements ProvidesKey<T>
{

    @Override
    public Object getKey( T item )
    {
        return item.getUuid();
    }

}
