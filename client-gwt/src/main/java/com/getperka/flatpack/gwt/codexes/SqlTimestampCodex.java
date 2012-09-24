package com.getperka.flatpack.gwt.codexes;

import java.sql.Timestamp;

public class SqlTimestampCodex
    extends AbstractDateCodex<Timestamp>
{

    @Override
    protected Timestamp readAsString( String element )
    {
        return new Timestamp( DATE_FORMAT.parseStrict( element ).getTime() );
    }

    @Override
    protected Timestamp readAsDouble( Double element )
    {
        return new Timestamp( element.longValue() );
    }

}
