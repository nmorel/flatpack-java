package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.getperka.flatpack.HasUuid;
import com.google.inject.BindingAnnotation;

/**
 * A binding annotation for a {@code Collection<Class<?>>} with all {@link HasUuid} types that
 * should be supported.
 */
@BindingAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AllTypes {}