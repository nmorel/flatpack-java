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

import java.util.logging.Logger;

/**
 * A base interface for generated FlatPack API accessors.
 */
public interface Api
{

    /**
     * Retrieve the base URI used to access the server.
     */
    String getServerBase();

    /**
     * Set the base URI used to access the server.
     */
    void setServerBase( String serverBase );

    /**
     * Enable verbose output via a {@link Logger}.
     */
    void setVerbose( boolean verbose );

}