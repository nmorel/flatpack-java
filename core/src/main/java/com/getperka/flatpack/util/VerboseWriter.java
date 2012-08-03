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

import java.io.IOException;
import java.io.Writer;

/**
 * A simple Writer implementation that spies on its input.
 */
class VerboseWriter extends Writer {

  private final StringBuilder builder = new StringBuilder();
  private final LogChunker chunker;
  private final Writer sink;

  public VerboseWriter(LogChunker chunker, Writer sink) {
    this.chunker = chunker;
    this.sink = sink;
  }

  @Override
  public void close() throws IOException {
    chunker.debug("Outgoing payload:\n" + builder);
    sink.close();
  }

  @Override
  public void flush() throws IOException {
    sink.flush();
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    builder.append(cbuf, off, len);
    sink.write(cbuf, off, len);
  }

}
