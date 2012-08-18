package com.getperka.flatpack.gwt;

import java.util.Date;

public class Test
{
    public static void main( String[] args )
    {
        Date date = new Date( new Date( 112, 7, 18, 15, 45, 56 ).getTime() + 543 );
        System.out.println( date.toString() );
        System.out.println( new Date( new Date().toString() ) );
    }
}
