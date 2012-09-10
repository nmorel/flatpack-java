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

import com.getperka.flatpack.gwt.FlatPackEntity;

/**
 * A subtype of Exception which conveys the status code for a failed request. If the return payload contained a
 * {@link FlatPackEntity}, it can be retrieved and inspected for errors.
 */
public class StatusCodeException
    extends Exception
{
    private static final long serialVersionUID = 2412978878192760667L;

    private FlatPackEntity<?> entity;
    private String response;
    private final int statusCode;

    public StatusCodeException( int statusCode )
    {
        super( "Status code " + statusCode );
        this.statusCode = statusCode;
    }

    public StatusCodeException( int statusCode, Throwable cause )
    {
        super( "Status code " + statusCode, cause );
        this.statusCode = statusCode;
    }

    public FlatPackEntity<?> getEntity()
    {
        return entity;
    }

    public String getResponse()
    {
        return response;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setEntity( FlatPackEntity<?> entity )
    {
        this.entity = entity;
    }

    public void setResponse( String response )
    {
        this.response = response;
    }
}
