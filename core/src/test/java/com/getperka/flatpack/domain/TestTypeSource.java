package com.getperka.flatpack.domain;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.getperka.flatpack.TypeSource;

public class TestTypeSource implements TypeSource {
  private final Set<Class<?>> types;

  public TestTypeSource() {
    types = new LinkedHashSet<Class<?>>(Arrays.<Class<?>> asList(
        Employee.class, Manager.class, Person.class));
  }

  @Override
  public Set<Class<?>> getTypes() {
    return types;
  }

}
