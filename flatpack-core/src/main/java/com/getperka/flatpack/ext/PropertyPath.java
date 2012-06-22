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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;

import com.getperka.flatpack.BaseHasUuid;

/**
 * Represents a sequence of simple property evaluations.
 */
public class PropertyPath extends BaseHasUuid {

  public interface Receiver {
    /**
     * Returns {@code true} if additional values should be received.
     */
    boolean receive(Object value);
  }

  private final List<Property> path;

  /**
   * Constructor that creates a copy of {@code path}.
   */
  public PropertyPath(Collection<Property> path) {
    this.path = Collections.unmodifiableList(new ArrayList<Property>(path));
  }

  /**
   * Evaluate each possible property value in the path, passing the value to {@code receiver}.
   */
  public void evaluate(Object target, Receiver receiver) {
    evaluate(target, path, receiver);
  }

  /**
   * Return an unmodifiable view of the properties that comprise the path.
   */
  @PermitAll
  public List<Property> getPath() {
    return path;
  }

  /**
   * For debugging use only.
   */
  @Override
  public String toString() {
    if (path.isEmpty()) {
      return "<this>";
    }

    StringBuilder sb = new StringBuilder();
    boolean needsDot = false;
    for (Property p : path) {
      if (needsDot) {
        sb.append(".");
      } else {
        needsDot = true;
      }
      sb.append(p.getName());
    }
    return sb.toString();
  }

  private boolean evaluate(Object target, List<Property> properties, Receiver receiver) {
    if (properties.isEmpty()) {
      return receiver.receive(target);
    }
    Throwable ex;
    try {
      Property prop = properties.get(0);
      Object currentValue = prop.getGetter().invoke(target);
      switch (prop.getType().getJsonKind()) {
        case STRING:
          return evaluate(currentValue, properties.subList(1, properties.size()), receiver);
        case LIST:
          for (Object element : (Iterable<?>) currentValue) {
            if (!evaluate(element, properties.subList(1, properties.size()), receiver)) {
              return false;
            }
          }
          break;
        case MAP:
          for (Object element : ((Map<?, ?>) currentValue).values()) {
            if (!evaluate(element, properties.subList(1, properties.size()), receiver)) {
              return false;
            }
          }
          break;
        default:
          throw new RuntimeException("Cannot descend into property " + prop);
      }
      return true;
    } catch (IllegalArgumentException e) {
      ex = e;
    } catch (IllegalAccessException e) {
      ex = e;
    } catch (InvocationTargetException e) {
      ex = e.getCause();
    }
    throw new RuntimeException(ex);
  }
}
