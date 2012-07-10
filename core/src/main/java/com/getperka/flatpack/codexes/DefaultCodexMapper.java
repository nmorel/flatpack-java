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

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

/**
 * Support for all built-in types.
 */
public class DefaultCodexMapper implements CodexMapper {

  private final Injector injector;
  private final Map<Class<?>, ValueCodex<?>> simpleCodexes = FlatPackCollections.mapForLookup();

  @Inject
  DefaultCodexMapper(Injector injector) {
    this.injector = injector;

    simpleCodexes.put(boolean.class, injector.getInstance(BooleanCodex.class));
    simpleCodexes.put(Boolean.class, injector.getInstance(BooleanCodex.class));
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
      clazz = box(clazz);
      if (Number.class.isAssignableFrom(clazz)) {
        @SuppressWarnings("unchecked")
        Key<ValueCodex<?>> key = (Key<ValueCodex<?>>) Key.get(createType(NumberCodex.class, clazz));
        simpleCodexes.put(clazz, injector.getInstance(key));
      }
    }
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Codex<?> getCodex(TypeContext context, Type type) {
    // Simple types
    if (simpleCodexes.containsKey(type)) {
      return simpleCodexes.get(type);
    }

    Class<?> erased = erase(type);

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

    /*
     * Try to find a one-arg String or Object constructor. This is kind of shady, but it works for a
     * number of the auxiliary value types used in the model classes, mainly joda-time.
     */
    try {
      Constructor<?> constructor = erased.getConstructor(String.class);
      return new ToStringCodex(constructor);
    } catch (NoSuchMethodException expected) {}
    try {
      Constructor<?> constructor = erased.getConstructor(Object.class);
      return new ToStringCodex(constructor);
    } catch (NoSuchMethodException expected) {}
    return null;
  }

  private Codex<?> getInstance(Class<?> codexType, Type type) {
    Key<?> key;
    try {
      key = Key.get(createType(codexType, type));
    } catch (ConfigurationException e) {
      key = Key.get(createType(codexType, erase(type)));
    }
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
