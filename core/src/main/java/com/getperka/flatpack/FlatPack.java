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

import java.lang.reflect.Type;

import com.getperka.flatpack.codexes.ValueCodex;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.TypeContext;

/**
 * The main entry-point to the FlatPack API. This type exists to provide a central point for
 * configuring instances of {@link Packer} and {@link Unpacker}.
 */
public class FlatPack {
  /**
   * Create a new instance of FlatPack.
   */
  public static synchronized FlatPack create(Configuration configuration) {
    return new FlatPack(configuration);
  }

  private final Packer packer;
  private final TypeContext types;
  private final Unpacker unpacker;

  private FlatPack(Configuration configuration) {
    types = new TypeContext(configuration);
    packer = new Packer(configuration, types);
    unpacker = new Unpacker(configuration, types);
  }

  /**
   * Returns a configured instance of {@link Packer}.
   */
  public Packer getPacker() {
    return packer;
  }

  /**
   * Returns a reference to the typesystem introspection logic.
   */
  public TypeContext getTypeContext() {
    return types;
  }

  /**
   * Returns a configured instance of {@link Unpacker}.
   */
  public Unpacker getUnpacker() {
    return unpacker;
  }

  /**
   * Returns {@code true} if the given type is an entity or may contain a reference to an entity.
   */
  public boolean isRootType(Type clazz) {
    Codex<?> codex = types.getCodex(clazz);
    return codex != null && !(codex instanceof ValueCodex);
  }
}
