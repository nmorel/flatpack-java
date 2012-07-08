package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * A binding annotation for a {@code boolean} value indicating whether or not payloads should 
 * be formatted for human consumption.
 */
@BindingAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PrettyPrint {}