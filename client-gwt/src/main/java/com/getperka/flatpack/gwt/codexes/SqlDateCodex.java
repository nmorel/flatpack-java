package com.getperka.flatpack.gwt.codexes;

import java.sql.Date;

public class SqlDateCodex
    extends AbstractDateCodex<Date>
{

    @Override
    protected Date readAsString( String element )
    {
        return new Date( DATE_FORMAT.parseStrict( element ).getTime() );
    }

    @Override
    protected Date readAsDouble( Double element )
    {
        return new Date( element.longValue() );
    }

}
