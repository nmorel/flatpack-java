package com.getperka.flatpack;

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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applied to a collection property getter to indicate that the collection should not be traversed
 * in {@link TraversalMode#SIMPLE}. Additionally, this annotation may name a property in the
 * collection's element type that will cause FlatPack to keep two sides of a relationship in sync if
 * an incoming payload only specifies one of the relationships.
 * <p>
 * Consider the following example:
 * 
 * <pre>
 * class Child extends BaseHasUuid {
 *   public Parent getParent() {}
 * }
 * class Parent extends BaseHasUuid {
 *   {@literal @SparseCollection("parent")}
 *   public List<Child> getChildren();
 * }
 * </pre>
 * 
 * If the following payload is received
 * 
 * <pre>
 * {
 *   "value" : "abc-123",
 *   "data" : {
 *     "child" : [
 *       {
 *         "uuid" : "abc-123",
 *         "parent" : "987-fed"
 *       ]
 *     ]
 *   }
 * }
 * </pre>
 * 
 * then if a {@code Parent} with uuid {@code 987-fed} is resolved, the incoming {@code Child} will
 * be added automatically to the {@code Parent.children} collection. Conversely, if a {@code Parent}
 * is received that referances a {@code Child}, whether in the payload or resolved from a backing
 * store, the {@code Child.parent} property will be implicitly updated.
 * <p>
 * This annotation is interchangeable with JPA's {@code @OneToMany} annotation with an explicit
 * {@code mappedBy} property.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SparseCollection {
  /**
   * The name of the property in the referenced entity type to update.
   */
  String value() default "";
}
