package com.getperka.flatpack.demo.gwt.ui.widget;

import java.math.BigDecimal;
import java.text.ParseException;

import com.google.gwt.dom.client.Document;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBox;

public class BigDecimalBox
    extends ValueBox<BigDecimal>
{
    private static class BigDecimalRenderer
        extends AbstractRenderer<BigDecimal>
    {
        private static BigDecimalRenderer INSTANCE;

        /**
         * Returns the instance.
         */
        public static Renderer<BigDecimal> instance()
        {
            if ( INSTANCE == null )
            {
                INSTANCE = new BigDecimalRenderer();
            }
            return INSTANCE;
        }

        protected BigDecimalRenderer()
        {
        }

        public String render( BigDecimal object )
        {
            if ( null == object )
            {
                return "";
            }

            return object.toString();
        }
    }

    private static class BigDecimalParser
        implements Parser<BigDecimal>
    {

        private static BigDecimalParser INSTANCE;

        /**
         * Returns the instance of the no-op renderer.
         */
        public static Parser<BigDecimal> instance()
        {
            if ( INSTANCE == null )
            {
                INSTANCE = new BigDecimalParser();
            }
            return INSTANCE;
        }

        protected BigDecimalParser()
        {
        }

        public BigDecimal parse( CharSequence object )
            throws ParseException
        {
            if ( "".equals( object.toString() ) )
            {
                return null;
            }

            try
            {
                return new BigDecimal( object.toString() );
            }
            catch ( NumberFormatException e )
            {
                throw new ParseException( e.getMessage(), 0 );
            }
        }
    }

    protected BigDecimalBox()
    {
        super( Document.get().createTextInputElement(), BigDecimalRenderer.instance(), BigDecimalParser.instance() );
    }

}
