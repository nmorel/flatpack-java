package com.getperka.flatpack.codexes;

import java.util.Set;

import com.getperka.flatpack.util.FlatPackCollections;

public class SetCodex<V> extends CollectionCodex<Set<V>, V> {
  SetCodex() {}

  @Override
  protected Set<V> newCollection() {
    return FlatPackCollections.setForIteration();
  }
}
