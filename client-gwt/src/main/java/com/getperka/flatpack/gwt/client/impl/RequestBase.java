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

import java.util.Collections;
import java.util.Map;

import com.getperka.flatpack.gwt.client.FlatBack;
import com.getperka.flatpack.gwt.client.Request;
import com.getperka.flatpack.gwt.client.StatusCodeException;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

abstract class RequestBase<R extends Request<R, S, X>, S, X>
    implements Request<R, S, X>
{
    // private static final Pattern pathArgPattern = Pattern.compile( "[{][^}]*[}]" );
    private final ApiBase api;
    private final Object[] args;
    private S entity;
    private Map<String, Object> headers = Collections.emptyMap();
    private final Method method;
    private final String path;
    private Map<String, Object> queryParams = Collections.emptyMap();
    private final boolean hasPayload;

    protected RequestBase( ApiBase api, Method method, String path, boolean hasPayload, Object... args )
    {
        this.api = api;
        this.args = args;
        this.method = method;
        this.path = path;
        this.hasPayload = hasPayload;
    }

    @Override
    public R execute( final FlatBack<X> callback )
    {
        String replacedPath = path;

        // TODO
        // Replace all {foo} in the path with the args
        // Matcher m = pathArgPattern.matcher( replacedPath );
        // int index = 0;
        // while ( m.find() && index < args.length )
        // {
        // replacedPath = m.replaceFirst( args[index++].toString() );
        // m = pathArgPattern.matcher( replacedPath );
        // }

        StringBuilder sb = new StringBuilder( replacedPath );
        // Now add query parameters
        if ( !queryParams.isEmpty() )
        {
            sb.append( "?" );
            boolean needsAmp = false;
            for ( Map.Entry<String, Object> entry : queryParams.entrySet() )
            {
                if ( needsAmp )
                {
                    sb.append( "&" );
                }
                else
                {
                    needsAmp = true;
                }
                // TODO encode ?
                sb.append( entry.getKey() ).append( "=" )
                    .append( /* URLEncoder.encode( */entry.getValue().toString()/* , "UTF8" ) */);
            }
        }

        String url = api.getServerBase() + sb.toString();

        RequestBuilder requestBuilder = new RequestBuilder( method, url );

        // HttpURLConnection conn = (HttpURLConnection) sendTo.toURL().openConnection();
        // conn.setDoOutput( hasPayload );
        // conn.setRequestMethod( method );
        for ( Map.Entry<String, Object> entry : headers.entrySet() )
        {
            requestBuilder.setHeader( entry.getKey(), entry.getValue().toString() );
            // conn.setRequestProperty( entry.getKey(), entry.getValue().toString() );
        }
        requestBuilder = api.filter( requestBuilder );
        writeEntity( requestBuilder );

        requestBuilder.setCallback( new RequestCallback() {

            @Override
            public void onResponseReceived( com.google.gwt.http.client.Request request, Response response )
            {
                try
                {
                    callback.onSuccess( decodeResponse( response ) );
                }
                catch ( StatusCodeException e )
                {
                    callback.onFailure( e );
                }
            }

            @Override
            public void onError( com.google.gwt.http.client.Request request, Throwable exception )
            {
                callback.onFailure( exception );
            }
        } );

        try
        {
            requestBuilder.send();
        }
        catch ( RequestException e )
        {
            callback.onFailure( e );
        }

        return as();
    }

    public Object getEntity()
    {
        return entity;
    }

    @Override
    public R header( String name, Object value )
    {
        if ( headers.isEmpty() )
        {
            headers = FlatPackCollections.mapForIteration();
        }
        headers.put( name, value );
        return as();
    }

    @Override
    public R queryParameter( String name, Object value )
    {
        if ( queryParams.isEmpty() )
        {
            queryParams = FlatPackCollections.mapForIteration();
        }
        queryParams.put( name, value );
        return as();
    }

    public R setEntity( S entity )
    {
        this.entity = entity;
        return as();
    }

    @SuppressWarnings( "unchecked" )
    protected R as()
    {
        return (R) this;
    }

    protected abstract X decodeResponse( Response response )
        throws StatusCodeException;

    protected ApiBase getApi()
    {
        return api;
    }

    /**
     * Returns {@code true} for a 2XX series response code.
     */
    protected boolean isOk( int statusCode )
    {
        return statusCode >= 200 && statusCode < 300;
    }

    protected abstract void writeEntity( RequestBuilder requestBuilder );
}