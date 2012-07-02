package com.getperka.flatpack;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * A source annotation used with the DocStringDoclet to indicate that a method's or class's source
 * should be extracted into {@code package.json}.
 */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Example {}
