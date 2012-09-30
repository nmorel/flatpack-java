{
  "value": "f13d4a87-2c2d-3a49-9577-f122148ded79",
  "data": {
    "apiDescription": [
      {
        "uuid": "f13d4a87-2c2d-3a49-9577-f122148ded79",
        "endpointsUuid": [
          "79c18883-c98b-3425-97a6-8af848f924ce",
          "87969d86-7ee0-3588-a322-0f2c3b0ad34b",
          "8c4644f7-f28f-36b3-9fa3-6b6ac71ec281",
          "c2b3bcff-9169-3a78-9f4b-524fb61681d9",
          "dd06395a-c207-34d5-804c-f4566cd29e10",
          "1287e42b-fc27-394f-a43a-c9bace128a3b",
          "1472a37f-ccdb-3e1c-962d-e9bfb111ba71",
          "30e84a5a-f71b-3924-8f65-57b90f16a7d5"
        ],
        "entitiesUuid": [
          "613b6e38-b098-3e7f-96f7-2f5044406bad",
          "1847f46f-642a-338d-bbeb-4b4b60584580",
          "9ea38475-39ce-303a-a053-5a386d017267",
          "739f3813-f506-33aa-abe5-5e392c3375d8",
          "cd92a122-b499-3101-a808-829143822e9c",
          "5135f675-9a52-3879-a356-16eceefb6f17",
          "08ab65f2-0088-3fe2-9245-15dc2ec1c71d",
          "58983d81-b29b-315b-8299-1517bbfd6e3d",
          "87b8d9da-4903-3d8c-a293-fa19679f06ad"
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
        "uuid": "87969d86-7ee0-3588-a322-0f2c3b0ad34b",
        "method": "GET",
        "path": "/hello",
        "queryParametersUuid": [
          "ac34ca0d-3e64-3894-96ad-7196d707aa9c"
        ],
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "8c4644f7-f28f-36b3-9fa3-6b6ac71ec281",
        "method": "GET",
        "returnTypeUuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "path": "/single",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "c2b3bcff-9169-3a78-9f4b-524fb61681d9",
        "method": "GET",
        "returnTypeUuid": "abb47d57-8483-3194-89b0-d96412d8b7e4",
        "path": "/multipleProperties",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "dd06395a-c207-34d5-804c-f4566cd29e10",
        "method": "GET",
        "returnTypeUuid": "f976d042-652f-3880-af0c-361a44258f65",
        "path": "/products",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "1287e42b-fc27-394f-a43a-c9bace128a3b",
        "method": "GET",
        "returnTypeUuid": "8272c0fc-cac4-3904-a7f9-b9bbbad76f98",
        "path": "/describe",
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
      },
      {
        "uuid": "30e84a5a-f71b-3924-8f65-57b90f16a7d5",
        "method": "GET",
        "returnTypeUuid": "30d12b74-0a1d-3ff7-985d-73c72a2ee3c0",
        "path": "/map",
        "roleNames": [
          "*"
        ]
      }
    ],
    "parameterDescription": [
      {
        "uuid": "ac34ca0d-3e64-3894-96ad-7196d707aa9c",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "endpointUuid": "87969d86-7ee0-3588-a322-0f2c3b0ad34b"
      }
    ],
    "type": [
      {
        "uuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "jsonKind": "STRING"
      },
      {
        "uuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "name": "product",
        "jsonKind": "STRING"
      },
      {
        "uuid": "abb47d57-8483-3194-89b0-d96412d8b7e4",
        "name": "multiplePropertiesBean",
        "jsonKind": "STRING"
      },
      {
        "uuid": "f976d042-652f-3880-af0c-361a44258f65",
        "jsonKind": "LIST",
        "listElementUuid": "442d79a3-df43-31f6-b37a-2121653c48a0"
      },
      {
        "uuid": "8272c0fc-cac4-3904-a7f9-b9bbbad76f98",
        "name": "apiDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "a8256fb6-ce24-3ca7-ba8f-41eb33f24046",
        "jsonKind": "NULL"
      },
      {
        "uuid": "30d12b74-0a1d-3ff7-985d-73c72a2ee3c0",
        "mapValueUuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "mapKeyUuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "jsonKind": "MAP"
      },
      {
        "uuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "typeHint": "java.util.UUID",
        "jsonKind": "STRING"
      },
      {
        "uuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "typeHint": "java.lang.Double",
        "jsonKind": "DOUBLE"
      },
      {
        "uuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "typeHint": "java.math.BigDecimal",
        "jsonKind": "ANY"
      },
      {
        "uuid": "23d21f10-d425-3831-a794-023cec349950",
        "typeHint": "java.lang.Byte",
        "jsonKind": "INTEGER"
      },
      {
        "uuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "typeHint": "java.lang.Short",
        "jsonKind": "INTEGER"
      },
      {
        "uuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "typeHint": "java.lang.Integer",
        "jsonKind": "INTEGER"
      },
      {
        "uuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "typeHint": "java.lang.Long",
        "jsonKind": "INTEGER"
      },
      {
        "uuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "typeHint": "java.lang.Float",
        "jsonKind": "DOUBLE"
      },
      {
        "uuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "jsonKind": "BOOLEAN"
      },
      {
        "uuid": "d57d22e0-6a95-3ce4-84e6-6dd61a932fb6",
        "name": "testEnum",
        "jsonKind": "STRING",
        "enumValues": [
          "ONE",
          "TWO",
          "THREE",
          "FOUR",
          "FIVE"
        ]
      },
      {
        "uuid": "86809220-1cbd-3749-aef1-a66368318353",
        "name": "childBean",
        "jsonKind": "STRING"
      },
      {
        "uuid": "09dbd219-3340-380a-827a-d8a7b1b5b706",
        "mapValueUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "mapKeyUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "jsonKind": "MAP"
      },
      {
        "uuid": "4c5f1936-4428-302c-8a5a-0a0c98975ee0",
        "mapValueUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "mapKeyUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "jsonKind": "MAP"
      },
      {
        "uuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "jsonKind": "LIST",
        "listElementUuid": "86809220-1cbd-3749-aef1-a66368318353"
      },
      {
        "uuid": "527f8568-17e9-385d-aa45-7a2332498fa8",
        "typeHint": "java.math.BigInteger",
        "jsonKind": "ANY"
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
        "uuid": "8fc89153-85bf-3fb4-b2da-cb1c364ae615",
        "typeHint": "com.getperka.flatpack.ext.TypeHint",
        "jsonKind": "STRING"
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
      }
    ],
    "entityDescription": [
      {
        "uuid": "613b6e38-b098-3e7f-96f7-2f5044406bad",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "4e786099-51b9-3134-9ccc-10fd1371f61b",
          "f9f66629-e239-38a4-a3d1-a50b232c447b",
          "0d2d906f-4f10-363a-b905-f521e4f21532",
          "fee36383-bd93-35c9-9884-cba75fad43c3"
        ],
        "typeName": "product",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false
      },
      {
        "uuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb"
        ],
        "typeName": "baseHasUuid",
        "persistent": false
      },
      {
        "uuid": "1847f46f-642a-338d-bbeb-4b4b60584580",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "52c2ef7c-572a-389d-9e30-b93e46c3e441",
          "1738b554-2863-38e5-92a4-544e5934a798",
          "325606d8-59f0-3e4e-adf3-c3e90a898761",
          "06079b28-b628-37ca-ba5f-f9fac6c38727",
          "cb8d5814-9723-3b93-9b7b-a90f5a18e9a6",
          "ce009798-54f8-3762-ae55-4547e98152a5",
          "ef72c3fe-5013-395f-9ae9-c00f3d7a65f3",
          "64afcf6c-d6d8-3329-8bfa-5e05c9fb1c45",
          "8ac6a485-bded-3143-92d9-8196876c87b7",
          "0e56e822-802b-385b-92a1-47f8b30f11af",
          "878d8ad2-5be2-3c78-a647-9fb4da71e5f1",
          "ae2f3d4e-b397-3fd3-8e5d-e00ebee46405",
          "9725e240-8d02-33d5-99dd-b18890fc6c88",
          "196b4a86-85f6-32af-ad01-e579b33f197b",
          "1d6d2a10-f691-36f0-af0d-4446213d73e1",
          "61079922-90b8-3302-9e88-903fad1655d5",
          "375f135b-14e1-3367-9219-d1c832f5bf0d",
          "5dfafb68-919d-3c41-9b3e-c334980a2147",
          "9cc0f14c-8cec-357a-b1e3-5c80d0780360",
          "139ea69c-9293-3d06-b2dc-770529eeaf0b",
          "0c57e53b-53d7-33cc-a1d3-b0d4974be362",
          "4b2276ba-1859-37c8-96ca-d1a367e37184",
          "a768c46b-ef0f-313c-ae4e-c8b5fd84f573",
          "2f7619ca-b1e7-3295-a537-bf7551889cfb",
          "6fbba897-ff25-35a4-a1e2-5ba1cebbc0f4",
          "82a75a4c-bc0a-310f-af5d-e39edd7fde59",
          "1902a5be-a57b-3da9-8cce-1e2f3dfb2968",
          "f98f005c-59cb-3641-8327-8ae2980e9e2e",
          "7678aad3-5c49-3d98-ad6e-a977db13aa05",
          "b9c212a9-5e1b-3106-85a0-ec7c1440e1a1",
          "5013de4a-1b4e-3990-97e4-7cc55cd7fa77"
        ],
        "typeName": "multiplePropertiesBean",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false
      },
      {
        "uuid": "9ea38475-39ce-303a-a053-5a386d017267",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
          "494d0819-4504-3e1e-9786-d82befdbeedc",
          "8dc42055-f59e-3886-bcd2-480c38d7108f",
          "eb344d08-ff62-3e65-a1e0-1d804e5cc19e"
        ],
        "typeName": "apiDescription",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      },
      {
        "uuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb"
        ],
        "typeName": "basePersistenceAware",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false
      },
      {
        "uuid": "739f3813-f506-33aa-abe5-5e392c3375d8",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "c1430945-0561-338f-9003-0a18f146f737"
        ],
        "typeName": "childBean",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false
      },
      {
        "uuid": "cd92a122-b499-3101-a808-829143822e9c",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "272bc6e6-d320-3228-88b9-010750c23e4e",
          "a18b1330-c2f4-3923-87ba-f511d5b6a018",
          "0da7cfc0-6137-36de-b36a-870de2cbd263",
          "54431d6e-df0d-3c5e-8ab4-723d8607095e",
          "dd799707-e571-3759-b935-8e20ffa5030d",
          "5be6cbb3-3b88-383f-b557-26688d4e1a41",
          "96a0fa0a-3eda-32f2-825f-5db97e122c16",
          "424c9e0c-260a-3438-9ada-ee35161ce8d6"
        ],
        "typeName": "endpointDescription",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      },
      {
        "uuid": "5135f675-9a52-3879-a356-16eceefb6f17",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "114d2db3-fa2b-34fb-8828-ff0142887c25",
          "661989aa-ef44-35ce-8d14-311f1bef1eaf",
          "217847a7-6e2d-3d36-a4e6-a4bcf945946f",
          "937ecbf4-9cdb-3794-9e57-eced56dca3da",
          "419782a1-c2a6-3034-a6ba-966a07ee8803"
        ],
        "typeName": "entityDescription",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      },
      {
        "uuid": "08ab65f2-0088-3fe2-9245-15dc2ec1c71d",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
          "b652e2f2-5142-3f32-8641-8acc521c4ac8",
          "9c510109-ebc1-3934-9df6-4d084f8bb89d",
          "a90873d2-1958-3776-8b3c-a4d226ca0093",
          "e90f6755-1128-3d3c-ab3c-a7451419d014",
          "09728c90-9892-3d80-a0fa-bf785128f8f0",
          "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7"
        ],
        "typeName": "type",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      },
      {
        "uuid": "58983d81-b29b-315b-8299-1517bbfd6e3d",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "35295942-2157-3bb2-8b93-0e7eff204ca2",
          "ed205c24-f22a-32ad-bc63-656e4d96913b",
          "52f89510-f974-304e-9841-aa63600ab4e1",
          "910eb3c9-ce60-3be9-8182-df702ed91608"
        ],
        "typeName": "parameterDescription",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      },
      {
        "uuid": "87b8d9da-4903-3d8c-a293-fa19679f06ad",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
          "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
          "054659d6-eae3-35a5-b8f5-b82f3738282b",
          "b31248bd-f64f-3fec-8671-adb86add2d6b",
          "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
          "98546ff9-be76-3c17-8987-194c9c39f88e",
          "e002627f-3684-3e20-bea9-a9b6590f84ef",
          "a4372cbe-2902-31ef-aeca-386baaf1f43f",
          "a0ab1071-14ce-3472-86dc-719268ddf138",
          "56f629da-2825-3abc-a481-2a0dfed14fa3",
          "da3079e5-f2ec-3a74-a336-f28d7dc0396c"
        ],
        "typeName": "property",
        "supertypeUuid": "cac59efa-d5fb-3eca-867c-bb9ece8fdcaf",
        "persistent": false
      }
    ],
    "property": [
      {
        "uuid": "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "name": "uuid",
        "enclosingTypeName": "baseHasUuid",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "4e786099-51b9-3134-9ccc-10fd1371f61b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "enclosingTypeName": "product",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "f9f66629-e239-38a4-a3d1-a50b232c447b",
        "typeUuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "name": "doubleProperty",
        "enclosingTypeName": "product",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "0d2d906f-4f10-363a-b905-f521e4f21532",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "notes",
        "enclosingTypeName": "product",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "fee36383-bd93-35c9-9884-cba75fad43c3",
        "typeUuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "name": "price",
        "enclosingTypeName": "product",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "52c2ef7c-572a-389d-9e30-b93e46c3e441",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "string",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "1738b554-2863-38e5-92a4-544e5934a798",
        "typeUuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "name": "bigDecimal",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "325606d8-59f0-3e4e-adf3-c3e90a898761",
        "typeUuid": "23d21f10-d425-3831-a794-023cec349950",
        "name": "bytePrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "06079b28-b628-37ca-ba5f-f9fac6c38727",
        "typeUuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "name": "shortPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "cb8d5814-9723-3b93-9b7b-a90f5a18e9a6",
        "typeUuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "name": "shortBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "ce009798-54f8-3762-ae55-4547e98152a5",
        "typeUuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "name": "intPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "ef72c3fe-5013-395f-9ae9-c00f3d7a65f3",
        "typeUuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "name": "longPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "64afcf6c-d6d8-3329-8bfa-5e05c9fb1c45",
        "typeUuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "name": "doublePrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "8ac6a485-bded-3143-92d9-8196876c87b7",
        "typeUuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "name": "doubleBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "0e56e822-802b-385b-92a1-47f8b30f11af",
        "typeUuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "name": "floatPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "878d8ad2-5be2-3c78-a647-9fb4da71e5f1",
        "typeUuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "name": "floatBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "ae2f3d4e-b397-3fd3-8e5d-e00ebee46405",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "booleanPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "9725e240-8d02-33d5-99dd-b18890fc6c88",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "booleanBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "196b4a86-85f6-32af-ad01-e579b33f197b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "charPrimitive",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "1d6d2a10-f691-36f0-af0d-4446213d73e1",
        "typeUuid": "d57d22e0-6a95-3ce4-84e6-6dd61a932fb6",
        "name": "enumProperty",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "61079922-90b8-3302-9e88-903fad1655d5",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "name": "uuidProperty",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "375f135b-14e1-3367-9219-d1c832f5bf0d",
        "typeUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "name": "singleEntity",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "5dfafb68-919d-3c41-9b3e-c334980a2147",
        "typeUuid": "09dbd219-3340-380a-827a-d8a7b1b5b706",
        "name": "mapStringToEntity",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "9cc0f14c-8cec-357a-b1e3-5c80d0780360",
        "typeUuid": "4c5f1936-4428-302c-8a5a-0a0c98975ee0",
        "name": "mapEntityToString",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "139ea69c-9293-3d06-b2dc-770529eeaf0b",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "arrayEntity",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "0c57e53b-53d7-33cc-a1d3-b0d4974be362",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "sqlTimestamp",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "4b2276ba-1859-37c8-96ca-d1a367e37184",
        "typeUuid": "527f8568-17e9-385d-aa45-7a2332498fa8",
        "name": "bigInteger",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "a768c46b-ef0f-313c-ae4e-c8b5fd84f573",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "listEntity",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "2f7619ca-b1e7-3295-a537-bf7551889cfb",
        "typeUuid": "23d21f10-d425-3831-a794-023cec349950",
        "name": "byteBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "6fbba897-ff25-35a4-a1e2-5ba1cebbc0f4",
        "typeUuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "name": "intBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "82a75a4c-bc0a-310f-af5d-e39edd7fde59",
        "typeUuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "name": "longBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "1902a5be-a57b-3da9-8cce-1e2f3dfb2968",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "charBoxed",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "f98f005c-59cb-3641-8327-8ae2980e9e2e",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "setEntity",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "7678aad3-5c49-3d98-ad6e-a977db13aa05",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "dateJdk",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "b9c212a9-5e1b-3106-85a0-ec7c1440e1a1",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "sqlDate",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "5013de4a-1b4e-3990-97e4-7cc55cd7fa77",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "sqlTime",
        "enclosingTypeName": "multiplePropertiesBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
        "typeUuid": "529dc19c-8a01-3c36-a9b6-cec5de515731",
        "name": "endpoints",
        "enclosingTypeName": "apiDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "494d0819-4504-3e1e-9786-d82befdbeedc",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "apiName",
        "enclosingTypeName": "apiDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "8dc42055-f59e-3886-bcd2-480c38d7108f",
        "typeUuid": "0739fd15-56ea-3fb9-bfee-66fbd08f582f",
        "name": "entities",
        "enclosingTypeName": "apiDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "eb344d08-ff62-3e65-a1e0-1d804e5cc19e",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "apiVersion",
        "enclosingTypeName": "apiDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "c1430945-0561-338f-9003-0a18f146f737",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "child",
        "enclosingTypeName": "childBean",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "272bc6e6-d320-3228-88b9-010750c23e4e",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "method",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "a18b1330-c2f4-3923-87ba-f511d5b6a018",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "returnType",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "0da7cfc0-6137-36de-b36a-870de2cbd263",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "path",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "54431d6e-df0d-3c5e-8ab4-723d8607095e",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "entity",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "dd799707-e571-3759-b935-8e20ffa5030d",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "name": "queryParameters",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "5be6cbb3-3b88-383f-b557-26688d4e1a41",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "name": "pathParameters",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "96a0fa0a-3eda-32f2-825f-5db97e122c16",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "424c9e0c-260a-3438-9ada-ee35161ce8d6",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "roleNames",
        "enclosingTypeName": "endpointDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "114d2db3-fa2b-34fb-8828-ff0142887c25",
        "typeUuid": "7e9a128d-6303-315c-8c5c-bc2a9daf0e82",
        "name": "properties",
        "enclosingTypeName": "entityDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "661989aa-ef44-35ce-8d14-311f1bef1eaf",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "typeName",
        "enclosingTypeName": "entityDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "217847a7-6e2d-3d36-a4e6-a4bcf945946f",
        "typeUuid": "ecd216c6-c358-3a7b-be43-8e02317e77b0",
        "name": "supertype",
        "enclosingTypeName": "entityDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "937ecbf4-9cdb-3794-9e57-eced56dca3da",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "persistent",
        "enclosingTypeName": "entityDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "419782a1-c2a6-3034-a6ba-966a07ee8803",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "enclosingTypeName": "entityDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "b652e2f2-5142-3f32-8641-8acc521c4ac8",
        "typeUuid": "8fc89153-85bf-3fb4-b2da-cb1c364ae615",
        "name": "typeHint",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "9c510109-ebc1-3934-9df6-4d084f8bb89d",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "mapValue",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "a90873d2-1958-3776-8b3c-a4d226ca0093",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "mapKey",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "e90f6755-1128-3d3c-ab3c-a7451419d014",
        "typeUuid": "3a3db3e1-0f1a-325d-8b44-013f15b28680",
        "name": "jsonKind",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "09728c90-9892-3d80-a0fa-bf785128f8f0",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "enumValues",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "listElement",
        "enclosingTypeName": "type",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "35295942-2157-3bb2-8b93-0e7eff204ca2",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "type",
        "enclosingTypeName": "parameterDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "ed205c24-f22a-32ad-bc63-656e4d96913b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "enclosingTypeName": "parameterDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "52f89510-f974-304e-9841-aa63600ab4e1",
        "typeUuid": "8972f791-3eaa-3e17-8202-a4d84aa17557",
        "name": "endpoint",
        "enclosingTypeName": "parameterDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "910eb3c9-ce60-3be9-8182-df702ed91608",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "enclosingTypeName": "parameterDescription",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "type",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "054659d6-eae3-35a5-b8f5-b82f3738282b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "enclosingTypeName",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "b31248bd-f64f-3fec-8671-adb86add2d6b",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "getterRoleNames",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "embedded",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "98546ff9-be76-3c17-8987-194c9c39f88e",
        "typeUuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1",
        "name": "impliedPropery",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "e002627f-3684-3e20-bea9-a9b6590f84ef",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "setterRoleNames",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "a4372cbe-2902-31ef-aeca-386baaf1f43f",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "deepTraversalOnly",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "a0ab1071-14ce-3472-86dc-719268ddf138",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "inheritPrincipal",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "56f629da-2825-3abc-a481-2a0dfed14fa3",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "suppressDefaultValue",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      },
      {
        "uuid": "da3079e5-f2ec-3a74-a336-f28d7dc0396c",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "enclosingTypeName": "property",
        "embedded": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false,
        "deepTraversalOnly": false
      }
    ]
  }
}