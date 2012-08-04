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

import java.io.Reader;
import java.io.Writer;

import javax.inject.Inject;

/**
 * Provides decorators for readers and writers.
 */
public interface IoObserver {

  /**
   * A no-op implementation.
   */
  public static class Null implements IoObserver {
    @Override
    public Reader observe(Reader reader) {
      return reader;
    }

    @Override
    public Writer observe(Writer writer) {
      return writer;
    }
  }

  /**
   * Returns wrappers that will spy on the content that passes through them and record it at
   * {@code debug} level.
   */
  public static class Verbose implements IoObserver {
    private final LogChunker chunker;

    @Inject
    public Verbose(LogChunker chunker) {
      this.chunker = chunker;
    }

    @Override
    public Reader observe(Reader source) {
      return new VerboseReader(chunker, source);
    }

    @Override
    public Writer observe(Writer sink) {
      return new VerboseWriter(chunker, sink);
    }
  }

  Reader observe(Reader reader);

  Writer observe(Writer writer);
}
