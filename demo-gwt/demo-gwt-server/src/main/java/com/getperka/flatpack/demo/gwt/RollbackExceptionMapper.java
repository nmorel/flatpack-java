package com.getperka.flatpack.demo.gwt;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.getperka.flatpack.FlatPackEntity;

@Provider
public class RollbackExceptionMapper
    implements ExceptionMapper<RollbackException>
{

    @Override
    public Response toResponse( RollbackException exception )
    {
        if ( exception.getCause() instanceof ConstraintViolationException )
        {
            FlatPackEntity<Void> toReturn = FlatPackEntity.nullResponse();
            toReturn.addConstraintViolations( ( (ConstraintViolationException) exception.getCause() )
                .getConstraintViolations() );
            return Response.status( Status.BAD_REQUEST ).entity( toReturn ).build();
        }
        else
        {
            return Response.status( Status.INTERNAL_SERVER_ERROR ).entity( exception.getCause().getMessage() ).build();
        }
    }
}
