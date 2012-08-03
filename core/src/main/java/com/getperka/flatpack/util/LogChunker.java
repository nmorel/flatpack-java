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

import javax.inject.Inject;

import org.slf4j.Logger;

import com.getperka.flatpack.inject.FlatPackLogger;
import com.getperka.flatpack.inject.VerboseLogChunkSize;

public class LogChunker {
  private final int chunkSize;
  private final Logger logger;

  @Inject
  public LogChunker(@FlatPackLogger Logger logger, @VerboseLogChunkSize int chunkSize) {
    this.logger = logger;
    this.chunkSize = chunkSize;
  }

  public void debug(CharSequence message) {
    if (!logger.isDebugEnabled()) {
      return;
    }

    if (message.length() <= chunkSize) {
      logger.debug(message.toString());
      return;
    }

    StringBuilder chunk = new StringBuilder();
    StringBuilder line = new StringBuilder();
    for (int i = 0, j = message.length(); i <= j; i++) {
      char c = i < j ? message.charAt(i) : '\n';
      switch (c) {
        case '\n':
        case '\r':
          if (chunk.length() + line.length() >= chunkSize) {
            if (chunk.length() > 0) {
              logger.debug(chunk.toString());
              chunk.setLength(0);
            }

            while (line.length() >= chunkSize) {
              logger.debug(line.substring(0, chunkSize));
              line.delete(0, chunkSize);
            }

            chunk.append(line);
            line.setLength(0);
          } else {
            if (chunk.length() > 0) {
              chunk.append('\n');
            }
            chunk.append(line);
            line.setLength(0);
          }
          break;
        default:
          line.append(c);
      }
    }
    if (chunk.length() > 0) {
      logger.debug(chunk.toString());
    }
  }
}
