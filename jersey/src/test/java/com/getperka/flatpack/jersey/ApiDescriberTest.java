/*
 * #%L
 * FlatPack Jersey integration
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
package com.getperka.flatpack.jersey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.search.SearchTypeSource;

public class ApiDescriberTest {

  /**
   * Demo method.
   */
  @GET
  @FlatPackResponse(ApiDescription.class)
  public static Response sampleMethod() {
    return null;
  }

  /**
   * Verify descriptions of flatpack-core can be read and parsed.
   * 
   * @throws IOException
   */
  @Test
  public void test() throws Exception {
    Method method = ApiDescriberTest.class.getDeclaredMethod("sampleMethod");

    FlatPack flatpack = FlatPack.create(new Configuration()
        .addTypeSource(new SearchTypeSource("com.getperka.flatpack")));
    ApiDescription description = new ApiDescriber(flatpack, Collections.singletonList(method))
        .describe();

    EntityDescription toCheck = null;
    for (EntityDescription desc : description.getEntities()) {
      if ("apiDescription".equals(desc.getTypeName())) {
        toCheck = desc;
        break;
      }
    }
    assertNotNull(toCheck);
    // Check doc string extraction
    assertEquals("A description of the entities contained within an API.", toCheck.getDocString());
  }
}
