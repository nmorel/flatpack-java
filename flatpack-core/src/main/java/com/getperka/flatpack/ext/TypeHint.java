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
package com.getperka.flatpack.ext;

import java.util.HashMap;
import java.util.Map;

/**
 * Indicates how certain simple {@link JsonKind} types should be interpreted as more complex values.
 */
public class TypeHint {
  private static final Map<String, TypeHint> map = new HashMap<String, TypeHint>();

  public static TypeHint create(Class<?> clazz) {
    return create(clazz.getCanonicalName());
  }

  public static synchronized TypeHint create(String description) {
    TypeHint toReturn = map.get(description);
    if (toReturn == null) {
      toReturn = new TypeHint(description);
      map.put(description, toReturn);
    }
    return toReturn;
  }

  private final String description;

  private TypeHint(String description) {
    this.description = description;
  }

  public String getValue() {
    return description;
  }
}
