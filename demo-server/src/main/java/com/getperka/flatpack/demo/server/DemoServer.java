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
package com.getperka.flatpack.demo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.getperka.flatpack.jersey.FlatPackProvider;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Bootstraps a Jersey environment running on top of Jetty.
 */
public class DemoServer {
  public static void main(String[] args) {
    int port = args.length == 0 ? 8080 : Integer.parseInt(args[0]);
    new DemoServer().start(port);
  }

  public boolean start(int port) {
    // Set up a ServletHolder for the Jersey dispatch logic
    ServletHolder holder = new ServletHolder(new ServletContainer());
    // Set the name of the Application class that the configuration is derived from
    holder.setInitParameter(ServletContainer.APPLICATION_CONFIG_CLASS,
        DemoApplication.class.getName());
    // Add FlatPackProvider as a container filter to enable SecurityContext integration
    holder.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
        FlatPackProvider.class.getName());
    holder.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
        FlatPackProvider.class.getName());

    // Attach the servlet to the Jetty server
    ServletContextHandler context = new ServletContextHandler(null, "/");
    context.addServlet(holder, "/");

    Server server = new Server(port);
    server.setHandler(context);
    try {
      server.start();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
