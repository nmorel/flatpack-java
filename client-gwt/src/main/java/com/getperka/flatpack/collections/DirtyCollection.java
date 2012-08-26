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

/**
 * A delegating {@link Collection} implementation that will call {@link DirtyFlag#set()} when it is
 * mutated.
 */
public class DirtyCollection<T> implements Collection<T> {
  private final Collection<T> delegate;
  private final DirtyFlag dirty;

  public DirtyCollection(Collection<T> delegate, DirtyFlag dirty) {
    this.delegate = delegate;
    this.dirty = dirty;
  }

  @Override
  public boolean add(T e) {
    dirty.set();
    return delegate.add(e);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    dirty.set();
    return delegate.addAll(c);
  }

  @Override
  public void clear() {
    dirty.set();
    delegate.clear();
  }

  @Override
  public boolean contains(Object o) {
    return delegate.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return delegate.containsAll(c);
  }

  @Override
  public boolean equals(Object o) {
    return this == o || delegate.equals(o);
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
  public Iterator<T> iterator() {
    return new DirtyIterator<T>(delegate.iterator(), dirty);
  }

  @Override
  public boolean remove(Object o) {
    dirty.set();
    return delegate.remove(o);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    dirty.set();
    return delegate.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    dirty.set();
    return delegate.retainAll(c);
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public Object[] toArray() {
    return delegate.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return delegate.toArray(a);
  }
}
