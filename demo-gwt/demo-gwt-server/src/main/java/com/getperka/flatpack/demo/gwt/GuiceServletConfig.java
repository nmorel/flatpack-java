package com.getperka.flatpack.demo.gwt;

import javax.servlet.ServletContextEvent;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.demo.gwt.persistence.Database;
import com.getperka.flatpack.demo.gwt.persistence.DatabaseResolver;
import com.getperka.flatpack.demo.gwt.persistence.TrueDatabase;
import com.getperka.flatpack.demo.gwt.resources.DemoResource;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.jersey.FlatPackProvider;
import com.getperka.flatpack.jersey.FlatPackResolver;
import com.getperka.flatpack.search.SearchTypeSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class GuiceServletConfig
    extends GuiceServletContextListener
{

    @Override
    public void contextInitialized( ServletContextEvent servletContextEvent )
    {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        super.contextInitialized( servletContextEvent );
    }

    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector( new JerseyServletModule() {
            protected void configureServlets()
            {
                bind( DemoResource.class );

                bind( Database.class ).to( TrueDatabase.class ).in( Scopes.SINGLETON );
                bind( EntityResolver.class ).to( DatabaseResolver.class ).in( Scopes.SINGLETON );

                bind( FlatPackProvider.class ).in( Scopes.SINGLETON );
                bind( RollbackExceptionMapper.class ).in( Scopes.SINGLETON );

                serve( "/resources/*" ).with( GuiceContainer.class );

                // Persistence
                install( new JpaPersistModule( "demoDb" ) );
                filter( "/resources/*" ).through( PersistFilter.class );
            };

            @Provides
            @Singleton
            public FlatPackResolver flatPackResolver( EntityResolver entityResolver )
            {
                Configuration configuration =
                    new Configuration()
                        /*
                         * The EntityResolver is optional and is used when FlatPack deserializes a payload to retrieve a
                         * persistent entity to which properties in the incoming payload will be applied. Implementors
                         * that do not that need to manually merge incoming DTOs with persistent state may omit this
                         * configuration.
                         */
                        .addEntityResolver( entityResolver )
                        /*
                         * At least one TypeSource is required. The TypeSources associated with a FlatPack stack
                         * determine the complete set of entity types that can be processed. This SearchTypeSource scans
                         * the classpath for HasUuid subtypes.
                         */
                        .addTypeSource(
                            new SearchTypeSource( "com.getperka.flatpack.client.dto", "com.getperka.flatpack.ext",
                                "com.getperka.flatpack.demo.gwt.model" ) ).withPrettyPrint( true )
                /*
                 * A PrincipalMapper is optional and, if present, enables the use principal-based property access
                 * restrictions whereby certain Principals are allowed to mutate only specific entities. This can be
                 * used, for example, to ensure that a Customer can only mutate properties that have an
                 * @InheritPrincipal chain back to the Customer.
                 */
                /* .withPrincipalMapper( new DemoPrincipalMapper() ) */
                /*
                 * A RoleMapper enables role-based property access, which restricts property getters and setters based
                 * on
                 * @PermitAll / @DenyAll / @RolesAllowed annotations. Beyond just simple security measures, roles can be
                 * used to create sets of properties to reduce payload sizes (e.g. "CustomerSummary" vs.
                 * "CustomerDetail").
                 */
                /* .withRoleMapper( new DemoRoleMapper() ) */;
                // The FlatPackResolver makes a FlatPack instance available through the Resources interface
                return new FlatPackResolver( configuration );
            }

        } );
    }

}
