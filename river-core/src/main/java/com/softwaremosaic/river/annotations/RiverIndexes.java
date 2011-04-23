package com.softwaremosaic.river.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 *
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RiverIndexes {
// NB would prefer to use inheritance or even Object[] to reduce the number of fields.. but Java does not allow that :(
    public RiverIndex[] value() default {};
}
