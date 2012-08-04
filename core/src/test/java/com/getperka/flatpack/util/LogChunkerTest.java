package com.getperka.flatpack.util;

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

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Test;
import org.slf4j.Logger;

public class LogChunkerTest {

  @Test
  public void test() {
    test("fo", "fo");
    test("foo\na\nb", "foo", "a\nb");
    test("foobarbaz", "foo", "bar", "baz");
    test("fo\nobarbaz", "fo", "oba", "rba", "z");
    test("fo\nobarbaz\n", "fo", "oba", "rba", "z\n");
  }

  private Logger expectLogs(String... messages) {
    Logger logger = createStrictMock(Logger.class);
    expect(logger.isInfoEnabled()).andReturn(true).anyTimes();
    for (String message : messages) {
      logger.info(message);
      expectLastCall();
    }
    replay(logger);
    return logger;
  }

  private void test(String message, String... expected) {
    Logger logger = expectLogs(expected);
    LogChunker c = new LogChunker(logger, 3);
    c.info(message);
    verify(logger);
  }
}
