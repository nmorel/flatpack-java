package com.getperka.flatpack.gwt.codexes;

import java.sql.Time;

public class SqlTimeCodex
    extends AbstractDateCodex<Time>
{

    @Override
    protected Time readAsString( String element )
    {
        return new Time( DATE_FORMAT.parseStrict( element ).getTime() );
    }

    @Override
    protected Time readAsDouble( Double element )
    {
        return new Time( element.longValue() );
    }

}
