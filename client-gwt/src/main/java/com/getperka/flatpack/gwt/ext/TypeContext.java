package com.getperka.flatpack.gwt.ext;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public interface TypeContext
{
    /**
     * Returns a Class from a payload name or {@code null} if the type is unknown.
     */
    Class<? extends HasUuid> getClass( String simplePayloadName );

    /**
     * Returns the codex
     */
    <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz );
}
