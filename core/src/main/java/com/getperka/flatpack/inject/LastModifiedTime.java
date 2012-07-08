package com.getperka.flatpack.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.joda.time.DateTime;

import com.getperka.flatpack.FlatPackEntity;
import com.google.inject.BindingAnnotation;

/**
 * A binding annotation for a {@link DateTime} indicating the last-modified time in a {@link FlatPackEntity}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface LastModifiedTime {}