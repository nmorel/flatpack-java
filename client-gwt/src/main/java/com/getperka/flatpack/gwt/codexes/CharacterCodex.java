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

import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.SerializationContext;

/**
 * Support for Character.
 */
public class CharacterCodex
    extends ValueCodex<Character>
{
    public CharacterCodex()
    {
    }

    @Override
    public Character readNotNull( Object element, DeserializationContext context )
    {
        if ( !( element instanceof String ) )
        {
            throw new IllegalArgumentException( "element is not a String : " + element.getClass().getName() );
        }
        String string = (String) element;
        if ( string.length() != 1 )
        {
            throw new IllegalArgumentException( "Can't convert the string '" + string + "' to a character" );
        }
        return string.charAt( 0 );
    }

    @Override
    public void writeNotNull( Character object, SerializationContext context )
    {
        context.getWriter().value( object.toString() );
    }
}