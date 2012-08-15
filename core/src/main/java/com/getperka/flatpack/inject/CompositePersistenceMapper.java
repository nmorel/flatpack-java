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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceMapper;

/**
 * A no-op implementation of PersistenceMapper.
 */
class CompositePersistenceMapper implements PersistenceMapper {
  private final List<PersistenceMapper> mappers;
  private final Map<Class<? extends HasUuid>, PersistenceMapper> map =
      new ConcurrentHashMap<Class<? extends HasUuid>, PersistenceMapper>();

  public CompositePersistenceMapper(List<PersistenceMapper> mappers) {
    this.mappers = mappers;
  }

  @Override
  public boolean canPersist(Class<? extends HasUuid> entityType) {
    if (map.containsKey(entityType)) {
      return map.get(entityType) != this;
    }
    for (PersistenceMapper mapper : mappers) {
      if (mapper.canPersist(entityType)) {
        map.put(entityType, mapper);
        return true;
      }
    }
    // Can't put a null value into ConcurrentHashMap, so use this instead
    map.put(entityType, this);
    return false;
  }

  @Override
  public boolean isPersisted(HasUuid entity) {
    if (!canPersist(entity.getClass())) {
      return false;
    }
    map.get(entity.getClass()).isPersisted(entity);
    return false;
  }
}
