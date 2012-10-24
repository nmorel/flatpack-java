package com.getperka.flatpack.gwt.codexes;

import java.util.List;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.Property;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.ext.TypeContext;

/**
 * This codex is used for the packing/unpacking process where you can have a supertype in parameter or return value.
 * 
 * @author Nicolas Morel
 */
public class DynamicEntityCodex<T extends HasUuid>
    extends EntityCodex<T>
{
    private TypeContext typeContext;

    public DynamicEntityCodex( TypeContext typeContext )
    {
        this.typeContext = typeContext;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public T readNotNull( Object element, DeserializationContext context )
    {
        if ( !( element instanceof String ) )
        {
            throw new IllegalArgumentException( "element is not a String : " + element.getClass().getName() );
        }
        UUID uuid = UUID.fromString( (String) element );
        HasUuid entity = context.getEntity( uuid );
        if ( null == entity )
        {
            context.addWarning( entity, "No entity found with the UUID : " + element );
        }
        return (T) entity;
    }

    @Override
    public void scanNotNull( T object, SerializationContext context )
        throws Exception
    {
        Codex<HasUuid> actual = typeContext.getCodex( object.getClass() );
        if ( null == actual )
        {
            context.addWarning( object, "No corresponding codex in TypeContext" );
        }
        else
        {
            actual.scan( object, context );
        }
    }

    @Override
    protected void initProperties( List<Property<T, ?>> properties )
    {
    }

    @Override
    public String getName()
    {
        // Should never be called
        throw new UnsupportedOperationException();
    }

    @Override
    protected T createInstance()
    {
        // Should never be called
        throw new UnsupportedOperationException();
    }

}
