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

import javax.inject.Inject;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonElement;

/**
 * Maps Class literals to their payload names and back.
 */
public class HasUuidClassCodex extends ValueCodex<Class<? extends HasUuid>> {
  private TypeContext typeContext;

  protected HasUuidClassCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder().withJsonKind(JsonKind.STRING).build();
  }

  @Override
  public Class<? extends HasUuid> readNotNull(JsonElement element, DeserializationContext context)
      throws Exception {
    String name = element.getAsString();
    return typeContext.getClass(name);
  }

  @Override
  public void writeNotNull(Class<? extends HasUuid> object, SerializationContext context)
      throws Exception {
    context.getWriter().value(typeContext.getPayloadName(object));
  }

  @Inject
  void inject(TypeContext typeContext) {
    this.typeContext = typeContext;
  }
}
