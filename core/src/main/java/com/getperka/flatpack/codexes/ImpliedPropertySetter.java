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

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.PostWorkOrder;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * The logic that is used to set implied properties. This work is deferred so that collection
 * properties may be mutated after any payload values are set.
 */
@PostWorkOrder(100)
class ImpliedPropertySetter implements Callable<Void> {
  private final DeserializationContext context;
  private final Property toSet;
  private final Object target;
  private final Object value;

  public ImpliedPropertySetter(DeserializationContext context, Property toSet, Object target,
      Object value) {
    if (toSet.getSetter() == null) {
      throw new IllegalArgumentException("No setter");
    }
    this.context = context;
    this.toSet = toSet;
    this.target = target;
    this.value = value;
  }

  @Override
  public Void call() throws Exception {
    Class<?> type = toSet.getGetter().getReturnType();
    if (Collection.class.isAssignableFrom(type)) {
      HasUuid entity = (HasUuid) target;
      if (!context.checkAccess(entity)) {
        return null;
      }
      Collection<Object> collection = null;

      /*
       * Update implied collections in-place. If the incoming payload had an explicit value for the
       * collection property, it will have been reset to a new collection instance already.
       */
      @SuppressWarnings("unchecked")
      Collection<Object> temp = (Collection<Object>) toSet.getGetter().invoke(target);
      collection = temp;

      // Create a new collection as necessary
      if (collection == null) {
        if (Set.class.isAssignableFrom(type)) {
          collection = FlatPackCollections.setForIteration();
        } else {
          collection = FlatPackCollections.listForAny();
        }
        toSet.getSetter().invoke(target, collection);
        context.addModified(entity, toSet);
      }
      // We can't assume much about the collection's behavior
      if (!collection.contains(value)) {
        collection.add(value);
      }
    } else if (target instanceof Collection) {
      for (Object element : (Collection<?>) target) {
        if (context.checkAccess((HasUuid) element)) {
          toSet.getSetter().invoke(element, value);
        }
      }
    } else if (context.checkAccess((HasUuid) target)) {
      toSet.getSetter().invoke(target, value);
    }
    return null;
  }
}