package com.getperka.flatpack.demo.gwt.persistence;

import java.util.UUID;

import javax.persistence.EntityManager;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.EntityResolver;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DatabaseResolver
    implements EntityResolver
{
    private final Provider<EntityManager> entityManager;

    @Inject
    public DatabaseResolver( Provider<EntityManager> entityManager )
    {
        this.entityManager = entityManager;
    }

    @Override
    public <T extends HasUuid> T resolve( Class<T> clazz, UUID uuid )
        throws Exception
    {
        return entityManager.get().find( clazz, uuid.toString() );
    }
}
