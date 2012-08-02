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
 * Indicates that a referenced entity's properties should be inlined into its parent entity. This
 * annotation allows the use of simple view types that aggregate a number of other objects together
 * in order to present a simplified conceptual model for clients.
 * <p>
 * Given the following two classes
 * 
 * <pre>
 * class Bar extends BaseHasUuid {
 *   public String getString();
 * }
 * class Foo extends BaseHasUuid {
 *   {@literal @Embedded}
 *   public Bar getBar() {}
 * }
 * </pre>
 * 
 * an instance of {@code Foo} would be serialized as
 * 
 * <pre>
 * {
 *   "uuid" : "abc-123",
 *   "string" : "Hello world"
 * }
 * </pre>
 * <p>
 * Note that when an instance of {@code Foo} is reified, its associated {@code Bar} instance will
 * have a randomly-assigned UUID.
 * <p>
 * This annotation is interchangeable with the similarly-named annotations found in JPA or other
 * persistence APIs.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Embedded {}
