package com.getperka.flatpack.inject;

import com.getperka.flatpack.Configuration;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.SerializationContext;

/**
 * Exposes additional implementation details for access by test code.
 */
public class FlatPackTestModule extends FlatPackModule {

  public FlatPackTestModule(Configuration configuration) {
    super(configuration);
  }

  @Override
  protected void configure() {
    super.configure();

    bind(DeserializationContext.class);
    expose(DeserializationContext.class);

    bind(SerializationContext.class);
    expose(SerializationContext.class);

    expose(EntityResolver.class);
    expose(PackScope.class);
  }
}
