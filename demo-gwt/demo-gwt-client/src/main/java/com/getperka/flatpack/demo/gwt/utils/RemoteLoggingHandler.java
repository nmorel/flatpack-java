package com.getperka.flatpack.demo.gwt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.logging.client.JsonLogRecordClientUtil;
import com.google.gwt.logging.client.RemoteLogHandlerBase;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

public class RemoteLoggingHandler
    extends RemoteLogHandlerBase
{
    /**
     * The list of pending records
     */
    private List<LogRecord> pendingRecords;

    /**
     * The command used to resolve the pending log records.
     */
    private ScheduledCommand pendingCommand;

    public RemoteLoggingHandler( Level level, List<String> ignoredLoggerNames )
    {
        super( ignoredLoggerNames );
        setLevel( level );
        this.pendingRecords = new ArrayList<LogRecord>();
    }

    @Override
    public void publish( LogRecord record )
    {
        if ( !isLoggable( record ) )
        {
            return;
        }

        pendingRecords.add( record );
        /*
         * Schedule a command to send the pending log records.
         */
        if ( null == pendingCommand )
        {
            pendingCommand = new ScheduledCommand() {
                @Override
                public void execute()
                {
                    pendingCommand = null;
                    sendRecords();
                }
            };
            Scheduler.get().scheduleDeferred( pendingCommand );
        }
    }

    private void sendRecords()
    {
        JSONArray array = new JSONArray();
        int i = 0;
        for ( LogRecord record : pendingRecords )
        {
            array.set( i++, new JSONString( JsonLogRecordClientUtil.logRecordAsJson( record ) ) );
        }

        RequestBuilder requestBuilder = new RequestBuilder( RequestBuilder.POST, "/gwtLogging" );
        requestBuilder.setHeader( "Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8 );
        try
        {
            requestBuilder.sendRequest( array.toString(), new RequestCallback() {

                @Override
                public void onResponseReceived( Request request, Response response )
                {
                }

                @Override
                public void onError( Request request, Throwable exception )
                {
                }
            } );
        }
        catch ( RequestException e )
        {
            e.printStackTrace();
        }
    }

}
