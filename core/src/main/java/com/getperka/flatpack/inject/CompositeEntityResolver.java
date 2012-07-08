package com.getperka.flatpack.inject;

import java.util.Collection;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.EntityResolver;

/**
 * Aggregates all user-provided resolvers into a single instance.
 */
class CompositeEntityResolver implements EntityResolver {

  private final Collection<EntityResolver> resolvers;

  CompositeEntityResolver(Collection<EntityResolver> resolvers) {
    this.resolvers = resolvers;
  }

  @Override
  public <T extends HasUuid> T resolve(Class<T> clazz, UUID uuid) throws Exception {
    for (EntityResolver resolver : resolvers) {
      T toReturn = resolver.resolve(clazz, uuid);
      if (toReturn != null) {
        return toReturn;
      }
    }
    return null;
  }
}
