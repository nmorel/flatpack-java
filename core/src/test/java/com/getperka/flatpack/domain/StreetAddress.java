package com.getperka.flatpack.domain;

import com.getperka.flatpack.BaseHasUuid;

public class StreetAddress extends BaseHasUuid {
  private String street;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }
}
