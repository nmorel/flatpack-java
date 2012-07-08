/**
 * FlatPack uses Guice internally for configuration and instantiation of various components. 
 * Users should not generally need to interact with any of the types in this package. Advanced
 * integration cases may forego the use of the {@link com.getperka.flatpack.FlatPack#create},
 * instead choosing to install {@link com.getperka.flatpack.inject.FlatPackModule}
 * into their Guice configuration.
 */
package com.getperka.flatpack.inject;