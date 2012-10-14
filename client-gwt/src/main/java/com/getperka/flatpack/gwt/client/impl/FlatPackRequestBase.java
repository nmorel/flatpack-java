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

import com.getperka.flatpack.gwt.FlatPackEntity;
import com.getperka.flatpack.gwt.client.FlatPackApi;
import com.getperka.flatpack.gwt.client.FlatPackRequest;
import com.getperka.flatpack.gwt.client.StatusCodeException;
import com.getperka.flatpack.gwt.codexes.Codex;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.Response;

public class FlatPackRequestBase<R extends FlatPackRequest<R, S, X>, S, X>
    extends RequestBase<R, S, FlatPackEntity<X>>
    implements FlatPackRequest<R, S, X>
{
    private final FlatPackApi api;
    private final Codex<S> sentCodex;
    private FlatPackEntity<S> toSend;
    private final Codex<X> returnCodex;

    protected FlatPackRequestBase( ApiBase api, Method method, String path, Codex<S> sentCodex, Codex<X> returnCodex,
                                   Object... args )
    {
        super( method, path, args );
        this.api = api;
        this.sentCodex = sentCodex;
        this.returnCodex = returnCodex;
    }

    @Override
    public FlatPackEntity<S> getEntity()
    {
        return toSend;
    }

    @Override
    public R setEntity( S entity )
    {
        toSend = new FlatPackEntity<S>().withValue( entity );
        return as();
    }

    @Override
    public FlatPackEntity<S> peek()
    {
        return getEntity();
    }

    @Override
    protected FlatPackEntity<X> decodeResponse( Response response )
        throws StatusCodeException
    {
        FlatPackEntity<X> entity = getApi().getUnpacker().unpack( response.getText(), returnCodex );

        int statusCode = response.getStatusCode();
        if ( isOk( statusCode ) )
        {
            return entity;
        }
        else
        {
            StatusCodeException sce = new StatusCodeException( statusCode );
            sce.setEntity( entity );
            sce.setResponse( response.getText() );
            throw sce;
        }
    }

    @Override
    protected void writeEntity( RequestBuilder requestBuilder )
    {
        if ( getEntity() == null )
        {
            return;
        }

        requestBuilder.setRequestData( getApi().getPacker().pack( getEntity(), sentCodex ) );
    }

    @Override
    protected FlatPackApi getApi()
    {
        return api;
    }
}