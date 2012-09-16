# FlatPack Serialization Framework

FlatPack is serialization framework used by Perka to make its JPA entities available via REST to JavaScript, iOS, and Android clients.

[![Build Status](https://nmorel.ci.cloudbees.com/job/flatpack-gwt/badge/icon)](https://nmorel.ci.cloudbees.com/job/flatpack-gwt/)

## Goals

* Serialize arbitrary JPA object graphs into payloads that can be processed easily in non-Java environments.
* Support role- and principal-based security on property access.
* Integration with jax-rs (specifically Jersey).
* Straightforward code generation of client libraries and documentation.
* Minimize developer friction

## Thirty-Second Tour
* `apidoc` and `apidoc-plugin` are used during the maven build process to make JavaDoc strings available to `ApiDescriber`.
* `client` provides support code for generated client libraries.
* `core` contains the serialization logic. The central types are `FlatPack`, `Packer`, and `Unpacker`.
* `demo-client` and `demo-server` show a very simple JAX-RS server and how to use generated client code. The `demo-client` module also contains smoke-tests.
* `eclipse-plugin` contains an m2eclipse connector for the FlatPack plugins. A pre-built drop-in is available at from [the project downloads](https://github.com/perka/flatpack-java/downloads).
* `fast` and `fast-plugin`, the FlatPack Automatic Source Tool, take an `ApiDescription` and generate client access libraries consisting of DTO objects and API request methods.
* `jersey` wires FlatPack into Jersey's request dispatch logic. The `ApiDescriber` type will produce a FlatPack description of the API server's methods and entities.
* `search` is utility code.

## More Information

Additional documentation is available in the [FlatPack wiki](https://github.com/perka/flatpack-java/wiki).