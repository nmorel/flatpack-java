package com.getperka.flatpack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;

import org.junit.Test;

import com.getperka.flatpack.domain.Employee;
import com.getperka.flatpack.domain.Manager;
import com.getperka.flatpack.domain.TestTypeSource;

/**
 * Test full encoding of {@link Packer} and {@link Unpacker}.
 */
public class PackTest extends FlatPackTest {

  @Test
  public void test() throws IOException {
    Employee employee = makeEmployee();
    Employee employee2 = deepPack(Employee.class, employee);

    check(employee, employee2);
  }

  @Test
  public void testImpliedProperties() throws IOException {
    Manager manager = makeManager();
    Employee employeeA = makeEmployee();
    Employee employeeB = makeEmployee();

    // A -> manager -> B
    employeeA.setManager(manager);
    manager.getEmployees().add(employeeB);

    Employee employeeA2 = deepPack(Employee.class, employeeA);
    assertEquals(2, employeeA2.getManager().getEmployees().size());
    for (Employee toTest : employeeA2.getManager().getEmployees()) {
      if (employeeA.equals(toTest)) {
        check(employeeA, toTest);
      } else if (employeeB.equals(toTest)) {
        check(employeeB, toTest);
      } else {
        fail("Unmatched employee");
      }
    }
  }

  protected <T> T deepPack(Type type, T value) throws IOException {
    @SuppressWarnings("unchecked")
    FlatPackEntity<T> entity = (FlatPackEntity<T>) FlatPackEntity.create(type, value, null)
        .withTraversalMode(TraversalMode.DEEP);

    StringWriter out = new StringWriter();
    flatpack.getPacker().pack(entity, out);
    System.out.println(out.toString());

    FlatPackEntity<T> entity2 = flatpack.getUnpacker().unpack(
        type, new StringReader(out.toString()), null);
    return entity2.getValue();
  }

  @Override
  protected Configuration getConfiguration() {
    return super.getConfiguration().addTypeSource(new TestTypeSource());
  }
}
