/*
 * #%L
 * FlatPack Jersey integration
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
package com.getperka.flatpack.jersey;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by the API documentation generator to specify the format of the returned data.
 * <p>
 * If the return type is parameterized (e.g. {@link java.util.List}), the value of the annotation
 * should be a flattened representation of the generic type. For example
 * {@code Map<String, Merchant>} would be
 * 
 * <pre>
 * {@literal @}FlatPackEntity({Map.class, String.class, Merchant.class})
 * </pre>
 * <p>
 * This annotation has no runtime implications if the actual value returned does not match the
 * declaration.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlatPackResponse {
  Class<?>[] value();
}
