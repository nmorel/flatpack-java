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
package com.getperka.flatpack.inject;

import java.security.Principal;
import java.util.Map;

import org.joda.time.DateTime;

import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.TraversalMode;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * Provides injection for objects whose lifespans are bounded by a pack or unpack operation.
 */
public class PackScope implements Scope {

  private static class DummyProvider implements Provider<Object> {
    private static final DummyProvider INSTANCE = new DummyProvider();

    @Override
    public Object get() {
      throw new IllegalStateException("Not in a PackScope");
    }
  }

  /**
   * Returns a dummy provider that always throws an exception. This provider is injected as the
   * default provider for bindings that can only be satisfied inside of the pack scope.
   */
  public static <T> Provider<T> provider() {
    return cast(DummyProvider.INSTANCE);
  }

  /**
   * Utility method for an unchecked cast.
   */
  @SuppressWarnings("unchecked")
  private static <T> Provider<T> cast(Provider<?> provider) {
    return (Provider<T>) provider;
  }

  /**
   * Retains the thread-local data.
   */
  private final ThreadLocal<Map<Key<?>, Object>> allData = new ThreadLocal<Map<Key<?>, Object>>();

  PackScope() {}

  public PackScope enter() {
    if (allData.get() != null) {
      throw new IllegalStateException("Already in a PackScope");
    }
    allData.set(FlatPackCollections.<Key<?>, Object> mapForLookup());
    return this;
  }

  public void exit() {
    allData.remove();
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
    Key<JsonWriter> key = Key.get(JsonWriter.class);
    if (writer == null) {
      allData.get().remove(key);
    } else {
      allData.get().put(key, writer);
    }
    return this;
  }

  public PackScope withLastModifiedTime(DateTime lastModified) {
    Key<DateTime> key = Key.get(DateTime.class, LastModifiedTime.class);
    if (lastModified == null) {
      lastModified = new DateTime(0);
    }
    allData.get().put(key, lastModified);
    return this;
  }

  public PackScope withPrincipal(Principal principal) {
    Key<Principal> key = Key.get(Principal.class);
    if (principal == null) {
      allData.get().remove(key);
    } else {
      allData.get().put(key, principal);
    }
    return this;
  }

  public PackScope withTraversalMode(TraversalMode mode) {
    Key<TraversalMode> key = Key.get(TraversalMode.class);
    if (mode == null) {
      allData.get().remove(key);
    } else {
      allData.get().put(key, mode);
    }
    return this;
  }
}
