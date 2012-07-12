package com.getperka.flatpack.domain;

import javax.inject.Inject;
import javax.persistence.Embedded;

import com.getperka.flatpack.BaseHasUuid;

public class Person extends BaseHasUuid {
  @Inject
  private StreetAddress address;
  private String name;

  Person() {}

  @Embedded
  public StreetAddress getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public void setAddress(StreetAddress address) {
    this.address = address;
  }

  public void setName(String name) {
    this.name = name;
  }
}
