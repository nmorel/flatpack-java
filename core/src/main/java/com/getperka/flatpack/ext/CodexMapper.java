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
package com.getperka.flatpack.ext;

import java.lang.reflect.Type;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.codexes.DefaultCodexMapper;
import com.getperka.flatpack.util.FlatPackTypes;

/**
 * Provides implementations of {@link Codex} for various types. Users can inject additional
 * CodexMapper implementations into the FlatPack stack via
 * {@link Configuration#addCodexMapper(CodexMapper)}.
 * 
 * @see DefaultCodexMapper
 * @see FlatPackTypes
 */
public interface CodexMapper {
  /**
   * Returns a Codex capable of processing {@code type} or {@code null} if the mapper does not have
   * a suitable Codex. Calls to this method will be cached by {@link TypeContext}.
   */
  Codex<?> getCodex(TypeContext context, Type type);
}
