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

import java.util.Iterator;
import java.util.ListIterator;

/**
 * A delegating {@link Iterator} and {@link ListIterator} implementation that will call
 * {@link DirtyFlag#set()} when it is mutated.
 */
class DirtyIterator<T> implements Iterator<T>, ListIterator<T> {
  private final DirtyFlag dirty;
  private final Iterator<T> it;
  private final ListIterator<T> lit;

  public DirtyIterator(Iterator<T> it, DirtyFlag dirty) {
    this.dirty = dirty;
    this.it = it;
    this.lit = null;
  }

  public DirtyIterator(ListIterator<T> it, DirtyFlag dirty) {
    this.dirty = dirty;
    this.it = it;
    this.lit = it;
  }

  @Override
  public void add(T arg0) {
    dirty.set();
    lit.add(arg0);
  }

  @Override
  public boolean hasNext() {
    return it.hasNext();
  }

  @Override
  public boolean hasPrevious() {
    return lit.hasPrevious();
  }

  @Override
  public T next() {
    return it.next();
  }

  @Override
  public int nextIndex() {
    return lit.nextIndex();
  }

  @Override
  public T previous() {
    return lit.previous();
  }

  @Override
  public int previousIndex() {
    return lit.previousIndex();
  }

  @Override
  public void remove() {
    dirty.set();
    it.remove();
  }

  @Override
  public void set(T arg0) {
    dirty.set();
    lit.set(arg0);
  }
}
