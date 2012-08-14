package com.getperka.flatpack.demo.gwt;

import java.util.HashMap;
import java.util.Map;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.TypeContext;
import com.getperka.flatpack.gwt.codexes.EntityCodex;

public class DemoTypeContext
    implements TypeContext
{
    private final Map<String, Class<? extends HasUuid>> classes = new HashMap<String, Class<? extends HasUuid>>();
    private final Map<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>> codexes =
        new HashMap<Class<? extends HasUuid>, EntityCodex<? extends HasUuid>>();

    public DemoTypeContext()
    {
        classes.put( "product", Product.class );
        codexes.put( Product.class, new ProductEntityCodex() );
    }

    /**
     * Returns a Class from a payload name or {@code null} if the type is unknown.
     */
    public Class<? extends HasUuid> getClass( String simplePayloadName )
    {
        return classes.get( simplePayloadName );
    }

    /**
     * Returns the codex
     */
    @SuppressWarnings( "unchecked" )
    public <T extends HasUuid> EntityCodex<T> getCodex( Class<? extends T> clazz )
    {
        return (EntityCodex<T>) codexes.get( clazz );
    }
}
