package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Principal;
import java.util.Collection;

import org.joda.time.DateTime;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.RoleMapper;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.PrincipalMapper;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.inject.PackScope.LastModifiedTime;
import com.getperka.flatpack.inject.PackScope.PackPrincipal;
import com.google.gson.stream.JsonWriter;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Providers;

public class FlatPackModule extends AbstractModule {

  /**
   * A binding annotation for a {@code Collection<Class<?>>} with all {@link HasUuid} types that
   * should be supported.
   */
  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface AllTypes {}

  /**
   * A binding annotation for a {@code Collection<CodexMapper>}.
   */
  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface ExtraMappers {}

  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface IgnoreUnresolvableTypes {}

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Nullable {};

  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface PrettyPrint {}

  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Resolvers {}

  @BindingAnnotation
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Verbose {}

  private final Configuration configuration;

  public FlatPackModule(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {
    bind(RoleMapper.class).toProvider(Providers.of(configuration.getRoleMapper()));
    bind(PrincipalMapper.class).toProvider(Providers.of(configuration.getPrincipalMapper()));

    bind(TypeContext.class).in(Scopes.SINGLETON);

    bind(new TypeLiteral<Collection<Class<?>>>() {})
        .annotatedWith(AllTypes.class)
        .toInstance(configuration.getAllTypes());

    bind(new TypeLiteral<Collection<CodexMapper>>() {})
        .annotatedWith(ExtraMappers.class)
        .toInstance(configuration.getExtraMappers());

    bind(new TypeLiteral<Collection<EntityResolver>>() {})
        .annotatedWith(Resolvers.class)
        .toInstance(configuration.getEntityResolvers());

    PackScope packScope = new PackScope();
    bindScope(PackScoped.class, packScope);
    bind(PackScope.class).toInstance(packScope);
    bind(DateTime.class)
        .annotatedWith(LastModifiedTime.class)
        .toProvider(packScope.<DateTime> provider())
        .in(packScope);

    bind(Principal.class)
        .annotatedWith(PackPrincipal.class)
        .toProvider(packScope.<Principal> provider())
        .in(packScope);

    bind(TraversalMode.class)
        .toProvider(packScope.<TraversalMode> provider())
        .in(packScope);

    bind(JsonWriter.class)
        .toProvider(packScope.<JsonWriter> provider())
        .in(packScope);

    bindConstant().annotatedWith(IgnoreUnresolvableTypes.class)
        .to(configuration.isIgnoreUnresolvableTypes());
    bindConstant().annotatedWith(PrettyPrint.class).to(configuration.isPrettyPrint());
    bindConstant().annotatedWith(Verbose.class).to(configuration.isVerbose());
  }
}
