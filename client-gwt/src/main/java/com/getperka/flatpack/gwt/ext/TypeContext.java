package com.getperka.flatpack.gwt.ext;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public interface TypeContext
{
    /**
     * @return a Class from a payload name or {@code null} if the type is unknown.
     */
    Class<? extends HasUuid> getClass( String simplePayloadName );

    /**
     * @return the codex
     */
    <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz );

    /**
     * @return the "type" name used for an entity type in the {@code data} section of the payload.
     */
    String getPayloadName( Class<? extends HasUuid> clazz );
}
