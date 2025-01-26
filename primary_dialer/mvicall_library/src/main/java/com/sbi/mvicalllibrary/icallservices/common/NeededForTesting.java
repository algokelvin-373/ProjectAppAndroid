
package com.sbi.mvicalllibrary.icallservices.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that the class, constructor, method or field is used by tests and therefore cannot be
 * removed by tools like ProGuard.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface NeededForTesting {}
