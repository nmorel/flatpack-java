package com.getperka.flatpack.demo.gwt.mvp;

import java.util.Map;

/**
 * Places with parameters should extend this class
 *
 * @author Nicolas Morel
 */
public interface PlaceWithParameters
{
    public static class MissingParametersException
        extends Exception
    {
        private static final long serialVersionUID = -4258407607343953704L;

        private String[] missingParameters;

        public MissingParametersException( String... missingParameters )
        {
            this.missingParameters = missingParameters;
        }

        public String[] getMissingParameters()
        {
            return missingParameters;
        }
    }

    /**
     * Return the place's parameters in their string representation
     *
     * @return the place's parameters in their string representation
     */
    Map<String, String> getParameters();

    /**
     * Give to the place their parameters in their string representation
     *
     * @param parameters parameters in their string representation
     * @throws MissingParametersException If the place requires some parameters that are not presents
     */
    void setParameters( Map<String, String> parameters )
        throws MissingParametersException;
}
