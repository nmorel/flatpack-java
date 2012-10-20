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

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.gwt.codexes.BooleanCodex;
import com.getperka.flatpack.gwt.codexes.EntityCodex;
import com.getperka.flatpack.gwt.ext.Property;

/**
 * A carrier object to associate out-of-band data with serialized entities. The UUID of this object corresponds to the
 * UUID of the entity it describes.
 */
class EntityMetadata
    extends BaseHasUuid
{

    private static EntityMetadataCodex CODEX_INSTANCE;

    static EntityMetadataCodex getCodex()
    {
        if ( null == CODEX_INSTANCE )
        {
            CODEX_INSTANCE = new EntityMetadataCodex();
            CODEX_INSTANCE.init();
        }
        return CODEX_INSTANCE;
    }

    static class EntityMetadataCodex
        extends EntityCodex<EntityMetadata>
    {

        @Override
        protected void initProperties( List<Property<EntityMetadata, ?>> properties )
        {
            properties.add( new Property<EntityMetadata, Boolean>( "persistent", new BooleanCodex() ) {

                @Override
                public Boolean getValue( EntityMetadata object )
                {
                    return object.isPersistent();
                }

                @Override
                public void setValue( EntityMetadata object, Boolean value )
                {
                    object.setPersistent( value );
                }
            } );
        }

        @Override
        public String getName()
        {
            return "metadata";
        }

        @Override
        protected EntityMetadata createInstance()
        {
            return new EntityMetadata();
        }

    }

    private boolean isPersistent;

    public boolean isPersistent()
    {
        return isPersistent;
    }

    public void setPersistent( boolean isPersistent )
    {
        this.isPersistent = isPersistent;
    }
}
