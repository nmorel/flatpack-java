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
package com.getperka.flatpack.gwt.ext;

import java.util.Comparator;
import java.util.UUID;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.gwt.codexes.Codex;

/**
 * An immutable view of a property that should be serialized.
 */
public abstract class Property<T, V>
    extends BaseHasUuid
{

    /**
     * Sorts Property objects by {@link #getName()}.
     */
    public static final Comparator<Property<?, ?>> PROPERTY_NAME_COMPARATOR = new Comparator<Property<?, ?>>() {
        @Override
        public int compare( Property<?, ?> o1, Property<?, ?> o2 )
        {
            return o1.getName().compareTo( o2.getName() );
        }
    };

    private Codex<V> codex;
    private boolean deepTraversalOnly;
    /**
     * This property is mutable by external callers. It's kind of a hack to allow the describe endpoint to lazily add
     * the doc strings.
     */
    private String docString;
    private String enclosingTypeName;
    private boolean embedded;
    private String name;
    private boolean suppressDefaultValue;
    private boolean primitive;

    public Property( String name, Codex<V> codex )
    {
        this( name, codex, false );
    }

    public Property( String name, Codex<V> codex, boolean primitive )
    {
        this.name = name;
        this.codex = codex;
        this.primitive = primitive;
    }

    public abstract V getValue( T object );

    public abstract void setValue( T object, V value );

    public Codex<V> getCodex()
    {
        return codex;
    }

    public String getDocString()
    {
        return docString;
    }

    /**
     * The payload name of the type that defines the property.
     */
    public String getEnclosingTypeName()
    {
        return enclosingTypeName;
    }

    /**
     * Returns the json payload name of the Property, which may differ from the bean name if a {@link JsonProperty}
     * annotation has been applied to the getter.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns {@code true} if the Property should be included only during a deep traversal.
     */
    public boolean isDeepTraversalOnly()
    {
        return deepTraversalOnly;
    }

    /**
     * Returns {@code true} if an entity Property's properties should be emitted into the owning entity's properties.
     */
    public boolean isEmbedded()
    {
        return embedded;
    }

    /**
     * If {@code true}, non-null properties that contain the property type's default value will not be serialized. For
     * example, integer properties whose values are {@code 0} will not be serialized.
     */
    public boolean isSuppressDefaultValue()
    {
        return suppressDefaultValue;
    }

    public void setDocString( String docString )
    {
        this.docString = docString;
    }

    /**
     * For debugging use only.
     */
    @Override
    public String toString()
    {
        return getEnclosingTypeName() + "." + getName();
    }

    @Override
    protected UUID defaultUuid()
    {
        return super.defaultUuid();
        // TODO won't work in GWT
        // return UUID.nameUUIDFromBytes((getEnclosingTypeName() + "." + getName()).getBytes(UTF8));
    }

    public void setDeepTraversalOnly( boolean deepTraversalOnly )
    {
        this.deepTraversalOnly = deepTraversalOnly;
    }

    public void setEmbedded( boolean embedded )
    {
        this.embedded = embedded;
    }

    public void setEnclosingTypeName( String enclosingTypeName )
    {
        this.enclosingTypeName = enclosingTypeName;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setCodex( Codex<V> codex )
    {
        this.codex = codex;
    }

    public void setSuppressDefaultValue( boolean suppressDefaultValue )
    {
        this.suppressDefaultValue = suppressDefaultValue;
    }

    public boolean isPrimitive()
    {
        return primitive;
    }

    public void setPrimitive( boolean primitive )
    {
        this.primitive = primitive;
    }
}
