package com.getperka.flatpack.codex;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;

import com.getperka.flatpack.FlatPackTest;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.codexes.ArrayCodex;
import com.getperka.flatpack.codexes.ListCodex;
import com.getperka.flatpack.codexes.SetCodex;
import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.Person;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.inject.TypeLiteral;

/**
 * Tests serializing collections of things.
 */
public class CollectionCodexTest extends FlatPackTest {
  @Inject
  private TypeLiteral<ArrayCodex<Person>> arrayPerson;

  @Inject
  private TypeLiteral<ArrayCodex<String>> arrayString;

  @Inject
  private TypeLiteral<ListCodex<Person>> listPerson;

  @Inject
  private TypeLiteral<ListCodex<String>> listString;

  @Inject
  private TypeLiteral<SetCodex<String>> setString;

  @Inject
  private Employee employee;

  @Test
  public void testArray() {
    String[] in = { "Hello", " ", "", null, "World!" };
    String[] out = testCodex(arrayString, in);
    assertArrayEquals(in, out);

    Set<HasUuid> scanned = FlatPackCollections.setForIteration();
    Employee[] in2 = { employee, null, employee };
    Person[] out2 = testCodex(arrayPerson, in2, scanned);

    assertEquals(Collections.singleton(employee), scanned);

    /*
     * Because we're testing without a full flatpack structure, all we can expect is that a HasUuid
     * is created with the same UUID. The concrete type would normally be specified in the data
     * section, however it is missing, so we expect the configured type of the codex instead.
     */
    Person p = out2[0];
    assertNotNull(p);
    assertEquals(Person.class, p.getClass());
    assertEquals(employee.getUuid(), p.getUuid());
  }

  @Test
  public void testList() {
    List<String> in = Arrays.asList("Hello", " ", "", null, "World!");
    List<String> out = testCodex(listString, in);
    assertEquals(in, out);

    Set<HasUuid> scanned = FlatPackCollections.setForIteration();
    List<Person> in2 = Arrays.<Person> asList(employee, null, employee);
    List<Person> out2 = testCodex(listPerson, in2, scanned);

    assertEquals(Collections.singleton(employee), scanned);

    /*
     * Because we're testing without a full flatpack structure, all we can expect is that a HasUuid
     * is created with the same UUID. The concrete type would normally be specified in the data
     * section, however it is missing, so we expect the configured type of the codex instead.
     */
    Person p = out2.get(0);
    assertNotNull(p);
    assertEquals(Person.class, p.getClass());
    assertEquals(employee.getUuid(), p.getUuid());

  }

  @Test
  public void testNull() {
    assertNull(testCodex(arrayString, null));
    assertNull(testCodex(listString, null));
    assertNull(testCodex(setString, null));
  }

  @Test
  public void testSet() {
    Set<String> in = new LinkedHashSet<String>(Arrays.asList("Hello", " ", "", null, "World!"));
    Set<String> out = testCodex(setString, in);

    assertEquals(in, out);
  }
}
