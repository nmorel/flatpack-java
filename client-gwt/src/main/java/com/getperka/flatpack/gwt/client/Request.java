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
package com.getperka.flatpack.gwt.client;

/**
 * Represents a server request. Instances of Request suclassess are vended by generated {@link Api} subtypes.
 *
 * @param <R> the Request type
 * @param <S> the type of data sent
 * @param <X> the type of data returned from {@link #execute()}
 */
public interface Request<R extends Request<R, S, X>, S, X>
{
    /**
     * Execute the request.
     */
    R execute( FlatBack<X> callback );

    /**
     * Add a customer header to the request.
     */
    R header( String name, Object value );

    /**
     * Add a custom query parameter to the request. The generated subclasses of Request will have convenience methods
     * for setting defined query parameters.
     */
    R queryParameter( String name, Object value );

    /**
     * Add data to send in the payload
     */
    R setEntity( S entity );
}