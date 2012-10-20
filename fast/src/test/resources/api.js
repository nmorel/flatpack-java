{
  "value": "f13d4a87-2c2d-3a49-9577-f122148ded79",
  "data": {
    "apiDescription": [
      {
        "uuid": "f13d4a87-2c2d-3a49-9577-f122148ded79",
        "endpointsUuid": [
          "79c18883-c98b-3425-97a6-8af848f924ce",
          "87969d86-7ee0-3588-a322-0f2c3b0ad34b",
          "c2b3bcff-9169-3a78-9f4b-524fb61681d9",
          "1287e42b-fc27-394f-a43a-c9bace128a3b",
          "30e84a5a-f71b-3924-8f65-57b90f16a7d5",
          "234fe56c-295a-31e8-a4a2-8bd54bcb6972",
          "dd06395a-c207-34d5-804c-f4566cd29e10",
          "1472a37f-ccdb-3e1c-962d-e9bfb111ba71"
        ],
        "entitiesUuid": [
          "1847f46f-642a-338d-bbeb-4b4b60584580",
          "9ea38475-39ce-303a-a053-5a386d017267",
          "613b6e38-b098-3e7f-96f7-2f5044406bad",
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
        "uuid": "c2b3bcff-9169-3a78-9f4b-524fb61681d9",
        "method": "GET",
        "returnTypeUuid": "abb47d57-8483-3194-89b0-d96412d8b7e4",
        "path": "/multipleProperties",
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
        "uuid": "30e84a5a-f71b-3924-8f65-57b90f16a7d5",
        "method": "GET",
        "returnTypeUuid": "30d12b74-0a1d-3ff7-985d-73c72a2ee3c0",
        "path": "/map",
        "roleNames": [
          "*"
        ]
      },
      {
        "uuid": "234fe56c-295a-31e8-a4a2-8bd54bcb6972",
        "method": "GET",
        "returnTypeUuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "path": "/products/{id}",
        "pathParametersUuid": [
          "43284179-7a00-3479-8ef2-b7b6785e1265"
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
    "parameterDescription": [
      {
        "uuid": "ac34ca0d-3e64-3894-96ad-7196d707aa9c",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "endpointUuid": "87969d86-7ee0-3588-a322-0f2c3b0ad34b"
      },
      {
        "uuid": "43284179-7a00-3479-8ef2-b7b6785e1265",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "id",
        "endpointUuid": "234fe56c-295a-31e8-a4a2-8bd54bcb6972"
      }
    ],
    "type": [
      {
        "uuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "jsonKind": "STRING"
      },
      {
        "uuid": "abb47d57-8483-3194-89b0-d96412d8b7e4",
        "name": "multiplePropertiesBean",
        "jsonKind": "STRING"
      },
      {
        "uuid": "8272c0fc-cac4-3904-a7f9-b9bbbad76f98",
        "name": "apiDescription",
        "jsonKind": "STRING"
      },
      {
        "uuid": "30d12b74-0a1d-3ff7-985d-73c72a2ee3c0",
        "jsonKind": "MAP",
        "mapKeyUuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "mapValueUuid": "442d79a3-df43-31f6-b37a-2121653c48a0"
      },
      {
        "uuid": "442d79a3-df43-31f6-b37a-2121653c48a0",
        "name": "product",
        "jsonKind": "STRING"
      },
      {
        "uuid": "f976d042-652f-3880-af0c-361a44258f65",
        "jsonKind": "LIST",
        "listElementUuid": "442d79a3-df43-31f6-b37a-2121653c48a0"
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
        "uuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "jsonKind": "ANY",
        "typeHint": "java.math.BigDecimal"
      },
      {
        "uuid": "527f8568-17e9-385d-aa45-7a2332498fa8",
        "jsonKind": "ANY",
        "typeHint": "java.math.BigInteger"
      },
      {
        "uuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "jsonKind": "LIST",
        "listElementUuid": "86809220-1cbd-3749-aef1-a66368318353"
      },
      {
        "uuid": "86809220-1cbd-3749-aef1-a66368318353",
        "name": "childBean",
        "jsonKind": "STRING"
      },
      {
        "uuid": "23d21f10-d425-3831-a794-023cec349950",
        "jsonKind": "INTEGER",
        "typeHint": "java.lang.Byte"
      },
      {
        "uuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "jsonKind": "INTEGER",
        "typeHint": "java.lang.Short"
      },
      {
        "uuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "jsonKind": "INTEGER",
        "typeHint": "java.lang.Integer"
      },
      {
        "uuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "jsonKind": "INTEGER",
        "typeHint": "java.lang.Long"
      },
      {
        "uuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "jsonKind": "DOUBLE",
        "typeHint": "java.lang.Double"
      },
      {
        "uuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "jsonKind": "DOUBLE",
        "typeHint": "java.lang.Float"
      },
      {
        "uuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "jsonKind": "BOOLEAN"
      },
      {
        "uuid": "dc8bf019-f75c-39f0-8781-f193a9f6ac03",
        "jsonKind": "STRING",
        "typeHint": "java.lang.Character"
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
        "uuid": "09dbd219-3340-380a-827a-d8a7b1b5b706",
        "jsonKind": "MAP",
        "mapKeyUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "mapValueUuid": "86809220-1cbd-3749-aef1-a66368318353"
      },
      {
        "uuid": "4c5f1936-4428-302c-8a5a-0a0c98975ee0",
        "jsonKind": "MAP",
        "mapKeyUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "mapValueUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe"
      },
      {
        "uuid": "99d9bd91-28b4-3b00-b5f4-8c56b67b9b32",
        "jsonKind": "STRING",
        "typeHint": "java.sql.Timestamp"
      },
      {
        "uuid": "6283b70d-6f92-3495-bcb3-cd0a0019ffc5",
        "jsonKind": "STRING",
        "typeHint": "java.util.Date"
      },
      {
        "uuid": "1daa4015-549c-3999-b2a2-0551a4982982",
        "jsonKind": "STRING",
        "typeHint": "java.sql.Time"
      },
      {
        "uuid": "815787da-cc9a-3aa0-a8ee-a79639fb9e3f",
        "jsonKind": "STRING",
        "typeHint": "java.sql.Date"
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
    "entityDescription": [
      {
        "uuid": "1847f46f-642a-338d-bbeb-4b4b60584580",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "52c2ef7c-572a-389d-9e30-b93e46c3e441",
          "1738b554-2863-38e5-92a4-544e5934a798",
          "4b2276ba-1859-37c8-96ca-d1a367e37184",
          "a768c46b-ef0f-313c-ae4e-c8b5fd84f573",
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
          "2f7619ca-b1e7-3295-a537-bf7551889cfb",
          "6fbba897-ff25-35a4-a1e2-5ba1cebbc0f4",
          "1902a5be-a57b-3da9-8cce-1e2f3dfb2968",
          "82a75a4c-bc0a-310f-af5d-e39edd7fde59",
          "7678aad3-5c49-3d98-ad6e-a977db13aa05",
          "f98f005c-59cb-3641-8327-8ae2980e9e2e",
          "5013de4a-1b4e-3990-97e4-7cc55cd7fa77",
          "b9c212a9-5e1b-3106-85a0-ec7c1440e1a1"
        ],
        "typeName": "multiplePropertiesBean",
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
        "uuid": "9ea38475-39ce-303a-a053-5a386d017267",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "eb344d08-ff62-3e65-a1e0-1d804e5cc19e",
          "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
          "494d0819-4504-3e1e-9786-d82befdbeedc",
          "8dc42055-f59e-3886-bcd2-480c38d7108f"
        ],
        "typeName": "apiDescription",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "A description of the entities contained within an API."
      },
      {
        "uuid": "613b6e38-b098-3e7f-96f7-2f5044406bad",
        "propertiesUuid": [
          "f6ec365c-a1ab-3bb0-817a-d6711f9952d5",
          "65193b64-d899-3274-9481-0d1bb9f6d586",
          "c532eb1b-8078-3a43-b15f-edffba872135",
          "4e786099-51b9-3134-9ccc-10fd1371f61b",
          "2cc2b6ea-04aa-3081-bd17-6569fac941df",
          "fee36383-bd93-35c9-9884-cba75fad43c3",
          "0d2d906f-4f10-363a-b905-f521e4f21532"
        ],
        "typeName": "product",
        "supertypeUuid": "4245d782-4cac-3380-bb64-d964b3bc7647",
        "persistent": true
      },
      {
        "uuid": "4245d782-4cac-3380-bb64-d964b3bc7647",
        "propertiesUuid": [
          "f6ec365c-a1ab-3bb0-817a-d6711f9952d5",
          "65193b64-d899-3274-9481-0d1bb9f6d586",
          "c532eb1b-8078-3a43-b15f-edffba872135"
        ],
        "typeName": "baseEntity",
        "persistent": true
      },
      {
        "uuid": "739f3813-f506-33aa-abe5-5e392c3375d8",
        "propertiesUuid": [
          "f6ec365c-a1ab-3bb0-817a-d6711f9952d5",
          "65193b64-d899-3274-9481-0d1bb9f6d586",
          "c532eb1b-8078-3a43-b15f-edffba872135",
          "c1430945-0561-338f-9003-0a18f146f737",
          "182f0d84-4cca-3ef5-bd21-d6c194d029a8"
        ],
        "typeName": "childBean",
        "supertypeUuid": "4245d782-4cac-3380-bb64-d964b3bc7647",
        "persistent": true
      },
      {
        "uuid": "cd92a122-b499-3101-a808-829143822e9c",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "272bc6e6-d320-3228-88b9-010750c23e4e",
          "a18b1330-c2f4-3923-87ba-f511d5b6a018",
          "0da7cfc0-6137-36de-b36a-870de2cbd263",
          "5be6cbb3-3b88-383f-b557-26688d4e1a41",
          "dd799707-e571-3759-b935-8e20ffa5030d",
          "54431d6e-df0d-3c5e-8ab4-723d8607095e",
          "96a0fa0a-3eda-32f2-825f-5db97e122c16",
          "424c9e0c-260a-3438-9ada-ee35161ce8d6"
        ],
        "typeName": "endpointDescription",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "Describes an <code>HTTP</code> request endpoint."
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
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "A description of an entity type."
      },
      {
        "uuid": "08ab65f2-0088-3fe2-9245-15dc2ec1c71d",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
          "e90f6755-1128-3d3c-ab3c-a7451419d014",
          "a90873d2-1958-3776-8b3c-a4d226ca0093",
          "09728c90-9892-3d80-a0fa-bf785128f8f0",
          "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7",
          "9c510109-ebc1-3934-9df6-4d084f8bb89d",
          "b652e2f2-5142-3f32-8641-8acc521c4ac8"
        ],
        "typeName": "type",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "A simple JSON type description."
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
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "Describes a path or query parameter in <entityReference payloadName='endpointDescription'> EndpointDescription</entityReference>."
      },
      {
        "uuid": "87b8d9da-4903-3d8c-a293-fa19679f06ad",
        "propertiesUuid": [
          "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
          "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
          "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
          "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
          "da3079e5-f2ec-3a74-a336-f28d7dc0396c",
          "054659d6-eae3-35a5-b8f5-b82f3738282b",
          "b31248bd-f64f-3fec-8671-adb86add2d6b",
          "5dbde79c-cf05-38e5-904c-322fa004a5b2",
          "e002627f-3684-3e20-bea9-a9b6590f84ef",
          "a4372cbe-2902-31ef-aeca-386baaf1f43f",
          "a0ab1071-14ce-3472-86dc-719268ddf138",
          "56f629da-2825-3abc-a481-2a0dfed14fa3",
          "b3cf70d1-c0fb-327e-943d-a0a447df288b"
        ],
        "typeName": "property",
        "supertypeUuid": "047a267b-694a-3e82-a9af-b14ae7431ea3",
        "persistent": false,
        "docString": "An immutable view of a property that should be serialized."
      }
    ],
    "property": [
      {
        "uuid": "e0e0884f-75dc-382a-bc5b-63ed72b0d3cb",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "name": "uuid",
        "embedded": false,
        "enclosingTypeName": "baseHasUuid",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "52c2ef7c-572a-389d-9e30-b93e46c3e441",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "string",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "1738b554-2863-38e5-92a4-544e5934a798",
        "typeUuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "name": "bigDecimal",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "4b2276ba-1859-37c8-96ca-d1a367e37184",
        "typeUuid": "527f8568-17e9-385d-aa45-7a2332498fa8",
        "name": "bigInteger",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a768c46b-ef0f-313c-ae4e-c8b5fd84f573",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "listEntity",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "325606d8-59f0-3e4e-adf3-c3e90a898761",
        "typeUuid": "23d21f10-d425-3831-a794-023cec349950",
        "name": "bytePrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "06079b28-b628-37ca-ba5f-f9fac6c38727",
        "typeUuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "name": "shortPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "cb8d5814-9723-3b93-9b7b-a90f5a18e9a6",
        "typeUuid": "8070d17e-b0de-3af2-8dd7-437cbabf291c",
        "name": "shortBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "ce009798-54f8-3762-ae55-4547e98152a5",
        "typeUuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "name": "intPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "ef72c3fe-5013-395f-9ae9-c00f3d7a65f3",
        "typeUuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "name": "longPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "64afcf6c-d6d8-3329-8bfa-5e05c9fb1c45",
        "typeUuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "name": "doublePrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "8ac6a485-bded-3143-92d9-8196876c87b7",
        "typeUuid": "4961f3ae-40d1-345f-9810-2fb59a55bb1f",
        "name": "doubleBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0e56e822-802b-385b-92a1-47f8b30f11af",
        "typeUuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "name": "floatPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "878d8ad2-5be2-3c78-a647-9fb4da71e5f1",
        "typeUuid": "a0349d5f-6d22-3c5c-9497-a778bf91206e",
        "name": "floatBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "ae2f3d4e-b397-3fd3-8e5d-e00ebee46405",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "booleanPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "9725e240-8d02-33d5-99dd-b18890fc6c88",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "booleanBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "196b4a86-85f6-32af-ad01-e579b33f197b",
        "typeUuid": "dc8bf019-f75c-39f0-8781-f193a9f6ac03",
        "name": "charPrimitive",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "1d6d2a10-f691-36f0-af0d-4446213d73e1",
        "typeUuid": "d57d22e0-6a95-3ce4-84e6-6dd61a932fb6",
        "name": "enumProperty",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "61079922-90b8-3302-9e88-903fad1655d5",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "name": "uuidProperty",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "375f135b-14e1-3367-9219-d1c832f5bf0d",
        "typeUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "name": "singleEntity",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "5dfafb68-919d-3c41-9b3e-c334980a2147",
        "typeUuid": "09dbd219-3340-380a-827a-d8a7b1b5b706",
        "name": "mapStringToEntity",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "9cc0f14c-8cec-357a-b1e3-5c80d0780360",
        "typeUuid": "4c5f1936-4428-302c-8a5a-0a0c98975ee0",
        "name": "mapEntityToString",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "139ea69c-9293-3d06-b2dc-770529eeaf0b",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "arrayEntity",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0c57e53b-53d7-33cc-a1d3-b0d4974be362",
        "typeUuid": "99d9bd91-28b4-3b00-b5f4-8c56b67b9b32",
        "name": "sqlTimestamp",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "2f7619ca-b1e7-3295-a537-bf7551889cfb",
        "typeUuid": "23d21f10-d425-3831-a794-023cec349950",
        "name": "byteBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "6fbba897-ff25-35a4-a1e2-5ba1cebbc0f4",
        "typeUuid": "6c72441c-b98a-3f4e-9d2b-e34c47600d3a",
        "name": "intBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "1902a5be-a57b-3da9-8cce-1e2f3dfb2968",
        "typeUuid": "dc8bf019-f75c-39f0-8781-f193a9f6ac03",
        "name": "charBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "82a75a4c-bc0a-310f-af5d-e39edd7fde59",
        "typeUuid": "62e8798c-dff1-3d0b-bb5c-3e7ec6ddff69",
        "name": "longBoxed",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "7678aad3-5c49-3d98-ad6e-a977db13aa05",
        "typeUuid": "6283b70d-6f92-3495-bcb3-cd0a0019ffc5",
        "name": "dateJdk",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "f98f005c-59cb-3641-8327-8ae2980e9e2e",
        "typeUuid": "476319ab-e41f-328b-aa4f-44cd111854f5",
        "name": "setEntity",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "5013de4a-1b4e-3990-97e4-7cc55cd7fa77",
        "typeUuid": "1daa4015-549c-3999-b2a2-0551a4982982",
        "name": "sqlTime",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b9c212a9-5e1b-3106-85a0-ec7c1440e1a1",
        "typeUuid": "815787da-cc9a-3aa0-a8ee-a79639fb9e3f",
        "name": "sqlDate",
        "embedded": false,
        "enclosingTypeName": "multiplePropertiesBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "eb344d08-ff62-3e65-a1e0-1d804e5cc19e",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "apiVersion",
        "embedded": false,
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "24e1f8b5-0604-39b8-9a8f-8c6cabe1597e",
        "typeUuid": "529dc19c-8a01-3c36-a9b6-cec5de515731",
        "name": "endpoints",
        "embedded": false,
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "494d0819-4504-3e1e-9786-d82befdbeedc",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "apiName",
        "embedded": false,
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "8dc42055-f59e-3886-bcd2-480c38d7108f",
        "typeUuid": "0739fd15-56ea-3fb9-bfee-66fbd08f582f",
        "name": "entities",
        "embedded": false,
        "enclosingTypeName": "apiDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "f6ec365c-a1ab-3bb0-817a-d6711f9952d5",
        "typeUuid": "6283b70d-6f92-3495-bcb3-cd0a0019ffc5",
        "name": "creationDate",
        "embedded": false,
        "enclosingTypeName": "baseEntity",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "65193b64-d899-3274-9481-0d1bb9f6d586",
        "typeUuid": "6283b70d-6f92-3495-bcb3-cd0a0019ffc5",
        "name": "modificationDate",
        "embedded": false,
        "enclosingTypeName": "baseEntity",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "c532eb1b-8078-3a43-b15f-edffba872135",
        "typeUuid": "f4f93b7e-fa2d-3130-91f3-09f9affe63a9",
        "name": "uuid",
        "embedded": false,
        "enclosingTypeName": "baseEntity",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "4e786099-51b9-3134-9ccc-10fd1371f61b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "embedded": false,
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "2cc2b6ea-04aa-3081-bd17-6569fac941df",
        "typeUuid": "86809220-1cbd-3749-aef1-a66368318353",
        "name": "child",
        "embedded": false,
        "enclosingTypeName": "product",
        "impliedPropertyUuid": "182f0d84-4cca-3ef5-bd21-d6c194d029a8",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "182f0d84-4cca-3ef5-bd21-d6c194d029a8",
        "typeUuid": "f976d042-652f-3880-af0c-361a44258f65",
        "name": "associatedProducts",
        "embedded": false,
        "enclosingTypeName": "childBean",
        "impliedPropertyUuid": "2cc2b6ea-04aa-3081-bd17-6569fac941df",
        "deepTraversalOnly": true,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "fee36383-bd93-35c9-9884-cba75fad43c3",
        "typeUuid": "6d381a70-ffbb-3789-a074-c3067da2f26b",
        "name": "price",
        "embedded": false,
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0d2d906f-4f10-363a-b905-f521e4f21532",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "notes",
        "embedded": false,
        "enclosingTypeName": "product",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "c1430945-0561-338f-9003-0a18f146f737",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "child",
        "embedded": false,
        "enclosingTypeName": "childBean",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "272bc6e6-d320-3228-88b9-010750c23e4e",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "method",
        "embedded": false,
        "docString": "The HTTP method used to access the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a18b1330-c2f4-3923-87ba-f511d5b6a018",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "returnType",
        "embedded": false,
        "docString": "The expected contents for the HTTP response.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "0da7cfc0-6137-36de-b36a-870de2cbd263",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "path",
        "embedded": false,
        "docString": "The path used to access the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "5be6cbb3-3b88-383f-b557-26688d4e1a41",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "name": "pathParameters",
        "embedded": false,
        "docString": "Describes any parameters embedded in  #getPath().",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "dd799707-e571-3759-b935-8e20ffa5030d",
        "typeUuid": "45ae7e6a-bbb1-3bb0-a7bc-5f58d06a75d7",
        "name": "queryParameters",
        "embedded": false,
        "docString": "Describes any query parameters for the endpoint.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "54431d6e-df0d-3c5e-8ab4-723d8607095e",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "entity",
        "embedded": false,
        "docString": "The expected entity type for the request. Generally, the <code>HTTP POST</code> body.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "96a0fa0a-3eda-32f2-825f-5db97e122c16",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "embedded": false,
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "424c9e0c-260a-3438-9ada-ee35161ce8d6",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "roleNames",
        "embedded": false,
        "docString": "Return the role names that are allowed to access the endpoint. A <code>null</code> value means that\n all roles are allowed, while a zero-length value means that no roles are allowed.",
        "enclosingTypeName": "endpointDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "114d2db3-fa2b-34fb-8828-ff0142887c25",
        "typeUuid": "7e9a128d-6303-315c-8c5c-bc2a9daf0e82",
        "name": "properties",
        "embedded": false,
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "661989aa-ef44-35ce-8d14-311f1bef1eaf",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "typeName",
        "embedded": false,
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "217847a7-6e2d-3d36-a4e6-a4bcf945946f",
        "typeUuid": "ecd216c6-c358-3a7b-be43-8e02317e77b0",
        "name": "supertype",
        "embedded": false,
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "937ecbf4-9cdb-3794-9e57-eced56dca3da",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "persistent",
        "embedded": false,
        "docString": "Indicates that instance of the the type may be persisted by the server. This hint can be used\n to reduce payload sizes by transmitting only mutated properties.",
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "419782a1-c2a6-3034-a6ba-966a07ee8803",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "embedded": false,
        "enclosingTypeName": "entityDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "1a4b5c02-4cd9-3148-89ac-4534eb84f2c6",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "e90f6755-1128-3d3c-ab3c-a7451419d014",
        "typeUuid": "3a3db3e1-0f1a-325d-8b44-013f15b28680",
        "name": "jsonKind",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a90873d2-1958-3776-8b3c-a4d226ca0093",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "mapKey",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "09728c90-9892-3d80-a0fa-bf785128f8f0",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "enumValues",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "aa9f52af-5ac3-3e87-8cf3-be6d76876fc7",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "listElement",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "9c510109-ebc1-3934-9df6-4d084f8bb89d",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "mapValue",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b652e2f2-5142-3f32-8641-8acc521c4ac8",
        "typeUuid": "8fc89153-85bf-3fb4-b2da-cb1c364ae615",
        "name": "typeHint",
        "embedded": false,
        "enclosingTypeName": "type",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "35295942-2157-3bb2-8b93-0e7eff204ca2",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "type",
        "embedded": false,
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "ed205c24-f22a-32ad-bc63-656e4d96913b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "embedded": false,
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "52f89510-f974-304e-9841-aa63600ab4e1",
        "typeUuid": "8972f791-3eaa-3e17-8202-a4d84aa17557",
        "name": "endpoint",
        "embedded": false,
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "910eb3c9-ce60-3be9-8182-df702ed91608",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "embedded": false,
        "enclosingTypeName": "parameterDescription",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "21f0e8f5-ae34-3b03-ba9a-c7acea5159aa",
        "typeUuid": "ac519969-0cab-3a23-b68c-1db2cbd0bff7",
        "name": "type",
        "embedded": false,
        "docString": "A simplified description of the property's type.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "818ac4aa-4ca2-3d61-a457-ae4c9b02def3",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "name",
        "embedded": false,
        "docString": "Returns the json payload name of the Property, which may differ from the bean name if a\n  JsonProperty annotation has been applied to the getter.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "df7dbdd6-eb9b-3a7b-839d-7794b48eaf20",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "embedded",
        "embedded": false,
        "docString": "Returns <code>true</code> if an entity Property's properties should be emitted into the owning\n entity's properties.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "da3079e5-f2ec-3a74-a336-f28d7dc0396c",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "docString",
        "embedded": false,
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "054659d6-eae3-35a5-b8f5-b82f3738282b",
        "typeUuid": "41d0c340-e8c1-3592-8bf4-f69c8ea830fe",
        "name": "enclosingTypeName",
        "embedded": false,
        "docString": "The payload name of the type that defines the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b31248bd-f64f-3fec-8671-adb86add2d6b",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "getterRoleNames",
        "embedded": false,
        "docString": "Returns the role names that are allowed to get the property. A value containing a single\n asterisk means that all roles may access the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "5dbde79c-cf05-38e5-904c-322fa004a5b2",
        "typeUuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1",
        "name": "impliedProperty",
        "embedded": false,
        "docString": "When a new value is assigned to the current property in some instance, the implied property of\n the new value should also be updated with the current instance.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "e002627f-3684-3e20-bea9-a9b6590f84ef",
        "typeUuid": "b187269e-51c8-310e-961f-1c8130d408c6",
        "name": "setterRoleNames",
        "embedded": false,
        "docString": "Return the role names that are allowed to set this property. A value containing a single\n asterisk means that all roles may set the property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a4372cbe-2902-31ef-aeca-386baaf1f43f",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "deepTraversalOnly",
        "embedded": false,
        "docString": "Returns <code>true</code> if the Property should be included only during a deep traversal.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "a0ab1071-14ce-3472-86dc-719268ddf138",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "inheritPrincipal",
        "embedded": false,
        "docString": "Returns <code>true</code> if the referred entity's owner should also be considered an owner of the\n entity that defines the Property.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "56f629da-2825-3abc-a481-2a0dfed14fa3",
        "typeUuid": "22176e08-d364-37ea-9478-488aa68dd24a",
        "name": "suppressDefaultValue",
        "embedded": false,
        "docString": "If <code>true</code>, non-null properties that contain the property type's default value will not be\n serialized. For example, integer properties whose values are <code>0</code> will not be serialized.",
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      },
      {
        "uuid": "b3cf70d1-c0fb-327e-943d-a0a447df288b",
        "typeUuid": "aaad9bd3-7a6e-3272-aebc-0c18cad6e9c1",
        "name": "implied",
        "embedded": false,
        "enclosingTypeName": "property",
        "deepTraversalOnly": false,
        "inheritPrincipal": false,
        "suppressDefaultValue": false
      }
    ]
  }
}