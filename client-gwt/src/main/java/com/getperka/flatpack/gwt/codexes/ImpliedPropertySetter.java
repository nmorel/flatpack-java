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
package com.getperka.flatpack.gwt.codexes;

import java.util.Collection;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.gwt.ext.Callable;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.Property;

/**
 * The logic that is used to set implied properties. This work is deferred so that collection properties may be mutated
 * after any payload values are set.
 */
@SuppressWarnings( { "unchecked", "rawtypes" } )
class ImpliedPropertySetter
    implements Callable<Void>
{
    private final DeserializationContext context;
    private final Property toSet;
    private final Object target;
    private final Object value;

    public ImpliedPropertySetter( DeserializationContext context, Property toSet, Object target, Object value )
    {
        this.context = context;
        this.toSet = toSet;
        this.target = target;
        this.value = value;
    }

    @Override
    public Void call()
        throws Exception
    {
        if ( toSet.getCodex() instanceof CollectionCodex )
        {
            /*
             * Update implied collections in-place. If the incoming payload had an explicit value for the collection
             * property, it will have been reset to a new collection instance already.
             */
            Collection<Object> collection = (Collection<Object>) toSet.getValue( target );

            // Create a new collection as necessary
            if ( collection == null )
            {
                collection = (Collection<Object>) ( (CollectionCodex<?, ?>) toSet.getCodex() ).newCollection();
                toSet.setValue( target, collection );
                context.addModified( (HasUuid) target, toSet );
            }
            // We can't assume much about the collection's behavior
            if ( !collection.contains( value ) )
            {
                collection.add( value );
            }
        }
        else if ( target instanceof Collection )
        {
            for ( Object element : (Collection<?>) target )
            {
                toSet.setValue( element, value );
            }
        }
        else
        {
            toSet.setValue( target, value );
        }
        return null;
    }
}