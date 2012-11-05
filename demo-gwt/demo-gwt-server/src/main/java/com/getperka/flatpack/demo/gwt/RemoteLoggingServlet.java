package com.getperka.flatpack.demo.gwt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gwt.logging.server.RemoteLoggingServiceUtil;
import com.google.gwt.logging.server.RemoteLoggingServiceUtil.RemoteLoggingException;
import com.google.gwt.logging.server.StackTraceDeobfuscator;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

/**
 * Custom implementation of a remote logging servlet which takes an array of log as input.
 * 
 * @author Nicolas Morel
 */
public class RemoteLoggingServlet
    extends HttpServlet
{
    private static final long serialVersionUID = -8859813580906355073L;

    private static final Logger logger = LoggerFactory.getLogger( RemoteLoggingServlet.class );

    private static final String STRONG_NAME_HEADER = "X-GWT-Permutation";

    @Inject
    private StackTraceDeobfuscator deobfuscator;

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {
        /*
         * if the header does not exist, we pass null, which is handled gracefully by the deobfuscation code.
         */
        HttpServletRequest threadLocalRequest = RequestFactoryServlet.getThreadLocalRequest();
        String strongName = null;
        if ( threadLocalRequest != null )
        {
            // can be null during tests
            strongName = threadLocalRequest.getHeader( STRONG_NAME_HEADER );
        }

        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse( new InputStreamReader( req.getInputStream(), Charsets.UTF_8 ) );
        JsonArray array = obj.getAsJsonArray();
        Iterator<JsonElement> iterator = array.iterator();
        String jsonLog;
        while ( iterator.hasNext() )
        {
            jsonLog = iterator.next().getAsString();
            try
            {
                RemoteLoggingServiceUtil.logOnServer( jsonLog, strongName, deobfuscator, "ClientLogger" );
            }
            catch ( RemoteLoggingException e )
            {
                logger.warn( "Error while trying to log the following client log : '{}'", jsonLog, e );
            }
        }
    }
}
