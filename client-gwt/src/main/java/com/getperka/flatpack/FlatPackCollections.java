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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A class with factory methods that vend different types of collections based on intended use.
 * These methods exist to help ensure that code will operate with predictable iteration order where
 * iteration is required.
 */
public class FlatPackCollections {

  /**
   * Returns the default List implementation to use.
   */
  public static <T> List<T> listForAny() {
    return new ArrayList<T>();
  }

  /**
   * Returns a Map with stable iteration order.
   */
  public static <K, V> Map<K, V> mapForIteration() {
    return new LinkedHashMap<K, V>();
  }

  /**
   * Returns a Map whose {@link Iterable} methods throw {@link UnsupportedOperationException}.
   */
  @SuppressWarnings("serial")
  public static <K, V> Map<K, V> mapForLookup() {
    return new HashMap<K, V>() {

      @Override
      public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
      }

      @Override
      public Set<K> keySet() {
        throw new UnsupportedOperationException();
      }

      @Override
      public Collection<V> values() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * Returns a Set guaranteed to have stable iteration order.
   */
  public static <T> Set<T> setForIteration() {
    return new LinkedHashSet<T>();
  }

  /**
   * Returns a Set that cannot be iterated over.
   */
  @SuppressWarnings("serial")
  public static <T> Set<T> setForLookup() {
    return new HashSet<T>() {
      @Override
      public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public static <K extends Comparable<K>, V> SortedMap<K, V> sortedMapForIteration() {
    return new TreeMap<K, V>();
  }

  private FlatPackCollections() {}
}
