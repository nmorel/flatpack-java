package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Principal;
import java.util.Map;

import org.joda.time.DateTime;

import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.stream.JsonWriter;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.util.Providers;

public class PackScope implements Scope {

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @BindingAnnotation
  public @interface LastModifiedTime {}

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @BindingAnnotation
  public @interface PackPrincipal {}

  private final ThreadLocal<Map<Key<?>, Object>> allData = new ThreadLocal<Map<Key<?>, Object>>();

  PackScope() {}

  public PackScope enter() {
    allData.set(FlatPackCollections.<Key<?>, Object> mapForLookup());
    return this;
  }

  public void exit() {
    allData.remove();
  }

  public <T> Provider<T> provider() {
    return cast(new Provider<Object>() {
      @Override
      public Object get() {
        throw new IllegalStateException("Not in a PackScope");
      }
    });
  }

  @Override
  public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
    return cast(new Provider<Object>() {
      @Override
      public Object get() {
        Map<Key<?>, Object> map = allData.get();
        if (map == null) {
          return unscoped.get();
        }
        if (!map.containsKey(key)) {
          return unscoped.get();
        }
        return map.get(key);
      }
    });
  }

  public PackScope withEntity(FlatPackEntity<?> entity) {
    withLastModifiedTime(entity.getLastModifiedTime());
    withPrincipal(entity.getPrincipal());
    withTraversalMode(entity.getTraversalMode());
    return this;
  }

  public PackScope withJsonWriter(JsonWriter writer) {
    allData.get().put(Key.get(JsonWriter.class), writer);
    return this;
  }

  public PackScope withLastModifiedTime(DateTime lastModified) {
    allData.get().put(Key.get(DateTime.class, LastModifiedTime.class), lastModified);
    return this;
  }

  public PackScope withPrincipal(Principal principal) {
    allData.get().put(Key.get(Principal.class, PackPrincipal.class), principal);
    return this;
  }

  public PackScope withTraversalMode(TraversalMode mode) {
    allData.get().put(Key.get(TraversalMode.class), mode);
    return this;
  }

  @SuppressWarnings("unchecked")
  private <T> Provider<T> cast(Provider<?> provider) {
    return (Provider<T>) provider;
  }

  @SuppressWarnings("unchecked")
  private <T> Provider<T> provide(Object value) {
    return (Provider<T>) Providers.of(value);
  }

}
