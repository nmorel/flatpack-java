package com.getperka.flatpack.util;

import org.slf4j.Logger;

public class ChunkedLogger {
  private static final String CHUNK_SIZE_PROPERTY = "flatpack.log.chunk.size";
  private final int chunkSize;
  private final Logger logger;

  public ChunkedLogger(Logger logger) {
    this.logger = logger;
    String chunkSizeString = System.getProperty(CHUNK_SIZE_PROPERTY);
    chunkSize = chunkSizeString != null ? Integer.parseInt(chunkSizeString) : Integer.MAX_VALUE;
  }

  public void debug(StringBuilder builder) {
    if (builder.length() > chunkSize) {
      int chunkCount = builder.length() / chunkSize;
      for (int i = 0; i < chunkCount + 1; i++) {
        int max = chunkSize * (i + 1);
        if (max >= builder.length()) {
          logger.debug(builder.substring(chunkSize * i));
        } else {
          logger.debug(chunkCount + ": "
            + builder.substring(chunkSize * i, max));
        }
      }
    }
    else {
      logger.debug(builder.toString());
    }
  }
}
