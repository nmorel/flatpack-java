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

import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.google.gson.JsonElement;

/**
 * Ignores all values.
 */
public class VoidCodex extends ValueCodex<Void> {
  protected VoidCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder().withJsonKind(JsonKind.NULL).build();
  }

  /**
   * The only valid value is {@code null}, which is the default value.
   */
  @Override
  public boolean isDefaultValue(Void value) {
    return true;
  }

  @Override
  public Void readNotNull(JsonElement element, DeserializationContext context) throws Exception {
    return null;
  }

  @Override
  public void writeNotNull(Void object, SerializationContext context) throws Exception {
    context.getWriter().nullValue();
  }
}