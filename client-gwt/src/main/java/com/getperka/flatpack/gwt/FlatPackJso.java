package com.getperka.flatpack.gwt;

import com.google.gwt.core.client.JavaScriptObject;

public class FlatPackJso
    extends JavaScriptObject
{
    protected FlatPackJso()
    {
    }

    public final native <T extends JavaScriptObject> T getData()
    /*-{
        return this.data;
    }-*/;

    public final native <T extends JavaScriptObject> T getValue()
    /*-{
        return this.value;
    }-*/;

    public final native <T extends JavaScriptObject> T getErrors()
    /*-{
        return this.errors;
    }-*/;

    public final native <T extends JavaScriptObject> T getWarnings()
    /*-{
        return this.warnings;
    }-*/;
}
