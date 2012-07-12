package com.getperka.flatpack.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

public class Manager extends Person {
  private List<Employee> employees = new ArrayList<Employee>();

  Manager() {}

  @OneToMany(mappedBy = "manager")
  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
}
