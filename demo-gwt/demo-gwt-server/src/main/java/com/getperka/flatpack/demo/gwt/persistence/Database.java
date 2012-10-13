package com.getperka.flatpack.demo.gwt.persistence;

import java.util.List;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;

public interface Database
{

    /**
     * Reset the contents of the fake store.
     */
    void clear();

    /**
     * Return all entities of type {@code clazz} that were passed to {@link #persist(HasUuid)}.
     */
    <T extends HasUuid> List<T> get( Class<T> clazz );

    /**
     * Return a specific entity passed to {@link #persist(HasUuid)}.
     */
    <T extends HasUuid> T get( Class<T> clazz, UUID uuid );

    /**
     * Return a specific entity passed to {@link #persist(HasUuid)}.
     */
    <T extends HasUuid> T get( Class<T> clazz, String id );

    /**
     * Add an entity. This will not add any referenced entities, so it isn't truly representative of what a real
     * persistence engine would do.
     */
    void persist( HasUuid entity );

}