/*
 * #%L
 * FlatPack serialization code
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.UUID;

import org.junit.Test;

public class BaseHasUuidTest {
  static class One extends BaseHasUuid {}

  static class OneSub extends One {}

  static class Two extends BaseHasUuid {}

  @Test
  public void testEquality() {
    UUID uuid = UUID.randomUUID();

    One one = new One();
    One one2 = new One();
    One oneSub = new OneSub();
    Two two = new Two();

    checkNotEquals(one, one2);
    checkNotEquals(one, oneSub);
    checkNotEquals(one, two);

    one.setUuid(uuid);
    one2.setUuid(uuid);
    oneSub.setUuid(uuid);
    two.setUuid(uuid);

    checkEquals(one, one2);
    checkEquals(one, oneSub);
    checkNotEquals(one, two);
  }

  private void checkEquals(Object a, Object b) {
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(a, b);
    assertEquals(b, a);
  }

  private void checkNotEquals(Object a, Object b) {
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
  }
}
