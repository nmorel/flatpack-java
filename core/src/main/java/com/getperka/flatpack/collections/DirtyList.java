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
import java.util.List;
import java.util.ListIterator;

/**
 * A delegating {@link List} implementation that will call {@link DirtyFlag#set()} when it is
 * mutated.
 */
public class DirtyList<T> extends DirtyCollection<T> implements List<T> {

  private final List<T> delegate;
  private final DirtyFlag dirty;

  public DirtyList(List<T> delegate, DirtyFlag dirty) {
    super(delegate, dirty);
    this.delegate = delegate;
    this.dirty = dirty;
  }

  @Override
  public void add(int index, T element) {
    delegate.add(index, element);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    dirty.set();
    return delegate.addAll(index, c);
  }

  @Override
  public T get(int index) {
    return delegate.get(index);
  }

  @Override
  public int indexOf(Object o) {
    return delegate.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return delegate.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return new DirtyIterator<T>(delegate.listIterator(), dirty);
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return new DirtyIterator<T>(delegate.listIterator(index), dirty);
  }

  @Override
  public T remove(int index) {
    dirty.set();
    return delegate.remove(index);
  }

  @Override
  public T set(int index, T element) {
    dirty.set();
    return delegate.set(index, element);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return new DirtyList<T>(delegate.subList(fromIndex, toIndex), dirty);
  }
}
