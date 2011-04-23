package com.softwaremosaic.river.inmemory.db;

/**
 *
 */
public interface IndexStore<T> {
    public void entityCreated( T entity );
    public void entityChanged( T oldValue, T newValue );
    public void entityRemoved( T entity );

    public Class<Object> getEntityClass();
}
