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
package com.getperka.flatpack.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A delegating {@link Map} implementation that will call {@link DirtyFlag#set()} when it is
 * mutated.
 */
public class DirtyMap<K, V> implements Map<K, V> {
  /**
   * Intercepts calls to {@link #setValue(Object)}.
   */
  static class DirtyEntry<K, V> implements Map.Entry<K, V> {
    private final Map.Entry<K, V> delegate;

    private final DirtyFlag dirty;

    public DirtyEntry(Map.Entry<K, V> delegate, DirtyFlag dirty) {
      this.delegate = delegate;
      this.dirty = dirty;
    }

    @Override
    public boolean equals(Object o) {
      return this == o || delegate.equals(o);
    }

    @Override
    public K getKey() {
      return delegate.getKey();
    }

    @Override
    public V getValue() {
      return delegate.getValue();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public V setValue(V value) {
      dirty.set();
      return delegate.setValue(value);
    }
  }

  /**
   * Wraps any entries returned from the iterator as a {@link DirtyEntry}.
   */
  static class DirtyEntrySet<K, V> extends DirtySet<Map.Entry<K, V>> {
    private final DirtyFlag dirty;

    public DirtyEntrySet(Set<java.util.Map.Entry<K, V>> delegate, DirtyFlag dirty) {
      super(delegate, dirty);
      this.dirty = dirty;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
      final Iterator<Map.Entry<K, V>> it = super.iterator();
      return new Iterator<Map.Entry<K, V>>() {
        @Override
        public boolean hasNext() {
          return it.hasNext();
        }

        @Override
        public java.util.Map.Entry<K, V> next() {
          return new DirtyEntry<K, V>(it.next(), dirty);
        }

        @Override
        public void remove() {
          it.remove();
        }
      };
    }

  }

  private final Map<K, V> delegate;

  private final DirtyFlag dirty;

  public DirtyMap(Map<K, V> delegate, DirtyFlag dirty) {
    this.delegate = delegate;
    this.dirty = dirty;
  }

  @Override
  public void clear() {
    dirty.set();
    delegate.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return delegate.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return delegate.containsValue(value);
  }

  @Override
  public Set<java.util.Map.Entry<K, V>> entrySet() {
    return new DirtyEntrySet<K, V>(delegate.entrySet(), dirty);
  }

  @Override
  public boolean equals(Object o) {
    return this == o || delegate.equals(o);
  }

  @Override
  public V get(Object key) {
    return delegate.get(key);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public Set<K> keySet() {
    return new DirtySet<K>(delegate.keySet(), dirty);
  }

  @Override
  public V put(K key, V value) {
    dirty.set();
    return delegate.put(key, value);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    dirty.set();
    delegate.putAll(m);
  }

  @Override
  public V remove(Object key) {
    dirty.set();
    return delegate.remove(key);
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public Collection<V> values() {
    return delegate.values();
  }
}
