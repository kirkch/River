package com.softwaremosaic.river;

/**
 *
 */
public interface IdGenerator<T> {
    public T generateIdFor( Object entity );
}
