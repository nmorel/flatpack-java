package com.getperka.flatpack.gwt.ext;

import java.util.HashMap;
import java.util.Map;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public abstract class BaseTypeContext
    implements TypeContext
{
    private final Map<String, Class<? extends HasUuid>> classes = new HashMap<String, Class<? extends HasUuid>>();
    private final Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> codexes =
        new HashMap<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>>();

    protected BaseTypeContext()
    {
        init( classes, codexes );
    }

    /**
     * Initialize the map of classes and codexes
     *
     * @param classes map entity name to entity class
     * @param codexes map entity class to entity codex
     */
    protected abstract void init( Map<String, Class<? extends HasUuid>> classes,
                                  Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> codexes );

    @Override
    public Class<? extends HasUuid> getClass( String simplePayloadName )
    {
        return classes.get( simplePayloadName );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz )
    {
        return (EntityCodex<T>) codexes.get( clazz );
    }

}
