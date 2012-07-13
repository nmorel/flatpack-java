/*
 * #%L
 * FlatPack Client
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
package com.getperka.flatpack.client;

import com.getperka.flatpack.FlatPackEntity;

/**
 * A specialization of {@link Request} that allows access to the {@link FlatPackEntity} that will be
 * sent to the server.
 * 
 * @param <R> the Requset type
 * @param <X> the type of data contained in the FlatPackEntity
 */
public interface FlatPackRequest<R extends FlatPackRequest<R, X>, X> extends
    Request<R, FlatPackEntity<X>> {
  FlatPackEntity<X> peek();
}