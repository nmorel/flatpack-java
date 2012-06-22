/*
 * #%L
 * Javadoc tool plugins
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
/*
 * Copyright 2011 Robert W. Vawter III <bob@vawter.org>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * Modified to add support for Perka's server roles and polymorphic type system, and flatpack
 * encoding rules.
 */
/**
 * This file is a schema that defines the jsonddl schema.
 * <p>
 * The var declaration at the top isn't actually important to the parser, but it
 * does make the schema a valid JavaScript program.
 */
var schema = {
  /**
   * A Schema is the top-level object that encapsulates the normalized form of a
   * json-ddl schema.
   */
  Schema : {
    /**
     * General information about the schema itself.
     */
    comment : "",
    /**
     * A map of object models by simple name.
     */
    models : {
      "" : "Model"
    }
  },
  /**
   * A model represents a single kind of object within the schema. There is a
   * 1:1 correspondence between Models and Java classes or JS type closures.
   */
  Model : {
    comment : "",
    /**
     * A two-level map that breaks out per-dialect properties to be consumed by
     * the dialect code-generators.
     */
    dialectProperties : {
      "" : {
        "" : ""
      }
    },
    enumValues : [ "EnumValue" ],
    name : "",
    properties : [ "Property" ],
    supertype : "Model"
  },
  /**
   * A property is a pair of a name and a type.
   */
  Property : {
    /**
     * This comment will be stored in this property.
     */
    comment : "",
    deprecated : "",
    /**
     * The other side of a OneToMany relationship.
     */
    impliedProperty : "",
    name : "",
    readOnly : false,
    roles : [ "" ],
    type : "Type"
  },
  EnumValue : {
    comment : "",
    name : ""
  },
  /**
   * This is a simplified type system, representing only the types of data that
   * can be directly expressed in the flatpack schema.
   */
  Kind : [ "BOOLEAN", "DATETIME", "DOUBLE", "ENUM", "ERROR", "FORMMULTIPART",
      "INTEGER", "JSON", "LIST", "LOCALDATETIME", "LOCALTIME",
      /**
       * The only map key type that makes sense for json is a String.
       */
      "MAP", "REFERENCE", "STRING", "TIMEZONE" ],
  /**
   * A specific Kind and parameterization thereof.
   */
  Type : {
    reference : "Model",
    kind : "Kind",
    listElement : "Type",
    mapKey : "Type",
    mapValue : "Type"
  },
  /**
   * The public-facing API is a collection of Endpoints.
   */
  Api : {
    endpoints : {
      '' : 'Endpoint'
    }
  },
  /**
   * Represents an HTTP request that can be made to the API server.
   */
  Endpoint : {
    comment : '',
    entityType : 'Type',
    method : 'Method',
    path : '',
    pathParameters : [ 'Parameter' ],
    permitAll : false,
    queryParameters : [ 'Parameter' ],
    returnType : 'Type',
    roles : [ '' ]
  },
  Method : [ 'DELETE', 'GET', 'POST', 'PUT' ],
  Parameter : {
    comment : '',
    name : '',
    type : 'Type'
  }
};