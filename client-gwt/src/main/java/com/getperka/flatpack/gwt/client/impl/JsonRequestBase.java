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
package com.getperka.flatpack.gwt.client.impl;

import com.getperka.flatpack.gwt.client.StatusCodeException;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.Response;

/**
 * A Request that interprets the server response as JSON.
 *
 * @param <R> the type of JsonRequestBase
 */
public class JsonRequestBase<R extends JsonRequestBase<R>>
    extends RequestBase<R, String, String>
{

    protected JsonRequestBase( ApiBase api, Method method, String path, boolean hasPayload, Object... args )
    {
        super( api, method, path, hasPayload, args );
    }

    @Override
    protected String decodeResponse( Response response )
        throws StatusCodeException
    {
        int statusCode = response.getStatusCode();

        if ( isOk( statusCode ) )
        {
            return response.getText();
        }
        else
        {
            StatusCodeException ex = new StatusCodeException( statusCode );
            ex.setResponse( response.getText() );
            throw ex;
        }
    }

    @Override
    protected void writeEntity( RequestBuilder requestBuilder )
    {
    }

}
