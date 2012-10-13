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
package com.getperka.flatpack.demo.gwt.resources;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import com.getperka.flatpack.demo.gwt.model.ChildBean;
import com.getperka.flatpack.demo.gwt.model.MultiplePropertiesBean;
import com.getperka.flatpack.demo.gwt.model.Product;
import com.getperka.flatpack.demo.gwt.model.TestEnum;
import com.getperka.flatpack.demo.gwt.persistence.Database;
import com.getperka.flatpack.demo.gwt.persistence.FakeDatabase;
import com.getperka.flatpack.jersey.ApiDescriber;
import com.getperka.flatpack.jersey.FlatPackResponse;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * In jax-rs parlance, a Resource contains mappings from HTTP paths to individual methods. The container will process
 * the incoming request and invoke a resource method.
 */
@Path( "/" )
@Produces( MediaType.APPLICATION_JSON )
public class DemoResource
{
    private final Database db;

    /**
     * Injected by the container and provides access to providers set up by {@link DemoApplication}.
     */
    @Context
    Providers providers;

    @Inject
    public DemoResource( Database db )
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
    @Transactional
    public List<Product> products()
    {
        return db.get( Product.class );
    }

    /**
     * Return a map of products.
     */
    @GET
    @Path( "products/{id}" )
    @FlatPackResponse( Product.class )
    public Product product( @PathParam( "id" ) String id )
    {
        return db.get( Product.class, id );
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

    /**
     * Return a map of products.
     */
    @SuppressWarnings( "deprecation" )
    @GET
    @Path( "multipleProperties" )
    @FlatPackResponse( MultiplePropertiesBean.class )
    public MultiplePropertiesBean multiplePropertiesGet()
    {
        MultiplePropertiesBean bean = new MultiplePropertiesBean();
        bean.setUuid( UUID.fromString( "15a1bf22-f764-4e4e-abc2-81146df9f54f" ) );
        bean.setString( "toto" );
        bean.setBytePrimitive( new Integer( 34 ).byteValue() );
        bean.setByteBoxed( new Integer( 87 ).byteValue() );
        bean.setShortPrimitive( new Integer( 12 ).shortValue() );
        bean.setShortBoxed( new Integer( 15 ).shortValue() );
        bean.setIntPrimitive( 234 );
        bean.setIntBoxed( 456 );
        bean.setLongPrimitive( 1234l );
        bean.setLongBoxed( 34567l );
        bean.setDoublePrimitive( 126.23 );
        bean.setDoubleBoxed( 1256.98 );
        bean.setFloatPrimitive( 12.89f );
        bean.setFloatBoxed( 67.3f );
        bean.setBigDecimal( new BigDecimal( "12345678987654.456789" ) );
        bean.setBigInteger( new BigInteger( "123456789098765432345678987654" ) );
        bean.setBooleanPrimitive( true );
        bean.setBooleanBoxed( false );
        bean.setEnumProperty( TestEnum.FOUR );
        bean.setUuidProperty( UUID.fromString( "99999999-9999-9999-9999-999999999999" ) );
        bean.setCharPrimitive( '\u00e7' );
        bean.setCharBoxed( '\u00e8' );

        ChildBean child1 = new ChildBean();
        child1.setUuid( UUID.fromString( "4de76f27-2bed-49d3-b624-4b0697cfc53f" ) );
        child1.setChild( "child1" );

        ChildBean child2 = new ChildBean();
        child2.setUuid( UUID.fromString( "e3aa7750-21f0-410c-a228-e48bd1ee6ff9" ) );
        child2.setChild( "child2" );

        bean.setSingleEntity( child2 );
        bean.setListEntity( Arrays.asList( child2, child1 ) );
        bean.setSetEntity( new HashSet<ChildBean>( bean.getListEntity() ) );
        bean.setArrayEntity( new ChildBean[] { child1, child2 } );

        Map<String, ChildBean> mapStringToEntity = new HashMap<String, ChildBean>();
        mapStringToEntity.put( child1.getChild(), child1 );
        mapStringToEntity.put( child2.getChild(), child2 );
        bean.setMapStringToEntity( mapStringToEntity );

        Map<ChildBean, String> mapEntityToString = new HashMap<ChildBean, String>();
        mapEntityToString.put( child1, child1.getChild() );
        mapEntityToString.put( child2, child2.getChild() );
        bean.setMapEntityToString( mapEntityToString );

        bean.setDateJdk( new Date( new Date( 112, 7, 18, 15, 45, 56 ).getTime() + 543 ) );
        bean.setSqlDate( new java.sql.Date( new Date( 112, 7, 18, 15, 45, 56 ).getTime() + 544 ) );
        bean.setSqlTime( new Time( new Date( 112, 7, 18, 15, 45, 56 ).getTime() + 545 ) );
        bean.setSqlTimestamp( new Timestamp( new Date( 112, 7, 18, 15, 45, 56 ).getTime() + 546 ) );
        // bean.setDateJoda( new LocalDateTime( 2011, 3, 14, 21, 56, 23, 996 ) );

        return bean;
    }
}
