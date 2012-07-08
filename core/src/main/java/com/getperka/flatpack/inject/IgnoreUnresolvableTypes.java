package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * A binding annotation for a {@code boolean} value indicating whether or not unknown types should be ignored.
 */
@BindingAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreUnresolvableTypes {}