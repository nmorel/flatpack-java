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
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A pass-through codex to allow raw access to JSON payloads.
 */
public class JsonElementCodex
    extends ValueCodex<JavaScriptObject>
{
    public JsonElementCodex()
    {
    }

    @Override
    public JavaScriptObject readNotNull( Object element, DeserializationContext context )
    {
        if ( !( element instanceof JavaScriptObject ) )
        {
            throw new IllegalArgumentException( "element is not a JavaScriptObject : " + element.getClass().getName() );
        }
        return (JavaScriptObject) element;
    }

    @Override
    public void writeNotNull( JavaScriptObject object, SerializationContext context )
    {
        context.getWriter().getOut().append( stringify( object ) );
    }

    private final native String stringify( JavaScriptObject object )
    /*-{
		return JSON.stringify(object);
    }-*/;
}