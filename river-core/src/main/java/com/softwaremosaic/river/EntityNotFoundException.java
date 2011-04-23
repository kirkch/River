package com.softwaremosaic.river;

/**
 *
 */
public class EntityNotFoundException extends RuntimeException {
    private Class entityClass;
    private Object key;

    public EntityNotFoundException( Class entityClass, Object key ) {
        super( "Unable to find '"+entityClass+"' identified by '"+key+"'" );
        this.entityClass = entityClass;
        this.key = key;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Object getKey() {
        return key;
    }
}
