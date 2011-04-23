package com.softwaremosaic.river.meta;

import com.mosaic.lang.reflect.JavaField;
import com.mosaic.lang.reflect.JavaProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class RootDocumentMeta<T> extends DocumentMeta<T> {
    private Map<String,IndexMeta<T>> indexes = new HashMap<String, IndexMeta<T>>();

    private JavaField<T> idField;

    public RootDocumentMeta( Class<T> entityType ) {
        super( entityType );
    }

    public void setEntityIdField( JavaField<T> idField ) {
        this.idField = idField;
    }

    public void appendIndex( IndexMeta index ) {
        assert index.getName() != null;

        indexes.put( index.getName(), index );
    }

    public Object getIdFor( T entity ) {
        return idField.getValue( entity );
    }

    public void setIdOnEntity( T entity, Object key ) {
        idField.setValue( entity, key );
    }

    public Collection<IndexMeta<T>> indexes() {
        return indexes.values();
    }

    public JavaField getIdField() {
        return idField;
    }

    public String getDocumentModelName() { // todo add optional annotation to set the collection name
        return getEntityClass().getSimpleName();
    }
}
