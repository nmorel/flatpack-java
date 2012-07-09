package com.getperka.flatpack.inject;

import java.security.Principal;
import java.util.Collection;

import org.joda.time.DateTime;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.RoleMapper;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.codexes.DefaultCodexMapper;
import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.PrincipalMapper;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.stream.JsonWriter;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Providers;

public class FlatPackModule extends AbstractModule {
  private final Configuration configuration;

  public FlatPackModule(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {
    // Bind TypeContext in singleton, because we want referential integrity
    bind(TypeContext.class).in(Scopes.SINGLETON);

    // Bind simple constants
    bindConstant()
        .annotatedWith(IgnoreUnresolvableTypes.class)
        .to(configuration.isIgnoreUnresolvableTypes());
    bindConstant()
        .annotatedWith(PrettyPrint.class)
        .to(configuration.isPrettyPrint());
    bindConstant()
        .annotatedWith(Verbose.class)
        .to(configuration.isVerbose());

    // Provide all class types
    bind(new TypeLiteral<Collection<Class<?>>>() {})
        .annotatedWith(AllTypes.class)
        .toInstance(configuration.getAllTypes());

    bindPackScope();
    bindUserTypes();
  }

  /**
   * Set up bindings for {@link PackScoped} types (e.g. SerializationContext).
   */
  private void bindPackScope() {
    PackScope packScope = new PackScope();
    // Make the instance of the PackScope available
    bind(PackScope.class).toInstance(packScope);

    // All @PackScoped object should be constructed through the scope
    bindScope(PackScoped.class, packScope);

    // Always provide a binding for Principal
    bind(Principal.class)
        .to(NullPrincipal.class)
        .in(packScope);

    // Additional, scope-specific bindings
    bind(DateTime.class)
        .annotatedWith(LastModifiedTime.class)
        .toProvider(Providers.of(new DateTime(0)))
        .in(packScope);

    bind(TraversalMode.class)
        .toProvider(PackScope.<TraversalMode> provider())
        .in(packScope);

    bind(JsonWriter.class)
        .toProvider(PackScope.<JsonWriter> provider())
        .in(packScope);
  }

  /**
   * Attach bindings for user-injectable behaviors or default instances.
   */
  private void bindUserTypes() {
    // CodexMapper
    if (configuration.getExtraMappers().isEmpty()) {
      bind(CodexMapper.class)
          .to(DefaultCodexMapper.class);
    } else {
      bind(CodexMapper.class)
          .toInstance(new CompositeCodexMapper(configuration.getExtraMappers()));
    }

    // EntityResolver
    if (configuration.getEntityResolvers().size() == 1) {
      bind(EntityResolver.class)
          .toInstance(configuration.getEntityResolvers().get(0));
    } else {
      bind(EntityResolver.class)
          .toInstance(new CompositeEntityResolver(configuration.getEntityResolvers()));
    }

    // PrincipalMapper
    if (configuration.getPrincipalMapper() == null) {
      bind(PrincipalMapper.class).to(PermissivePrincipalMapper.class);
    } else {
      bind(PrincipalMapper.class).toInstance(configuration.getPrincipalMapper());
    }

    // RoleMapper
    if (configuration.getRoleMapper() == null) {
      bind(RoleMapper.class).to(NullRoleMapper.class);
      bindConstant()
          .annotatedWith(DisableRoleChecks.class)
          .to(true);
    } else {
      bind(RoleMapper.class).toInstance(configuration.getRoleMapper());
      bindConstant()
          .annotatedWith(DisableRoleChecks.class)
          .to(false);
    }
  }
}