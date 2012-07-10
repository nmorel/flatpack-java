package com.getperka.flatpack.domain;

import com.getperka.flatpack.BaseHasUuid;

public class Person extends BaseHasUuid {
  private String name;

  Person() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
