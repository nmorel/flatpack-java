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
package com.getperka.flatpack.ext;

/**
 * Indicates how certain simple {@link JsonKind} types should be interpreted as more complex values.
 * <p>
 * <b>GWT Implementation : removed the static method. It is only used in consultation for the ApiDescription.</b>
 * </p>
 */
public class TypeHint
{

    private final String description;

    public TypeHint( String description )
    {
        this.description = description;
    }

    public String getValue()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return description;
    }

}
