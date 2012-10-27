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
package com.getperka.flatpack.gwt;

import java.util.List;

import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * The main entry-point to the FlatPack API. This type exists to provide a central point for configuring instances of
 * {@link Packer} and {@link Unpacker}.
 */
public class FlatPack
{

    public static class Builder
    {
        private final TypeContext typeContext;
        private boolean ignoreUnresolvableTypes = false;
        private boolean prettyPrint;
        private boolean verbose;
        private List<EntityResolver> resolvers = FlatPackCollections.listForAny();

        private Builder( TypeContext typeContext )
        {
            assert null != typeContext : "typeContext can't be null";
            this.typeContext = typeContext;
        }

        /**
         * Add an additional {@link EntityResolver} which will be queried before any previously-added resolvers.
         */
        public Builder addEntityResolver( EntityResolver resolver )
        {
            resolvers.add( 0, resolver );
            return this;
        }

        public Builder withIgnoreUnresolvableTypes( boolean ignore )
        {
            this.ignoreUnresolvableTypes = ignore;
            return this;
        }

        public Builder withPrettyPrint( boolean prettyPrint )
        {
            this.prettyPrint = prettyPrint;
            return this;
        }

        public Builder withVerbose( boolean verbose )
        {
            this.verbose = verbose;
            return this;
        }

        public FlatPack create()
        {
            FlatPack flatPack = new FlatPack();
            flatPack.typeContext = typeContext;
            flatPack.packer = new Packer( typeContext, prettyPrint, verbose );
            flatPack.unpacker = new Unpacker( typeContext, ignoreUnresolvableTypes, verbose, resolvers );
            return flatPack;
        }
    }

    public static Builder builder( TypeContext typeContext )
    {
        return new Builder( typeContext );
    }

    private TypeContext typeContext;
    private Packer packer;
    private Unpacker unpacker;

    private FlatPack()
    {
    }

    /**
     * Returns a configured instance of {@link TypeContext}.
     */
    public TypeContext getTypeContext()
    {
        return typeContext;
    }

    /**
     * Returns a configured instance of {@link Packer}.
     */
    public Packer getPacker()
    {
        return packer;
    }

    /**
     * Returns a configured instance of {@link Unpacker}.
     */
    public Unpacker getUnpacker()
    {
        return unpacker;
    }
}
