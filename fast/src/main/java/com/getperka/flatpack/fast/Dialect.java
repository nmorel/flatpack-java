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

import com.getperka.flatpack.client.dto.ApiDescription;

/**
 * A code-generation dialect. To register a new implementation, add or update the
 * {@code META-INF/services/com.getperka.flatpack.fast.Dialect} resource.
 */
public interface Dialect {
  /**
   * Generate accessors for {@code api} into {@code outputDir}.
   */
  void generate(ApiDescription api, File outputDir) throws IOException;

  /**
   * The name of the dialect, used to select the implementation based on the {@code --dialect} flag
   * passed into the tool.
   */
  String getDialectName();
}