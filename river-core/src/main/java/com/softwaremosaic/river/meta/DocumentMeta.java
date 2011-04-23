package com.softwaremosaic.river.meta;

import com.mosaic.lang.reflect.JavaClass;
import com.mosaic.lang.reflect.JavaField;
import com.mosaic.lang.reflect.JavaProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
public class DocumentMeta<T> {
    private Class<T>                            entityType;
    private ConcurrentMap<String, JavaField<T>> dataFields = new ConcurrentHashMap<String, JavaField<T>>();

    public DocumentMeta( Class<T> entityType ) {
        this.entityType = entityType;
    }

    public Class<T> getEntityClass() {
        return entityType;
    }

    
    public Collection<JavaProperty> getProperties() {
        return JavaClass.toJavaClass( entityType ).getProperties().values();
    }

    public Map<String,JavaField<T>> getDataFields() {
        return Collections.unmodifiableMap( dataFields );
    }

    public boolean appendDataField( String key, JavaField<T> field ) {
        return dataFields.putIfAbsent( key, field ) == null;
    }

    public JavaField<T> getDataField( String fieldName ) {
        return dataFields.get( fieldName );
    }
}
