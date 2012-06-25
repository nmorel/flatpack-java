/*
 * #%L
 * FlatPack Automatic Source Tool
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
package com.getperka.flatpack.fast;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getperka.cli.flags.Command;
import com.getperka.cli.flags.DefaultCommand;
import com.getperka.cli.flags.Flag;
import com.getperka.cli.flags.ShellBase;
import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.Unpacker;
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.search.SearchTypeSource;

/**
 * Main tool entry point for the FlatPack Automatic Source Toolkit.
 */
public class FastTool extends ShellBase {
  private static final String DEFAULT_URI = "https://getperka.com/api/2/describe";
  private static final Logger logger = LoggerFactory.getLogger(FastTool.class);

  public static void main(String[] args) {
    System.exit(new FastTool().exec(args) ? 0 : 1);
  }

  @Command(help = "Generate a client library")
  @DefaultCommand
  boolean generate(
      @Flag(tag = "in", help = "The URI to load the ApiDescription from",
          defaultValue = DEFAULT_URI) URI source,
      @Flag(tag = "dialect", help = "The source dialect to use", defaultValue = "java") String dialect,
      @Flag(tag = "out", help = "The directory to generate source into", defaultValue = ".") File out)
      throws IOException {

    Unpacker unpacker = FlatPack.create(new Configuration()
        .addTypeSource(new SearchTypeSource("com.getperka.flatpack")))
        .getUnpacker();

    logger.info("Retrieving {}", source);
    Reader reader = new InputStreamReader(source.toURL().openStream(), Charset.forName("UTF8"));

    ApiDescription api = unpacker.<ApiDescription> unpack(ApiDescription.class, reader, null)
        .getValue();
    logger.info("Retrieved API description with {} entities and {} endpoints", api.getEntities()
        .size(), api.getEndpoints().size());

    ServiceLoader<Dialect> loader = ServiceLoader.load(Dialect.class);
    for (Dialect d : loader) {
      if (d.getDialectName().equals(dialect)) {
        logger.info("Executing {} into {}", d.getClass().getCanonicalName(), out.getPath());
        d.generate(api, out);
        logger.info("Finished");
        return true;
      }
    }
    logger.error("Could not find dialect {}", dialect);
    return false;
  }
}
