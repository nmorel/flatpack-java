package com.getperka.flatpack.gwt.codexes;

import java.util.Date;

public class DateCodex
    extends AbstractDateCodex<Date>
{

    @Override
    protected Date readAsString( String element )
    {
        return DATE_FORMAT.parseStrict( element );
    }

    @Override
    protected Date readAsDouble( Double element )
    {
        return new Date( element.longValue() );
    }

}
