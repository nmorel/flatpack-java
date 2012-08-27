package com.getperka.flatpack.fast.gwt;

import com.getperka.flatpack.ext.Property;

public class PropertyCodexDescription
{
    private Property property;

    private CodexDescription codex;

    public PropertyCodexDescription( Property property )
    {
        this.property = property;
        this.codex = new CodexDescription( property.getType() );
    }

    public Property getProperty()
    {
        return property;
    }

    public void setProperty( Property property )
    {
        this.property = property;
    }

    public CodexDescription getCodex()
    {
        return codex;
    }

    public void setCodex( CodexDescription codex )
    {
        this.codex = codex;
    }

}
