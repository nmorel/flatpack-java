package com.getperka.flatpack.gwt.ext;

import java.util.HashMap;
import java.util.Map;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public abstract class BaseTypeContext
    implements TypeContext
{
    private final Map<String, Class<? extends HasUuid>> payloadToClass =
        new HashMap<String, Class<? extends HasUuid>>();
    private final Map<Class<? extends HasUuid>, String> classToPayload =
        new HashMap<Class<? extends HasUuid>, String>();
    private final Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> classToCodex =
        new HashMap<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>>();

    protected BaseTypeContext()
    {
        init();
    }

    /**
     * Initialize the maps
     */
    protected abstract void init();

    /**
     * Add an entity to the context
     *
     * @param payload Entity's name that will appear in the payload
     * @param clazz Entity's class
     * @param codex Entity's codex
     */
    protected <T extends HasUuid> void add( String payload, Class<T> clazz, EntityCodex<T> codex )
    {
        payloadToClass.put( payload, clazz );
        classToPayload.put( clazz, payload );
        classToCodex.put( clazz, codex );
    }

    @Override
    public Class<? extends HasUuid> getClass( String simplePayloadName )
    {
        return payloadToClass.get( simplePayloadName );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz )
    {
        return (EntityCodex<T>) classToCodex.get( clazz );
    }

    @Override
    public String getPayloadName( Class<? extends HasUuid> clazz )
    {
        return classToPayload.get( clazz );
    }

}
