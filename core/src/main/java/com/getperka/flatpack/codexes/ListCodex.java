package com.getperka.flatpack.codexes;

import java.util.List;

import com.getperka.flatpack.util.FlatPackCollections;

public class ListCodex<V> extends CollectionCodex<List<V>, V> {

  ListCodex() {}

  @Override
  protected List<V> newCollection() {
    return FlatPackCollections.listForAny();
  }
}
