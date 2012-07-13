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
/**
 * FlatPack uses Guice internally for configuration and instantiation of various components. 
 * Users should not generally need to interact with any of the types in this package. Advanced
 * integration cases may forego the use of the {@link com.getperka.flatpack.FlatPack#create},
 * instead choosing to install {@link com.getperka.flatpack.inject.FlatPackModule}
 * into their Guice configuration.
 */
package com.getperka.flatpack.inject;