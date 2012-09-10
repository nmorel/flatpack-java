package com.getperka.flatpack.gwt.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.Window;

public abstract class FlatBack<T>
{
    /**
     * Called when a Request has been successfully executed on the server.
     *
     * @param response a response of type V
     */
    public abstract void onSuccess( T result );

    /**
     * Receives general failure notifications. The default implementation calls {@link Window#alert(String)} with the
     * {@link Throwable} message.
     *
     * @param cause a {@link Throwable} instance
     */
    public void onFailure( Throwable cause )
    {
        Window.alert( cause.getMessage() );
    }

    /**
     * Called if an object sent to the server could not be validated. The default implementation calls
     * {@link #onFailure(Throwable)} if <code>violations</code> is not empty.
     *
     * @param violations a Set of {@link ConstraintViolation} instances
     */
    public void onConstraintViolation( Set<ConstraintViolation<?>> violations )
    {
        if ( !violations.isEmpty() )
        {
            onFailure( new Exception( "The call failed on the server due to a ConstraintViolation" ) );
        }
    }
}
