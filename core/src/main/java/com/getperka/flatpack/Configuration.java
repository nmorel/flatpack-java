/*
 * #%L
 * FlatPack serialization code
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.PrincipalMapper;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * Provides configuration options when creating {@link FlatPack} instances.
 */
public class Configuration {
  private final Set<Class<?>> allTypes = FlatPackCollections.setForIteration();
  private List<CodexMapper> extraMappers = FlatPackCollections.listForAny();
  private boolean ignoreUnresolvableTypes = false;
  private boolean prettyPrint;
  private List<PersistenceMapper> persistenceMappers;
  private PrincipalMapper principalMapper;
  private List<EntityResolver> resolvers = FlatPackCollections.listForAny();
  private RoleMapper roleMapper;
  private boolean verbose;

  /**
   * Add an additional CodexMapper which will be queried before any previously-added mappers.
   */
  public Configuration addCodexMapper(CodexMapper mapper) {
    extraMappers.add(0, mapper);
    return this;
  }

  /**
   * Add an additional {@link EntityResolver} which will be queried before any previously-added
   * resolvers.
   */
  public Configuration addEntityResolver(EntityResolver resolver) {
    resolvers.add(0, resolver);
    return this;
  }

  public Configuration addPersistenceMapper(PersistenceMapper mapper) {
    if (persistenceMappers == null) {
      persistenceMappers = FlatPackCollections.listForAny();
    }
    persistenceMappers.add(mapper);
    return this;
  }

  /**
   * Attach additional types to the configuration.
   */
  public Configuration addTypeSource(TypeSource source) {
    allTypes.addAll(source.getTypes());
    return this;
  }

  public Set<Class<?>> getAllTypes() {
    return Collections.unmodifiableSet(allTypes);
  }

  /**
   * Returns an immutable view of the {@link EntityResolver} instances that were passed to
   * {@link #addEntityResolver(EntityResolver)}.
   */
  public List<EntityResolver> getEntityResolvers() {
    return Collections.unmodifiableList(resolvers);
  }

  /**
   * Returns an immutable view of the {@link CodexMapper} instances that were passed to
   * {@link #addCodexMapper(CodexMapper)}.
   */
  public List<CodexMapper> getExtraMappers() {
    return Collections.unmodifiableList(extraMappers);
  }

  /**
   * Returns an immutable view of the {@link PersistenceMapper} instances that were passed to
   * {@link #addPersistenceMapper(PersistenceMapper)}.
   */
  public List<PersistenceMapper> getPersistenceMappers() {
    return persistenceMappers == null ? Collections.<PersistenceMapper> emptyList() :
        Collections.unmodifiableList(persistenceMappers);
  }

  public PrincipalMapper getPrincipalMapper() {
    return principalMapper;
  }

  /**
   * Returns the {@link RoleMapper} that maps role names to interface types.
   */
  public RoleMapper getRoleMapper() {
    return roleMapper;
  }

  /**
   * By default, Unpacker will refuse to process payloads that contain unresolvable types. This
   * behavior is typically desired for server operation, where an unresolvable type would typically
   * indicate a misbehaving client. However, this behavior is not appropriate for clients, because
   * it would force all clients to upgrade their object schema in lock-step with the server.
   */
  public boolean isIgnoreUnresolvableTypes() {
    return ignoreUnresolvableTypes;
  }

  /**
   * If {@code true}, payloads will be formatted to be human-readable.
   */
  public boolean isPrettyPrint() {
    return prettyPrint;
  }

  /**
   * If {@code true}, payloads will be written to {@link System#out}.
   */
  public boolean isVerbose() {
    return verbose;
  }

  public void setRoleMapper(RoleMapper roleMapper) {
    this.roleMapper = roleMapper;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  public Configuration withIgnoreUnresolvableTypes(boolean ignore) {
    this.ignoreUnresolvableTypes = ignore;
    return this;
  }

  public Configuration withPrettyPrint(boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
    return this;
  }

  public Configuration withPrincipalMapper(PrincipalMapper mapper) {
    this.principalMapper = mapper;
    return this;
  }

  public Configuration withRoleMapper(RoleMapper roleMapper) {
    setRoleMapper(roleMapper);
    return this;
  }

  public Configuration withVerbose(boolean verbose) {
    setVerbose(verbose);
    return this;
  }
}
