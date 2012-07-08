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
