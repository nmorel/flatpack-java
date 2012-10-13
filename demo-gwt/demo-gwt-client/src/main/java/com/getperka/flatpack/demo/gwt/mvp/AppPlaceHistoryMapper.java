package com.getperka.flatpack.demo.gwt.mvp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.getperka.flatpack.demo.gwt.mvp.PlaceWithParameters.MissingParametersException;
import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

/**
 * @author Nicolas Morel
 */
public class AppPlaceHistoryMapper
    implements PlaceHistoryMapper
{
    public static final String SEPARATOR_TOKEN_PARAMETERS = ":";

    public static final String SEPARATOR_PARAMETERS = "&";

    public static final String SEPARATOR_PARAMETER_ID_VALUE = "=";

    private static final Logger logger = Logger.getLogger( "AppPlaceHistoryMapper" );

    @Override
    public Place getPlace( String token )
    {
        if ( null == token || "".equals( token ) )
        {
            // it will show the default place
            return null;
        }

        TokenEnum tokenEnum = TokenEnum.fromToken( token );
        if ( null == tokenEnum )
        {
            logger.warning( "No corresponding place found for the token " + token );
            return new ErrorPlace( "Page not found" );
        }

        BasePlace place = tokenEnum.createNewPlace();
        PlaceWithParameters placeWithParameters = place.hasParameters();

        // if the place has parameters, retrieving them from token
        if ( null != placeWithParameters )
        {
            int index = token.indexOf( SEPARATOR_TOKEN_PARAMETERS );
            if ( index != -1 )
            {
                String[] parameters = token.substring( index + 1 ).split( SEPARATOR_PARAMETERS );
                Map<String, String> mapParameters = new HashMap<String, String>( parameters.length );
                for ( String parameter : parameters )
                {
                    String[] paramIdValue = parameter.split( SEPARATOR_PARAMETER_ID_VALUE );
                    mapParameters.put( paramIdValue[0], paramIdValue[1] );
                }
                try
                {
                    placeWithParameters.setParameters( mapParameters );
                }
                catch ( MissingParametersException e )
                {
                    String message = "Attempted to access a place with missing parameters : " + e.getMissingParameters();
                    logger.warning( message );
                    return new ErrorPlace( message );
                }
            }
        }

        return place;
    }

    @Override
    public String getToken( Place place )
    {
        assert place instanceof BasePlace : "Only VisitorPlace are managed";

        BasePlace vPlace = (BasePlace) place;
        String token = vPlace.getToken().token();
        PlaceWithParameters placeWithParameters = vPlace.hasParameters();

        if ( null != placeWithParameters )
        {
            Map<String, String> parameters = placeWithParameters.getParameters();
            if ( null != parameters && !parameters.isEmpty() )
            {
                StringBuilder tokenBuilder = new StringBuilder( token );
                tokenBuilder.append( SEPARATOR_TOKEN_PARAMETERS );
                boolean first = true;
                for ( Entry<String, String> parameter : parameters.entrySet() )
                {
                    if ( !first )
                    {
                        tokenBuilder.append( SEPARATOR_PARAMETERS );
                    }
                    tokenBuilder.append( parameter.getKey() );
                    tokenBuilder.append( SEPARATOR_PARAMETER_ID_VALUE );
                    tokenBuilder.append( parameter.getValue() );
                    first = false;
                }
                token = tokenBuilder.toString();
            }
        }
        return token;
    }
}
