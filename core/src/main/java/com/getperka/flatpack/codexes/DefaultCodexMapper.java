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
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.DateTimeZone;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.CodexMapper;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gson.JsonElement;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * Support for all built-in types.
 */
public class DefaultCodexMapper implements CodexMapper {

  @Inject
  private Injector injector;

  DefaultCodexMapper() {}

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Codex<?> getCodex(TypeContext context, Type type) {
    Codex<?> toReturn = null;
    Class<?> erased = erase(type);

    // Collections and collection-like objects
    if (Collection.class.isAssignableFrom(erased)) {
      Type valueType = getSingleParameterization(type, Collection.class);
      toReturn = new CollectionCodex(erased, context.getCodex(valueType));
    } else if (erased.isArray()) {
      // Treat an array like a list
      toReturn = getInstance(ArrayCodex.class, erased.getComponentType());
    } else if (Map.class.isAssignableFrom(erased)) {
      // Maps can be <String, ?> or <HasUuid, ?>
      Type[] params = getParameterization(Map.class, type);
      if (HasUuid.class.isAssignableFrom(erase(params[0]))) {
        toReturn = new EntityMapCodex((EntityCodex<?>) context.getCodex(params[0]),
            context.getCodex(params[1]));
      } else {
        toReturn = new StringMapCodex(context.getCodex(params[1]));
      }
      // Scalar types below here
    } else if (Boolean.class.equals(erased) || boolean.class.equals(erased)) {
      toReturn = new BooleanCodex();
    } else if (CharSequence.class.isAssignableFrom(erased)) {
      toReturn = new StringCodex();
    } else if (Class.class.equals(erased)) {
      toReturn = new HasUuidClassCodex();
    } else if (DateTimeZone.class.isAssignableFrom(erased)) {
      toReturn = new DateTimeZoneCodex();
    } else if (Enum.class.isAssignableFrom(erased)) {
      toReturn = new EnumCodex(erased);
    } else if (HasUuid.class.isAssignableFrom(erased)) {
      toReturn = getInstance(EntityCodex.class, type);
    } else if (JsonElement.class.isAssignableFrom(erased)) {
      toReturn = new JsonElementCodex();
    } else if (TypeHint.class.isAssignableFrom(erased)) {
      toReturn = new TypeHintCodex();
    } else if (UUID.class.equals(erased)) {
      toReturn = new UUIDCodex();
    } else if (Void.class.equals(erased) || void.class.equals(erased)) {
      toReturn = new VoidCodex();
    } else if (BOXED_TYPES.contains(erased)) {
      toReturn = new NumberCodex(erased);
    } else if (PRIMITIVE_TYPES.contains(erased)) {
      toReturn = new NumberCodex(box(erased));
    } else {
      /*
       * Try to find a one-arg String or Object constructor. This is kind of shady, but it works for
       * a number of the auxiliary value types used in the model classes, mainly joda-time.
       */
      try {
        Constructor<?> constructor = erased.getConstructor(String.class);
        toReturn = new ToStringCodex(constructor);
      } catch (NoSuchMethodException expected) {}
      try {
        Constructor<?> constructor = erased.getConstructor(Object.class);
        toReturn = new ToStringCodex(constructor);
      } catch (NoSuchMethodException expected) {}
    }
    return toReturn;
  }

  private Codex<?> getInstance(Class<?> codexType, Type type) {
    Key<?> key = Key.get(createType(codexType, type));
    return (Codex<?>) injector.getInstance(key);
  }

}
