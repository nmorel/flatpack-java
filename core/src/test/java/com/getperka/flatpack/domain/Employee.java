package com.getperka.flatpack.domain;

import javax.persistence.ManyToOne;

import com.getperka.flatpack.PostUnpack;
import com.getperka.flatpack.PreUnpack;
import com.google.gson.JsonObject;

public class Employee extends Person {
  private int employeeNumber;
  public boolean employeePreUnpack;
  public boolean employeePre1Unpack;
  public boolean employeePostUnpack;
  private Manager manager;

  Employee() {}

  public int getEmployeeNumber() {
    return employeeNumber;
  }

  @ManyToOne
  public Manager getManager() {
    return manager;
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  @PostUnpack
  void employeePostUnpack() {
    employeePostUnpack = true;
  }

  @PreUnpack
  void employeePreUnpack() {
    employeePreUnpack = true;
  }

  @PreUnpack
  void employeePreUnpack(JsonObject obj) {
    employeePre1Unpack = true;
  }
}
