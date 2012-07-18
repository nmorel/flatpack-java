package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.slf4j.Logger;

import com.google.inject.BindingAnnotation;

/**
 * A binding annotation used with a {@link Logger} to get the log sink.
 */
@BindingAnnotation
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FlatPackLogger {}
