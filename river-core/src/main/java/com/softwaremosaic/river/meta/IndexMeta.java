package com.softwaremosaic.river.meta;

import com.mosaic.lang.Immutable;
import com.mosaic.lang.reflect.JavaField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class IndexMeta<T> implements Immutable, Iterable<JavaField> {
    private String              name;
    private RootDocumentMeta<T> rootDocumentMeta;
    private List<JavaField>     compositeKey = new ArrayList<JavaField>();
    private IndexType           indexType;


    public IndexMeta( String name, RootDocumentMeta<T> rootDocumentMeta ) {
        this.name       = name;
        this.rootDocumentMeta = rootDocumentMeta;
    }

    public String getName() {
        return name;
    }

    public IndexType getIndexType() {
        return indexType;
    }

    public void setIndexType( IndexType indexType ) {
        this.indexType = indexType;
    }

    public void appendField( JavaField field ) {
        compositeKey.add( field );
    }

    public JavaField getCompositeKey( int index ) {
        return compositeKey.get( index );
    }

    public int getKeySize() {
        return compositeKey.size();
    }

    @Override
    public Iterator<JavaField> iterator() {
        return compositeKey.iterator();
    }

    @Override
    public String toString() {
        return name + "(" + compositeKey + ")";
    }

    public Class<T> getEntityClass() {
        return rootDocumentMeta.getEntityClass();
    }

    public Object[] getKeyFor( T entity ) {
        final int keySize = compositeKey.size();
        Object[] key = new Object[keySize];

        for ( int i=0; i<keySize; i++ ) {
            key[i] = compositeKey.get(i).getValue( entity );
        }

        return key;
    }

    public Object getIdFor( T entity ) {
        return rootDocumentMeta.getIdFor( entity );
    }
}
