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
package com.getperka.flatpack;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.ConstraintViolation;

import org.slf4j.Logger;

import com.getperka.flatpack.codexes.EntityCodex;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.inject.FlatPackLogger;
import com.getperka.flatpack.inject.PackScope;
import com.getperka.flatpack.inject.PrettyPrint;
import com.getperka.flatpack.inject.Verbose;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.stream.JsonWriter;

/**
 * Writes {@link FlatPackEntity} objects into a {@link Writer}.
 */
public class Packer {

  @Inject
  private Provider<SerializationContext> contexts;
  @Inject
  @FlatPackLogger
  private Logger logger;
  @Inject
  private PackScope packScope;
  @Inject
  private PersistenceMapper persistenceMapper;
  @Inject
  private TypeContext typeContext;
  @Inject
  @Verbose
  private boolean verbose;
  @Inject
  @PrettyPrint
  private boolean prettyPrint;

  @Inject
  private Provider<EntityCodex<EntityMetadata>> metadataCodex;

  Packer() {}

  /**
   * Write the given entity into a {@link Writer}.
   * 
   * @param entity the entity to write
   * @param out the destination output which will be closed by this method
   */
  public void pack(FlatPackEntity<?> entity, Writer out) throws IOException {
    StringWriter verboseWriter = null;
    Writer target;
    if (verbose) {
      verboseWriter = new StringWriter();
      target = verboseWriter;
    } else {
      target = out;
    }
    JsonWriter json = new JsonWriter(target);
    json.setSerializeNulls(false);
    if (prettyPrint) {
      json.setIndent("  ");
    }

    packScope.enter().withEntity(entity).withJsonWriter(json);
    try {
      pack(entity);
    } finally {
      packScope.exit();
    }
    if (verbose) {
      String payload = verboseWriter.toString();
      logger.debug("Outgoing flatpack payload:\n{}", payload);
      out.write(payload);
      verboseWriter.close();
      out.close();
    }
  }

  /**
   * Creates a map representing the {@code data} payload structure from an assortment of entities.
   * This method also filters out persistent objects that do not have any local mutations.
   */
  private Map<Class<? extends HasUuid>, List<HasUuid>> collate(Set<HasUuid> entities) {
    Map<Class<? extends HasUuid>, List<HasUuid>> toReturn = FlatPackCollections
        .mapForIteration();

    for (HasUuid entity : entities) {
      Class<? extends HasUuid> key = entity.getClass();

      // Ignore any dirty-tracking entity with no mutations
      if (entity instanceof PersistenceAware) {
        PersistenceAware maybeDirty = (PersistenceAware) entity;
        if (maybeDirty.wasPersistent() && maybeDirty.dirtyPropertyNames().isEmpty()) {
          continue;
        }
      }

      List<HasUuid> list = toReturn.get(key);
      if (list == null) {
        list = FlatPackCollections.listForAny();
        toReturn.put(key, list);
      }
      list.add(entity);
    }

    return toReturn;
  }

  private void pack(FlatPackEntity<?> entity) throws IOException {
    SerializationContext context = contexts.get(); // injector.getInstance(SerializationContext.class);

    @SuppressWarnings("unchecked")
    Codex<Object> codex = (Codex<Object>) typeContext.getCodex(entity.getType());
    if (entity.getTraversalMode().isSparse()) {
      // Sparse entities only include explicitly-enumerated entities
      for (HasUuid extra : entity.getExtraEntities()) {
        context.add(extra);
      }
    } else {
      codex.scan(entity.getValue(), context);
      @SuppressWarnings("unchecked")
      Codex<Collection<HasUuid>> extraCodex = (Codex<Collection<HasUuid>>) typeContext
          .getCodex(new TypeReference<Collection<HasUuid>>() {}.getType());
      extraCodex.scan(entity.getExtraEntities(), context);
    }

    JsonWriter json = context.getWriter();
    json.beginObject();
    // value : ['type', 'uuid']
    json.name("value");
    codex.write(entity.getValue(), context);

    // errors : { 'foo.bar.baz' : 'May not be null' }
    Set<ConstraintViolation<?>> violations = entity.getConstraintViolations();
    Map<String, String> errors = entity.getExtraErrors();
    if (!violations.isEmpty() || !errors.isEmpty()) {
      json.name("errors");
      json.beginObject();
      for (ConstraintViolation<?> v : violations) {
        json.name(v.getPropertyPath().toString());
        json.value(v.getMessage());
      }
      for (Map.Entry<String, String> entry : errors.entrySet()) {
        json.name(entry.getKey()).value(entry.getValue());
      }
      json.endObject(); // errors
    }

    List<HasUuid> persistent = FlatPackCollections.listForAny();

    // data : { 'type' : [ { ... }, { ... } ], 'otherType' : [ { ... }, { ... } ] }
    json.name("data");
    json.beginObject();
    for (Map.Entry<Class<? extends HasUuid>, List<HasUuid>> entry : collate(
        context.getEntities()).entrySet()) {
      // Determine how to encode the current type
      EntityCodex<HasUuid> entityCodex =
          (EntityCodex<HasUuid>) typeContext.getCodex(entry.getKey());

      json.name(typeContext.getPayloadName(entry.getKey()));
      json.beginArray();
      for (HasUuid toWrite : entry.getValue()) {
        if (persistenceMapper.isPersisted(toWrite)) {
          persistent.add(toWrite);
        }
        entityCodex.writeProperties(toWrite, context);
      }
      json.endArray();
    }
    json.endObject(); // data

    // Write metadata for any entities
    if (!persistent.isEmpty()) {
      EntityCodex<EntityMetadata> metaCodex = metadataCodex.get();
      json.name("metadata");
      json.beginArray();
      for (HasUuid toWrite : persistent) {
        EntityMetadata meta = new EntityMetadata();
        meta.setPersistent(true);
        meta.setUuid(toWrite.getUuid());
        metaCodex.writeProperties(meta, context);
      }
      json.endArray(); // metadata
    }

    // Write extra top-level data keys, which are only used for simple side-channel data
    for (Map.Entry<String, String> entry : entity.getExtraData().entrySet()) {
      json.name(entry.getKey()).value(entry.getValue());
    }

    // Write extra warnings, some of which may be from the serialization process
    Map<UUID, String> codexWarnings = context.getWarnings();
    Map<String, String> warnings = entity.getExtraWarnings();
    if (!codexWarnings.isEmpty() || !warnings.isEmpty()) {
      json.name("warnings");
      json.beginObject();
      for (Map.Entry<UUID, String> entry : codexWarnings.entrySet()) {
        json.name(entry.getKey().toString()).value(entry.getValue());
      }
      for (Map.Entry<String, String> entry : warnings.entrySet()) {
        json.name(entry.getKey()).value(entry.getValue());
      }
      json.endObject(); // warnings
    }
    json.endObject(); // core payload

    context.runPostWork();
    context.close();
  }

}
