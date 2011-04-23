package com.softwaremosaic.river.annotations;

import com.softwaremosaic.river.meta.IndexType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 *
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RiverIndex {
    String name();
    String keys();

    IndexType type() default IndexType.UNIQUE_INDEX;
}
