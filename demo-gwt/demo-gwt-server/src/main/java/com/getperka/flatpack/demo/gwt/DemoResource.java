/*
 * #%L
 * FlatPack Demonstration Server
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack.demo.gwt;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Providers;

import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.jersey.ApiDescriber;
import com.getperka.flatpack.jersey.FlatPackResponse;

/**
 * In jax-rs parlance, a Resource contains mappings from HTTP paths to individual methods. The container will process
 * the incoming request and invoke a resource method.
 */
@Path( "/" )
@Produces( MediaType.APPLICATION_JSON )
public class DemoResource
{
    private final FakeDatabase db;

    /**
     * Injected by the container and provides access to providers set up by {@link DemoApplication}.
     */
    @Context
    Providers providers;

    public DemoResource( FakeDatabase db )
    {
        this.db = db;
    }

    /**
     * Provide a description of the entities and service methods defined by the web service. Providing an endpoint like
     * this is optional, but allows you to use FastTool to automatically generate client access libraries.
     */
    @GET
    @Path( "describe" )
    @FlatPackResponse( ApiDescription.class )
    public ApiDescription describeGet()
        throws IOException
    {
        FlatPack flatpack =
            providers.getContextResolver( FlatPack.class, MediaType.WILDCARD_TYPE ).getContext( FlatPack.class );
        ApiDescriber api = new ApiDescriber( flatpack, Arrays.asList( DemoResource.class.getMethods() ) );
        return api.describe();
    }

    /**
     * This is a simple endpoint just to show a basic jax-rs request endpoint. The method name is irrelevant, all
     * mapping information is derived from the method annotation {@link GET} and the {@link Path} annotation. Because
     * multiple methods (e.g. {@link POST}, {@link PUT}) may be bound to the same path, but with different methods or
     * media types, a suggested coding style is to add the discriminators to the end of a descriptive method name.
     *
     * @param name inserted into the return payload
     */
    @GET
    @Path( "hello" )
    @Produces( MediaType.TEXT_PLAIN )
    public String helloWorldGet( @QueryParam( "name" ) @DefaultValue( "World" ) String name )
    {
        return "Hello " + name + "!";
    }

    /**
     * Return the list of products.
     */
    @GET
    @Path( "products" )
    @FlatPackResponse( { List.class, Product.class } )
    public List<Product> productsGet()
    {
        return db.get( Product.class );
    }

    /**
     * Stores or updates Products.
     * <p>
     * This method demonstrates how ConstraintViolations can be presented to clients in a uniform manner. A more
     * complete mechanism for handling escaping {@link ConstraintViolationException} in a jax-rs container would be to
     * install an {@link ExceptionMapper} to unwrap the exception and report the violations.
     */
    @PUT
    @Path( "products" )
    @FlatPackResponse( Void.class )
    public Response productsPut( List<Product> products )
    {
        FlatPackEntity<Void> toReturn = FlatPackEntity.nullResponse();
        Status status = Status.CREATED;

        /*
         * Call the JSR 303 validator on the entity. In a real system, this might normally be part of the database
         * flush.
         */
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for ( Product product : products )
        {
            Set<ConstraintViolation<Product>> violations = validator.validate( product );
            if ( violations.isEmpty() )
            {
                db.persist( product );
            }
            else
            {
                status = Status.BAD_REQUEST;
                toReturn.addConstraintViolations( violations );
            }
        }

        /*
         * Full control over the response value is possible by explicitly building a Response object. In this case, we
         * want to return a 201 status code and an empty return value. If we were using a JSR-303 Validator, any
         * ConstraintViolations could be added to the FlatPackEntity by calling addConstraintViolations(), which will
         * map the violations into the errors segment of the response.
         */
        return Response.status( status ).entity( toReturn ).build();
    }

    /**
     * Destroy all information in the {@link FakeDatabase} so this resource can be used by tests.
     */
    @POST
    @Path( "reset" )
    public void reset()
    {
        db.clear();
    }

    /**
     * Return a map of products.
     */
    @GET
    @Path( "map" )
    @FlatPackResponse( { Map.class, Product.class, Product.class } )
    public Map<Product, Product> mapGet()
    {
        Map<Product, Product> map = new HashMap<Product, Product>();
        for ( Product product : db.get( Product.class ) )
        {
            map.put( product, product );
        }
        return map;
    }
}
