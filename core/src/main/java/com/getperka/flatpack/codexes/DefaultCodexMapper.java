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
package com.getperka.flatpack.codexes;

import static com.getperka.flatpack.util.FlatPackTypes.BOXED_TYPES;
import static com.getperka.flatpack.util.FlatPackTypes.PRIMITIVE_TYPES;
import static com.getperka.flatpack.util.FlatPackTypes.box;
import static com.getperka.flatpack.util.FlatPackTypes.createType;
import static com.getperka.flatpack.util.FlatPackTypes.erase;
import static com.getperka.flatpack.util.FlatPackTypes.getParameterization;
import static com.getperka.flatpack.util.FlatPackTypes.getSingleParameterization;
import static com.getperka.flatpack.util.FlatPackTypes.instantiable;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.DateTimeZone;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.ext.TypeHint;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.JsonElement;
import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.ProvisionException;

/**
 * Support for all built-in types.
 */
public class DefaultCodexMapper implements CodexMapper {

  private Injector injector;
  private final Map<Class<?>, ValueCodex<?>> simpleCodexes = FlatPackCollections.mapForIteration();

  protected DefaultCodexMapper() {}

  @Override
  public Codex<?> getCodex(TypeContext context, Type type) {
    Class<?> erased = erase(type);

    // Simple types
    if (simpleCodexes.containsKey(erased)) {
      return simpleCodexes.get(erased);
    }
    for (Entry<Class<?>, ValueCodex<?>> entry : simpleCodexes.entrySet()) {
      if (entry.getKey().isAssignableFrom(erased)) {
        return entry.getValue();
      }
    }

    // Entities
    if (HasUuid.class.isAssignableFrom(erased)) {
      return getInstance(EntityCodex.class, type);
    }

    // Enums
    if (Enum.class.isAssignableFrom(erased)) {
      return getInstance(EnumCodex.class, erased);
    }

    // Collections and collection-like objects
    if (erased.isArray()) {
      // Treat an array like a list
      return getInstance(ArrayCodex.class, erased.getComponentType());
    }
    if (Collection.class.equals(erased) || List.class.isAssignableFrom(erased)) {
      Type valueType = getSingleParameterization(type, Collection.class);
      return getInstance(ListCodex.class, valueType);
    }
    if (Set.class.isAssignableFrom(erased)) {
      Type valueType = getSingleParameterization(type, Collection.class);
      return getInstance(SetCodex.class, valueType);
    }

    // Maps, either <String, ?> or <HasUuid, ?>
    if (Map.class.isAssignableFrom(erased)) {
      Type[] params = getParameterization(Map.class, type);
      if (HasUuid.class.isAssignableFrom(erase(params[0]))) {
        return getInstance(EntityMapCodex.class, params[0], params[1]);
      } else if (String.class.equals(params[0])) {
        return getInstance(StringMapCodex.class, params[1]);
      }
    }

    // java.util.Date and subtypes
    if (java.util.Date.class.isAssignableFrom(erased)) {
      return getInstance(DateCodex.class, erased);
    }

    /*
     * Try to find a one-arg String or Object constructor. This is kind of shady, but it works for a
     * number of the auxiliary value types used in the model classes, mainly joda-time.
     */
    try {
      return getInstance(ToStringCodex.class, type);
    } catch (ProvisionException ignored) {}

    return null;
  }

  @Inject
  void inject(Injector injector) {
    this.injector = injector;

    simpleCodexes.put(BigDecimal.class, (NumberCodex<?>) injector.getInstance(
        Key.get(createType(NumberCodex.class, BigDecimal.class))));
    simpleCodexes.put(BigInteger.class, (NumberCodex<?>) injector.getInstance(
        Key.get(createType(NumberCodex.class, BigInteger.class))));
    simpleCodexes.put(boolean.class, injector.getInstance(BooleanCodex.class));
    simpleCodexes.put(Boolean.class, injector.getInstance(BooleanCodex.class));
    simpleCodexes.put(char.class, injector.getInstance(CharacterCodex.class));
    simpleCodexes.put(Character.class, injector.getInstance(CharacterCodex.class));
    simpleCodexes.put(Class.class, injector.getInstance(HasUuidClassCodex.class));
    simpleCodexes.put(DateTimeZone.class, injector.getInstance(DateTimeZoneCodex.class));
    simpleCodexes.put(JsonElement.class, injector.getInstance(JsonElementCodex.class));
    simpleCodexes.put(String.class, injector.getInstance(StringCodex.class));
    simpleCodexes.put(TypeHint.class, injector.getInstance(TypeHintCodex.class));
    simpleCodexes.put(UUID.class, injector.getInstance(UUIDCodex.class));
    simpleCodexes.put(void.class, injector.getInstance(VoidCodex.class));
    simpleCodexes.put(Void.class, injector.getInstance(VoidCodex.class));

    for (Class<?> clazz : BOXED_TYPES) {
      if (Number.class.isAssignableFrom(clazz)) {
        @SuppressWarnings("unchecked")
        Key<ValueCodex<?>> key = (Key<ValueCodex<?>>) Key.get(createType(NumberCodex.class, clazz));
        simpleCodexes.put(clazz, injector.getInstance(key));
      }
    }

    for (Class<?> clazz : PRIMITIVE_TYPES) {
      Class<?> boxed = box(clazz);
      if (Number.class.isAssignableFrom(boxed)) {
        @SuppressWarnings("unchecked")
        Key<ValueCodex<?>> key = (Key<ValueCodex<?>>) Key.get(createType(NumberCodex.class, boxed));
        simpleCodexes.put(clazz, injector.getInstance(key));
      }
    }
  }

  private Codex<?> getInstance(Class<?> codexType, Type type) {
    Key<?> key = Key.get(createType(codexType, instantiable(type)));
    return (Codex<?>) injector.getInstance(key);
  }

  private Codex<?> getInstance(Class<?> codexType, Type type, Type type2) {
    Key<?> key;
    try {
      key = Key.get(createType(codexType, type, type2));
    } catch (ConfigurationException e) {
      key = Key.get(createType(codexType, erase(type)));
    }
    return (Codex<?>) injector.getInstance(key);
  }
}
