package com.getperka.flatpack.gwt;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.PersistenceMapper;
import com.getperka.flatpack.gwt.codexes.Codex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.SerializationContext;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.getperka.flatpack.gwt.json.JsonWriter;
import com.getperka.flatpack.util.FlatPackCollections;

public class Packer
{
    private static Logger logger = Logger.getLogger( "Packer" );

    private PersistenceMapper persistenceMapper;
    private TypeContext typeContext;

    public Packer( TypeContext typeContext )
    {
        this.typeContext = typeContext;

        // TODO it seems we don't need this mapper on client side
        this.persistenceMapper = new PersistenceMapper() {

            @Override
            public boolean isPersisted( HasUuid entity )
            {
                return false;
            }

            @Override
            public boolean canPersist( Class<? extends HasUuid> entityType )
            {
                return false;
            }
        };
    }

    public <T> String pack( FlatPackEntity<T> entity, Codex<T> codex )
    {
        SerializationContext context = new SerializationContext( new JsonWriter( new StringBuilder() ) );

        if ( entity.getTraversalMode().isSparse() )
        {
            // Sparse entities only include explicitly-enumerated entities
            for ( HasUuid extra : entity.getExtraEntities() )
            {
                context.add( extra );
            }
        }
        else
        {
            codex.scan( entity.getValue(), context );
        }

        JsonWriter json = context.getWriter();
        json.beginObject();
        // value : ['type', 'uuid']
        json.name( "value" );
        codex.write( entity.getValue(), context );

        // errors : { 'foo.bar.baz' : 'May not be null' }
        Set<ConstraintViolation<?>> violations = entity.getConstraintViolations();
        Map<String, String> errors = entity.getExtraErrors();
        if ( !violations.isEmpty() || !errors.isEmpty() )
        {
            json.name( "errors" );
            json.beginObject();
            for ( ConstraintViolation<?> v : violations )
            {
                json.name( v.getPropertyPath().toString() );
                json.value( v.getMessage() );
            }
            for ( Map.Entry<String, String> entry : errors.entrySet() )
            {
                json.name( entry.getKey() ).value( entry.getValue() );
            }
            json.endObject(); // errors
        }

        List<HasUuid> persistent = FlatPackCollections.listForAny();

        // data : { 'type' : [ { ... }, { ... } ], 'otherType' : [ { ... }, { ... } ] }
        json.name( "data" );
        json.beginObject();
        for ( Map.Entry<Class<? extends HasUuid>, List<HasUuid>> entry : collate( context.getEntities() ).entrySet() )
        {
            // Determine how to encode the current type
            EntityCodex<HasUuid> entityCodex = (EntityCodex<HasUuid>) typeContext.getCodex( entry.getKey() );

            json.name( typeContext.getPayloadName( entry.getKey() ) );
            json.beginArray();
            for ( HasUuid toWrite : entry.getValue() )
            {
                if ( persistenceMapper.isPersisted( toWrite ) )
                {
                    persistent.add( toWrite );
                }
                entityCodex.writeProperties( toWrite, context );
            }
            json.endArray();
        }
        json.endObject(); // data

        // Write metadata for any entities
        if ( !persistent.isEmpty() )
        {
            EntityCodex<EntityMetadata> metaCodex = EntityMetadata.getCodex();
            json.name( metaCodex.getName() );
            json.beginArray();
            for ( HasUuid toWrite : persistent )
            {
                EntityMetadata meta = new EntityMetadata();
                meta.setPersistent( true );
                meta.setUuid( toWrite.getUuid() );
                metaCodex.writeProperties( meta, context );
            }
            json.endArray(); // metadata
        }

        // Write extra top-level data keys, which are only used for simple side-channel data
        for ( Map.Entry<String, String> entry : entity.getExtraData().entrySet() )
        {
            json.name( entry.getKey() ).value( entry.getValue() );
        }

        // Write extra warnings, some of which may be from the serialization process
        Map<UUID, String> codexWarnings = context.getWarnings();
        Map<String, String> warnings = entity.getExtraWarnings();
        if ( !codexWarnings.isEmpty() || !warnings.isEmpty() )
        {
            json.name( "warnings" );
            json.beginObject();
            for ( Map.Entry<UUID, String> entry : codexWarnings.entrySet() )
            {
                json.name( entry.getKey().toString() ).value( entry.getValue() );
            }
            for ( Map.Entry<String, String> entry : warnings.entrySet() )
            {
                json.name( entry.getKey() ).value( entry.getValue() );
            }
            json.endObject(); // warnings
        }
        json.endObject(); // core payload

        context.runPostWork();

        return json.toString();
    }

    /**
     * Creates a map representing the {@code data} payload structure from an assortment of entities. This method also
     * filters out persistent objects that do not have any local mutations.
     */
    private Map<Class<? extends HasUuid>, List<HasUuid>> collate( Set<HasUuid> entities )
    {
        Map<Class<? extends HasUuid>, List<HasUuid>> toReturn = FlatPackCollections.mapForIteration();

        for ( HasUuid entity : entities )
        {
            Class<? extends HasUuid> key = entity.getClass();

            // Ignore any dirty-tracking entity with no mutations
            if ( entity instanceof PersistenceAware )
            {
                PersistenceAware maybeDirty = (PersistenceAware) entity;
                if ( maybeDirty.wasPersistent() && maybeDirty.dirtyPropertyNames().isEmpty() )
                {
                    continue;
                }
            }

            List<HasUuid> list = toReturn.get( key );
            if ( list == null )
            {
                list = FlatPackCollections.listForAny();
                toReturn.put( key, list );
            }
            list.add( entity );
        }

        return toReturn;
    }

}
