package com.getperka.flatpack.gwt.client;

import com.getperka.flatpack.gwt.FlatPackEntity;

public abstract class FlatBackEntity<T>
    extends FlatBack<FlatPackEntity<T>>
{
    /**
     * Called when a Request has been successfully executed on the server.
     * 
     * @param response a response of type FlatPackEntity<T>
     */
    public void onSuccess( FlatPackEntity<T> result )
    {
        doOnSuccess( result.getValue() );
    }

    /**
     * Called when a Request has been successfully executed on the server.
     * 
     * @param response a response of type T
     */
    protected abstract void doOnSuccess( T result );
}
