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

import java.io.IOException;

import javax.inject.Inject;

import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.google.gson.JsonElement;

/**
 * Allows arbitrary objects to be written, but not read, by examining their type.
 */
public class DynamicCodex extends Codex<Object> {

  @Inject
  private TypeContext typeContext;

  DynamicCodex() {}

  @Override
  public Type describe() {
    return new Type.Builder().withJsonKind(JsonKind.ANY).build();
  }

  @Override
  public Object readNotNull(JsonElement element, DeserializationContext context)
      throws IOException {
    context.fail(new UnsupportedOperationException("Cannot read arbitrary type"));
    return null;
  }

  @Override
  public void scanNotNull(Object object, SerializationContext context) {
    Codex<Object> actual = typeContext.getCodex(object.getClass());
    if (actual == this) {
      context.fail(new UnsupportedOperationException(object.getClass().getName()));
    }
    actual.scan(object, context);
  }

  @Override
  public void writeNotNull(Object object, SerializationContext context) throws IOException {
    Codex<Object> actual = typeContext.getCodex(object.getClass());
    actual.write(object, context);
  }
}