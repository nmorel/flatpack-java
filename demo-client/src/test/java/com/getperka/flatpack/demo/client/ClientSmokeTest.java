/*
 * #%L
 * FlatPack Demonstration Client
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
package com.getperka.flatpack.demo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.client.StatusCodeException;
import com.getperka.flatpack.demo.server.DemoServer;

/**
 * Contains a variety of smoke tests to demonstrate various facets of the FlatPack stack.
 */
public class ClientSmokeTest {

  private static final int PORT = 8111;

  /**
   * Spin up an instance of the demo server.
   */
  @BeforeClass
  public static void beforeClass() {
    assertTrue(new DemoServer().start(PORT));
  }

  private ClientApi api;
  private Random random;

  /**
   * Creates an instance of the ClientApi.
   */
  @Before
  public void before() throws IOException {
    random = new Random(0);

    Configuration config = new Configuration()
        .addTypeSource(ClientTypeSource.get());
    api = new ClientApi(FlatPack.create(config));
    api.setServerBase(UriBuilder.fromUri("http://localhost").port(PORT).build());
    assertEquals(204, api.resetPost().execute().getResponseCode());
  }

  /**
   * Demonstrates ConstraintViolation handling. ConstraintViolations are returned as a simple
   * {@code string:string} map in the FlatPackEntity's {@code error} block. The goal is to provide
   * enough context for user interfaces to present the error message in a useful way, without
   * assuming that the client is a Java app.
   */
  @Test
  public void testConstraintViolation() throws IOException {
    Product p = makeProduct();
    p.setPrice(BigDecimal.valueOf(-1));

    FlatPackEntity<?> entity = null;
    try {
      api.productsPut(Collections.singletonList(p))
          .queryParameter("isAdmin", "true")
          .execute();
      fail("Should have seen StatusCodeException");
    } catch (StatusCodeException e) {
      // The 400 status code is returned by the service method.
      assertEquals(400, e.getStatusCode());
      /*
       * If the server returned a valid flatpack-encoded response, it can be retrieved from the
       * StatusCodeException. Otherwise, this method will return null.
       */
      entity = e.getEntity();
    }

    // Pull out the error messages
    Map<String, String> errors = entity.getExtraErrors();
    assertNotNull(errors);
    assertEquals(1, errors.size());
    Map.Entry<String, String> entry = errors.entrySet().iterator().next();
    assertEquals("price", entry.getKey());
    assertEquals("must be greater than or equal to 0", entry.getValue());
  }

  /**
   * Demonstrates how generated client API will present a non-FlatPack resource (as an
   * HttpUrlConnection).
   */
  @Test
  public void testNonFlatpackEndpoint() throws IOException {
    // The query parameters are added as a builder-style pattern
    HttpURLConnection conn = api.helloGet().withName("ClientSmokeTest").execute();
    assertEquals(200, conn.getResponseCode());
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
        Charset.forName("UTF8")));
    assertEquals("Hello ClientSmokeTest!", reader.readLine());
  }

  /**
   * Demonstrates the use of roles to restrict property setters.
   */
  @Test
  public void testRolePropertyAccess() throws IOException {
    // Create a Product
    Product p = new Product();
    UUID uuid = p.getUuid();
    p.setName("Product");
    p.setNotes("Some notes");
    p.setPrice(BigDecimal.valueOf(42));
    api.productsPut(Collections.singletonList(p))
        .queryParameter("isAdmin", "true")
        .execute();

    // Try to update it with a non-admin request
    p = new Product();
    p.setUuid(uuid);
    p.setPrice(BigDecimal.valueOf(1));
    api.productsPut(Collections.singletonList(p)).execute();

    // Verify that nothing changed, as nobody
    List<Product> products = api.productsGet().execute().getValue();
    assertEquals(1, products.size());
    p = products.get(0);
    // Same UUID
    assertEquals(uuid, p.getUuid());
    // Unchanged price
    assertEquals(BigDecimal.valueOf(42), p.getPrice());
    // Can't see the notes
    assertNull(p.getNotes());

    // Now try the update again, as an admin
    p = new Product();
    p.setUuid(uuid);
    p.setPrice(BigDecimal.valueOf(99));
    api.productsPut(Collections.singletonList(p))
        .queryParameter("isAdmin", "true")
        .execute();

    // Verify the changes, as nobody
    products = api.productsGet().execute().getValue();
    assertEquals(1, products.size());
    p = products.get(0);
    // Same UUID
    assertEquals(uuid, p.getUuid());
    // Unchanged price
    assertEquals(BigDecimal.valueOf(99), p.getPrice());
  }

  /**
   * Demonstrates a couple of round-trips to the server.
   */
  @Test
  public void testSimpleGetAndPut() throws IOException {
    List<Product> products = api.productsGet().execute().getValue();
    assertEquals(0, products.size());

    // A server error would be reported as a StatusCodeException, a subclass of IOException
    api.productsPut(Arrays.asList(makeProduct(), makeProduct()))
        .queryParameter("isAdmin", "true")
        .execute();

    /*
     * The object returned from productsGet() is an endpoint-specific interface that may contain
     * additional fluid setters for declared query parameters. It also provides access to some
     * request internals, including the FlatPackEntity that will be sent as part of the request.
     * This allows callers to further customize outgoing requests, in the above case to add the
     * isAdmin query parameter that interacts with the DummyAuthenticator. The call to execute()
     * triggers payload serialization and execution of the HTTP request. This returns a
     * FlatPackEntity describing the response, and getValue() returns the primary value object
     * contained in the payload.
     */
    FlatPackEntity<List<Product>> entity = api.productsGet().execute();
    assertTrue(entity.getExtraErrors().toString(), entity.getExtraErrors().isEmpty());
    assertTrue(entity.getExtraWarnings().toString(), entity.getExtraWarnings().isEmpty());
    products = entity.getValue();
    assertEquals(2, products.size());
    assertEquals(BigDecimal.valueOf(360), products.get(0).getPrice());
    assertEquals(BigDecimal.valueOf(948), products.get(1).getPrice());
    assertTrue(products.get(0).wasPersistent());
    assertTrue(products.get(1).wasPersistent());

    // Try to update one of the objects
    Product p = products.get(0);
    p.setPrice(BigDecimal.valueOf(99));
    assertEquals(Collections.singleton("price"), p.dirtyPropertyNames());
    api.productsPut(Collections.singletonList(p)).queryParameter("isAdmin", "true").execute();

    // Re-fetch and verify update
    products = api.productsGet().execute().getValue();
    assertEquals(99, products.get(0).getPrice().intValue());
    assertTrue(products.get(0).dirtyPropertyNames().isEmpty());
  }

  private Product makeProduct() {
    Product p = new Product();
    p.setName("ClientSmokeTest");
    p.setPrice(BigDecimal.valueOf(random.nextInt(1000)));
    return p;
  }
}
