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

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple Reader implementation that spies on the underlying Reader's contents.
 */
class VerboseReader extends Reader {
  private static final Logger logger = LoggerFactory.getLogger(VerboseReader.class);
  private final StringBuilder builder = new StringBuilder();
  private final Reader source;

  public VerboseReader(Reader source) {
    this.source = source;
  }

  @Override
  public void close() throws IOException {
    logger.debug("Incoming flatpack payload:\n{}", builder.toString());
    source.close();
  }

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    int toReturn = source.read(cbuf, off, len);
    builder.append(cbuf, off, toReturn);
    return toReturn;
  }
}
