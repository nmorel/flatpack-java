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
{
  "value": "f13d4a87-2c2d-3a49-9577-f122148ded79",
  "data": {
    "apiDescription": [
      {
        "uuid": "f13d4a87-2c2d-3a49-9577-f122148ded79",
        "endpointsUuid": [
          "79c18883-c98b-3425-97a6-8af848f924ce",
          "1287e42b-fc27-394f-a43a-c9bace128a3b",
          "87969d86-7ee0-3588-a322-0f2c3b0ad34b",
          "dd06395a-c207-34d5-804c-f4566cd29e10",
          "1472a37f-ccdb-3e1c-962d-e9bfb111ba71"
        ],
        "entitiesUuid": [
          "9ea38475-39ce-303a-a053-5a386d017267",
          "047a267b-694a-3e82-a9af-b14ae7431ea3",
          "cd92a122-b499-3101-a808-829143822e9c",
          "5135f675-9a52-3879-a356-16eceefb6f17",
          "b8da57c7-4372-3592-b249-a6be2607760c",
          "58983d81-b29b-315b-8299-1517bbfd6e3d",
          "613b6e38-b098-3e7f-96f7-2f5044406bad",
          "87b8d9da-4903-3d8c-a293-fa19679f06ad",
          "1ee524d6-7c1a-3aa5-b8e1-dc8ac5b595fa",
          "08ab65f2-0088-3fe2-9245-15dc2ec1c71d"
        ]
      }
    ],
    "endpointDescription": [
      {
        "uuid": "79c18883-c98b-3425-97a6-8af848f924ce",
        "method": "POST",
        "path": "/reset",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "1287e42b-fc27-394f-a43a-c9bace128a3b",
        "method": "GET",
        "returnTypeUuid": "8272c0fc-cac4-3904-a7f9-b9bbbad76f98",
        "path": "/describe",
        "docString": "Provide a description of the entities and service methods defined by the web service. Providing\n an endpoint like this is optional, but allows you to use FastTool to automatically generate\n client access libraries.",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "87969d86-7ee0-3588-a322-0f2c3b0ad34b",
        "method": "GET",
        "path": "/hello",
        "docString": "This is a simple endpoint just to show a basic jax-rs request endpoint. The method name is\n irrelevant, all mapping information is derived from the method annotation  GET and the\n  Path annotation. Because multiple methods (e.g.  POST,  PUT) may be bound\n to the same path, but with different methods or media types, a suggested coding style is to add\n the discriminators to the end of a descriptive method name.",
        "queryParametersUuid": [
          "ac34ca0d-3e64-3894-96ad-7196d707aa9c"
        ],
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "dd06395a-c207-34d5-804c-f4566cd29e10",
        "method": "GET",
        "returnTypeUuid": "f976d042-652f-3880-af0c-361a44258f65",
        "path": "/products",
        "docString": "Return the list of products.",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "1472a37f-ccdb-3e1c-962d-e9bfb111ba71",
        "method": "PUT",
        "returnTypeUuid": "a8256fb6-ce24-3ca7-ba8f-41eb33f24046",
        "path": "/products",
        "entityUuid": "f976d042-652f-3880-af0c-361a44258f65",
        "roleNames": [
          "*"
        ]
      }
    ],
    "type": [
      {
        "uuid": "8272c0fc-cac4-3904-a7f9-b9bbbad76f98",
        "name": "apiDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "jsonKind": "STRING"
      },
      {
        "uuid": "f976d042-652f-3880-af0c-361a44258f65",
        "jsonKind": "LIST",
        "listElementUuid": "442d79a3-df43-31f6-b37a-2121653c48a0"
      },
      {
        "uuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "name": "product",
        "jsonKind": "STRING"
      },
      {
        "uuid": "a8256fb6-ce24-3ca7-ba8f-41eb33f24046",
        "jsonKind": "NULL"
      },
      {
        "uuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "jsonKind": "STRING",
        "typeHint": "java.util.UUID"
      },
      {
        "uuid": "529dc19c-8a01-3c36-a9b6-cec5de515731",
        "jsonKind": "LIST",
        "listElementUuid": "8972f791-3eaa-3e17-8202-a4d84aa17557"
      },
      {
        "uuid": "8972f791-3eaa-3e17-8202-a4d84aa17557",
        "name": "endpointDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "0739fd15-56ea-3fb9-bfee-66fbd08f582f",
        "jsonKind": "LIST",
        "listElementUuid": "ecd216c6-c358-3a7b-be43-8e02317e77b0"
      },
      {
        "uuid": "ecd216c6-c358-3a7b-be43-8e02317e77b0",
        "name": "entityDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "type",
        "jsonKind": "STRING"
      },
      {
        "uuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "jsonKind": "LIST",
        "listElementUuid": "98686d52-56c7-375d-b7d7-e7199e2ba052"
      },
      {
        "uuid": "98686d52-56c7-375d-b7d7-e7199e2ba052",
        "name": "parameterDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "jsonKind": "LIST",
        "listElementUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe"
      },
      {
        "uuid": "7e9a128d-6303-315c-8c5c-bc2a9daf0e82",
        "jsonKind": "LIST",
        "listElementUuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1"
      },
      {
        "uuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1",
        "name": "property",
        "jsonKind": "STRING"
      },
      {
        "uuid": "acee1d10-788f-394d-aaf5-1b9d8ee8494a",
        "jsonKind": "STRING",
        "typeHint": "java.math.BigDecimal"
      },
      {
        "uuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "jsonKind": "BOOLEAN"
      },
      {
        "uuid": "3a3db3e1-0f1a-325d-8b44-013f15b28680",
        "name": "jsonKind",
        "jsonKind": "STRING",
        "enumValues": [
          "ANY",
          "BOOLEAN",
          "DOUBLE",
          "INTEGER",
          "LIST",
          "MAP",
          "NULL",
          "STRING"
        ]
      },
      {
        "uuid": "8fc89153-85bf-3fb4-b2da-cb1c364ae615",
        "jsonKind": "STRING",
        "typeHint": "com.getperka.flatpack.ext.TypeHint"
      }
    ],
    "parameterDescription": [
      {
        "uuid": "ac34ca0d-3e64-3894-96ad-7196d707aa9c",
        "name": "name",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "docString": "inserted into the return payload",
        "endpointUuid": "87969d86-7ee0-3588-a322-0f2c3b0ad34b"
      }
    ],
    "entityDescription": [
      {
        "uuid": "9ea38475-39ce-303a-a053-5a386d017267",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "494d0819-4504-3e1e-9786-d82befdbeedc",
          "eb344d08-ff62-3e65-a1e0-1d804e5cc19e",
          "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
          "8dc42055-f59e-3886-bcd2-480c38d7108f"
        ],
        "typeName": "apiDescription",
        "docString": "A description of the entities contained within an API.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb"
        ],
        "typeName": "baseHasUuid"
      },
      {
        "uuid": "cd92a122-b499-3101-a808-829143822e9c",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "272bc6e6-d320-3228-88b9-010750c23e4e",
          "a18b1330-c2f4-3923-87ba-f511d5b6a018",
          "0da7cfc0-6137-36de-b36a-870de2cbd263",
          "54431d6e-df0d-3c5e-8ab4-723d8607095e",
          "96a0fa0a-3eda-32f2-825f-5db97e122c16",
          "5be6cbb3-3b88-383f-b557-26688d4e1a41",
          "dd799707-e571-3759-b935-8e20ffa5030d",
          "424c9e0c-260a-3438-9ada-ee35161ce8d6"
        ],
        "typeName": "endpointDescription",
        "docString": "Describes an <code>HTTP</code> request endpoint.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "5135f675-9a52-3879-a356-16eceefb6f17",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "114d2db3-fa2b-34fb-8828-ff0142887c25",
          "661989aa-ef44-35ce-8d14-311f1bef1eaf",
          "419782a1-c2a6-3034-a6ba-966a07ee8803",
          "217847a7-6e2d-3d36-a4e6-a4bcf945946f"
        ],
        "typeName": "entityDescription",
        "docString": "A description of an entity type.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "b8da57c7-4372-3592-b249-a6be2607760c",
        "propertiesUuid": [
          "cbff7c17-f0b4-3e54-a433-ab021b6c4f37"
        ],
        "typeName": "hasUuid"
      },
      {
        "uuid": "58983d81-b29b-315b-8299-1517bbfd6e3d",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "ed205c24-f22a-32ad-bc63-656e4d96913b",
          "35295942-2157-3bb2-8b93-0e7eff204ca2",
          "910eb3c9-ce60-3be9-8182-df702ed91608",
          "52f89510-f974-304e-9841-aa63600ab4e1"
        ],
        "typeName": "parameterDescription",
        "docString": "Describes a path or query parameter in <entityReference payloadName='endpointDescription'> EndpointDescription</entityReference>.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "613b6e38-b098-3e7f-96f7-2f5044406bad",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "4e786099-51b9-3134-9ccc-10fd1371f61b",
          "0d2d906f-4f10-363a-b905-f521e4f21532",
          "fee36383-bd93-35c9-9884-cba75fad43c3"
        ],
        "typeName": "product",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "87b8d9da-4903-3d8c-a293-fa19679f06ad",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
          "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
          "da3079e5-f2ec-3a74-a336-f28d7dc0396c",
          "054659d6-eae3-35a5-b8f5-b82f3738282b",
          "b31248bd-f64f-3fec-8671-adb86add2d6b",
          "98546ff9-be76-3c17-8987-194c9c39f88e",
          "e002627f-3684-3e20-bea9-a9b6590f84ef",
          "a4372cbe-2902-31ef-aeca-386baaf1f43f",
          "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
          "a0ab1071-14ce-3472-86dc-719268ddf138",
          "56f629da-2825-3abc-a481-2a0dfed14fa3"
        ],
        "typeName": "property",
        "docString": "An immutable view of a property that should be serialized.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "1ee524d6-7c1a-3aa5-b8e1-dc8ac5b595fa",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "44c7b9a4-3ccc-3832-b6db-40e9cb5912a2"
        ],
        "typeName": "propertyPath",
        "docString": "Represents a sequence of simple property evaluations.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      },
      {
        "uuid": "08ab65f2-0088-3fe2-9245-15dc2ec1c71d",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
          "e90f6755-1128-3d3c-ab3c-a7451419d014",
          "09728c90-9892-3d80-a0fa-bf785128f8f0",
          "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7",
          "a90873d2-1958-3776-8b3c-a4d226ca0093",
          "9c510109-ebc1-3934-9df6-4d084f8bb89d",
          "b652e2f2-5142-3f32-8641-8acc521c4ac8"
        ],
        "typeName": "type",
        "docString": "A simple JSON type description.",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3"
      }
    ],
    "property": [
      {
        "uuid": "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
        "name": "uuid",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "enclosingTypeName": "baseHasUuid",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "494d0819-4504-3e1e-9786-d82befdbeedc",
        "name": "apiName",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "eb344d08-ff62-3e65-a1e0-1d804e5cc19e",
        "name": "apiVersion",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
        "name": "endpoints",
        "typeUuid": "529dc19c-8a01-3c36-a9b6-cec5de515731",
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "8dc42055-f59e-3886-bcd2-480c38d7108f",
        "name": "entities",
        "typeUuid": "0739fd15-56ea-3fb9-bfee-66fbd08f582f",
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "272bc6e6-d320-3228-88b9-010750c23e4e",
        "name": "method",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "docString": "The HTTP method used to access the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a18b1330-c2f4-3923-87ba-f511d5b6a018",
        "name": "returnType",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "docString": "The expected contents for the HTTP response.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0da7cfc0-6137-36de-b36a-870de2cbd263",
        "name": "path",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "docString": "The path used to access the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "54431d6e-df0d-3c5e-8ab4-723d8607095e",
        "name": "entity",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "docString": "The expected entity type for the request. Generally, the <code>HTTP POST</code> body.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "96a0fa0a-3eda-32f2-825f-5db97e122c16",
        "name": "docString",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "5be6cbb3-3b88-383f-b557-26688d4e1a41",
        "name": "pathParameters",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "docString": "Describes any parameters embedded in  #getPath().",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "dd799707-e571-3759-b935-8e20ffa5030d",
        "name": "queryParameters",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "docString": "Describes any query parameters for the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "424c9e0c-260a-3438-9ada-ee35161ce8d6",
        "name": "roleNames",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "docString": "Return the role names that are allowed to access the endpoint. A <code>null</code> value means that\n all roles are allowed, while a zero-length value means that no roles are allowed.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "114d2db3-fa2b-34fb-8828-ff0142887c25",
        "name": "properties",
        "typeUuid": "7e9a128d-6303-315c-8c5c-bc2a9daf0e82",
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "661989aa-ef44-35ce-8d14-311f1bef1eaf",
        "name": "typeName",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "419782a1-c2a6-3034-a6ba-966a07ee8803",
        "name": "docString",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "217847a7-6e2d-3d36-a4e6-a4bcf945946f",
        "name": "supertype",
        "typeUuid": "ecd216c6-c358-3a7b-be43-8e02317e77b0",
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "cbff7c17-f0b4-3e54-a433-ab021b6c4f37",
        "name": "uuid",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "enclosingTypeName": "hasUuid",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "ed205c24-f22a-32ad-bc63-656e4d96913b",
        "name": "name",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "35295942-2157-3bb2-8b93-0e7eff204ca2",
        "name": "type",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "910eb3c9-ce60-3be9-8182-df702ed91608",
        "name": "docString",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "52f89510-f974-304e-9841-aa63600ab4e1",
        "name": "endpoint",
        "typeUuid": "8972f791-3eaa-3e17-8202-a4d84aa17557",
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "4e786099-51b9-3134-9ccc-10fd1371f61b",
        "name": "name",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0d2d906f-4f10-363a-b905-f521e4f21532",
        "name": "notes",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "fee36383-bd93-35c9-9884-cba75fad43c3",
        "name": "price",
        "typeUuid": "acee1d10-788f-394d-aaf5-1b9d8ee8494a",
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
        "name": "name",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "docString": "Returns the json payload name of the Property, which may differ from the bean name if a\n  JsonProperty annotation has been applied to the getter.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
        "name": "type",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "docString": "A simplified description of the property's type.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "da3079e5-f2ec-3a74-a336-f28d7dc0396c",
        "name": "docString",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "054659d6-eae3-35a5-b8f5-b82f3738282b",
        "name": "enclosingTypeName",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "docString": "The payload name of the type that defines the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b31248bd-f64f-3fec-8671-adb86add2d6b",
        "name": "getterRoleNames",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "docString": "Returns the role names that are allowed to get the property. A value containing a single\n asterisk means that all roles may access the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "98546ff9-be76-3c17-8987-194c9c39f88e",
        "name": "impliedPropery",
        "typeUuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1",
        "docString": "When a new value is assigned to the current property in some instance, the implied property of\n the new value should also be updated with the current instance.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "e002627f-3684-3e20-bea9-a9b6590f84ef",
        "name": "setterRoleNames",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "docString": "Return the role names that are allowed to set this property. A value containing a single\n asterisk means that all roles may set the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a4372cbe-2902-31ef-aeca-386baaf1f43f",
        "name": "deepTraversalOnly",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "docString": "Returns <code>true</code> if the Property should be included only during a deep traversal.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
        "name": "embedded",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "docString": "Returns <code>true</code> if an entity Property's properties should be emitted into the owning\n entity's properties.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a0ab1071-14ce-3472-86dc-719268ddf138",
        "name": "inheritPrincipal",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "docString": "Returns <code>true</code> if the referred entity's owner should also be considered an owner of the\n entity that defines the Property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "56f629da-2825-3abc-a481-2a0dfed14fa3",
        "name": "suppressDefaultValue",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "docString": "If <code>true</code>, non-null properties that contain the property type's default value will not be\n serialized. For example, integer properties whose values are <code>0</code> will not be serialized.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "44c7b9a4-3ccc-3832-b6db-40e9cb5912a2",
        "name": "path",
        "typeUuid": "7e9a128d-6303-315c-8c5c-bc2a9daf0e82",
        "docString": "Return an unmodifiable view of the properties that comprise the path.",
        "enclosingTypeName": "propertyPath",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
        "name": "name",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "e90f6755-1128-3d3c-ab3c-a7451419d014",
        "name": "jsonKind",
        "typeUuid": "3a3db3e1-0f1a-325d-8b44-013f15b28680",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "09728c90-9892-3d80-a0fa-bf785128f8f0",
        "name": "enumValues",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7",
        "name": "listElement",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a90873d2-1958-3776-8b3c-a4d226ca0093",
        "name": "mapKey",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "9c510109-ebc1-3934-9df6-4d084f8bb89d",
        "name": "mapValue",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b652e2f2-5142-3f32-8641-8acc521c4ac8",
        "name": "typeHint",
        "typeUuid": "8fc89153-85bf-3fb4-b2da-cb1c364ae615",
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      }
    ]
  }
}