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
package com.getperka.flatpack.inject;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.inject.Inject;

import com.getperka.flatpack.codexes.DefaultCodexMapper;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.TypeContext;

/**
 * Aggregates all user-provided {@link CodexMapper} instances with the {@link DefaultCodexMapper}.
 */
class CompositeCodexMapper implements CodexMapper {

  @Inject
  private DefaultCodexMapper defaultMapper;

  private final Collection<CodexMapper> extraMappers;

  CompositeCodexMapper(Collection<CodexMapper> extraMappers) {
    this.extraMappers = extraMappers;
  }

  @Override
  public Codex<?> getCodex(TypeContext context, Type type) {
    for (CodexMapper mapper : extraMappers) {
      Codex<?> codex = mapper.getCodex(context, type);
      if (codex != null) {
        return codex;
      }
    }
    return defaultMapper.getCodex(context, type);
  }
}
