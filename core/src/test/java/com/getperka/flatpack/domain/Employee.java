package com.getperka.flatpack.domain;

public class Employee extends Person {
  private int employeeNumber;

  Employee() {}

  public int getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }
}
