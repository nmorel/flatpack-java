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

import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.SerializationContext;

/**
 * A base class for Codex implementations that process values that cannot contain any entities.
 * 
 * @param <T> the type of data that the instance operates on
 */
public abstract class ValueCodex<T> extends Codex<T> {
  @Override
  public void scanNotNull(T object, SerializationContext context) throws Exception {}
}