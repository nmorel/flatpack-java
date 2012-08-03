/*
 * #%L
 * FlatPack Client
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
package com.getperka.flatpack.client.impl;

import java.net.HttpURLConnection;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.client.Api;
import com.getperka.flatpack.util.IoObserver;
import com.getperka.flatpack.util.LogChunker;

/**
 * A base class for accessing FlatPack API servers.
 */
public abstract class ApiBase implements Api {
  private static final String CHUNK_SIZE_PROPERTY = "flatpack.log.chunk.size";

  private final FlatPack flatpack;
  private URI serverBase;
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private IoObserver ioObserver = new IoObserver.Null();

  protected ApiBase(FlatPack flatpack) {
    this.flatpack = flatpack;
  }

  @Override
  public URI getServerBase() {
    return serverBase;
  }

  @Override
  public void setServerBase(URI serverBase) {
    this.serverBase = serverBase;
  }

  @Override
  public void setVerbose(boolean verbose) {
    if (verbose) {
      LogChunker chunker = new LogChunker(logger,
          Integer.getInteger(CHUNK_SIZE_PROPERTY, Integer.MAX_VALUE));
      ioObserver = new IoObserver.Verbose(chunker);
    } else {
      ioObserver = new IoObserver.Null();
    }
  }

  /**
   * Hook point for altering a connection before it is sent.
   */
  protected HttpURLConnection filter(HttpURLConnection conn) {
    return conn;
  }

  protected FlatPack getFlatPack() {
    return flatpack;
  }

  protected IoObserver getIoObserver() {
    return ioObserver;
  }

  protected Logger getLogger() {
    return logger;
  }
}
