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
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.getperka.flatpack.gwt.client.Api;
import com.getperka.flatpack.gwt.client.FlatBack;
import com.getperka.flatpack.gwt.client.Request;
import com.getperka.flatpack.gwt.client.StatusCodeException;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

abstract class RequestBase<R extends Request<R, S, X>, S, X>
    implements Request<R, S, X>
{
    private final Object[] args;
    private S entity;
    private Map<String, Object> headers = Collections.emptyMap();
    private final Method method;
    private final String path;
    private Map<String, Object> queryParams = Collections.emptyMap();

    protected RequestBase( Method method, String path, Object... args )
    {
        this.args = args;
        this.method = method;
        this.path = path;
    }

    @Override
    public R execute( final FlatBack<X> callback )
    {
        RequestBuilder requestBuilder = new RequestBuilder( method, buildRequestUrl() );
        requestBuilder.setHeader( "Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8 );

        for ( Map.Entry<String, Object> entry : headers.entrySet() )
        {
            requestBuilder.setHeader( entry.getKey(), entry.getValue().toString() );
        }
        requestBuilder = getApi().filter( requestBuilder );
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
                    if ( null != e.getEntity() )
                    {
                        Set<ConstraintViolation<?>> violations = e.getEntity().getConstraintViolations();
                        if ( null != violations && !violations.isEmpty() )
                        {
                            callback.onConstraintViolation( violations );
                        }
                        else
                        {
                            callback.onFailure( e );
                        }
                    }
                    else
                    {
                        callback.onFailure( e );
                    }
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

    /**
     * Build the request url
     *
     * @return the built url
     */
    protected String buildRequestUrl()
    {
        StringBuilder sb = new StringBuilder( null == getApi().getServerBase() ? "" : getApi().getServerBase() );

        // Replace arguments
        replaceArgs( path, sb );

        // Now add query parameters
        addQueryParameters( sb );

        return sb.toString();
    }

    /**
     * Replace the path arguments
     *
     * @param path
     * @return the path with replaced arguments
     */
    protected void replaceArgs( String path, StringBuilder sb )
    {
        if ( null == args || args.length == 0 )
        {
            sb.append( path );
            return;
        }

        RegExp regExp = RegExp.compile( "[{][^}]*[}]", "g" );

        int argsIndex = 0;
        int fromIndex = 0;
        int length = path.length();
        MatchResult result;

        while ( fromIndex < length && argsIndex < args.length )
        {
            // Find the next match
            result = regExp.exec( path );
            if ( result == null )
            {
                // No more matches
                break;
            }
            int index = result.getIndex();
            String match = result.getGroup( 0 );

            // Append the characters leading up to the match
            sb.append( path.substring( fromIndex, index ) );
            // Append the argument
            sb.append( args[argsIndex++] );

            // Skip past the matched string
            fromIndex = index + match.length();
            regExp.setLastIndex( fromIndex );
        }

        // Append the tail of the string
        if ( fromIndex < length )
        {
            sb.append( path.substring( fromIndex ) );
        }
    }

    protected void addQueryParameters( StringBuilder sb )
    {
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
                sb.append( entry.getKey() ).append( "=" ).append( URL.encodeQueryString( entry.getValue().toString() ) );
            }
        }
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

    protected abstract Api getApi();

    /**
     * Returns {@code true} for a 2XX series response code.
     */
    protected boolean isOk( int statusCode )
    {
        return statusCode >= 200 && statusCode < 300;
    }

    protected abstract void writeEntity( RequestBuilder requestBuilder );
}