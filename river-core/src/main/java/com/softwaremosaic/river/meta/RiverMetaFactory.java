package com.softwaremosaic.river.meta;

import com.mosaic.lang.reflect.JavaClass;
import com.mosaic.lang.reflect.JavaField;
import com.softwaremosaic.river.annotations.RiverIndex;
import com.softwaremosaic.river.annotations.RiverIndexes;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class RiverMetaFactory {
    private ConcurrentMap<Class,DocumentMeta> cache = new ConcurrentHashMap<Class, DocumentMeta>();

    public <T> RootDocumentMeta<T> fetchRootDocumentMetaFor( Class<T> entityType ) {
        assert entityType != null;
        
        DocumentMeta m = cache.get( entityType );
        if ( m == null ) {
            m = processEntityType( entityType );

            cache.putIfAbsent( entityType, m );
        }

        return (RootDocumentMeta<T>) m;
    }

    public <T> String getPrimaryKeyIndexNameFor( Class<T> entityClass ) {
        return "primaryKeyUniqueIndex";
    }

    public <T> DocumentMeta<T> fetchDocumentMetaFor( Class<T> entityType ) {
        assert entityType != null;

        DocumentMeta m = cache.get( entityType );
        if ( m == null ) {
            m = processDocument( entityType );

            cache.putIfAbsent( entityType, m );
        }

        return (DocumentMeta<T>) m;
    }

    private <T> DocumentMeta processDocument( Class<T> entityType ) {
        DocumentMeta<T> m = new DocumentMeta<T>( entityType );

        JavaClass jc = JavaClass.toJavaClass( entityType );

        appendDataFields( jc, m );

        return m;
    }

    private <T> RootDocumentMeta<T> processEntityType( Class<T> entityType ) {
        RootDocumentMeta<T> m = new RootDocumentMeta<T>( entityType );

        JavaClass jc = JavaClass.toJavaClass( entityType );

        appendDataFields( jc, m );
        identifyIdField( jc, m );
        appendIndexes( jc, m );

        return m;
    }

    private <T> void appendDataFields( JavaClass<T> jc, DocumentMeta<T> m ) {
        for ( JavaField<T> field : jc.getDeclaredFields().values() ) {
            if ( isDocumentDataField(field) ) {
                String  fieldName         = field.getName();
                boolean addedSuccessfully = m.appendDataField( fieldName, field );

                if ( !addedSuccessfully ) {
                    String fieldNameA = m.getDataField( fieldName ).getOwningClass().getClassName();
                    String fieldNameB = field.getOwningClass().getClassName();

                    throw new IllegalStateException( String.format("'%s' is declared twice, once on %s and again on %s", fieldName, fieldNameA, fieldNameB) );
                }
            }
        }

        if ( !jc.isRootObject() ) {
            appendDataFields( jc.getParent(), m );
        }
    }

    private <T> boolean isDocumentDataField( JavaField<T> field ) {
        return !field.isTransient() && !field.isStatic();
    }

    private <T> void identifyIdField( JavaClass jc, RootDocumentMeta<T> rootDocumentMeta ) {
        JavaField idField = jc.getField( "id" );

        if ( idField == null ) {
            throw new IllegalStateException( "Unable to identify how to set the Primary Key on " + jc + ". Add setId()." );
        }

        rootDocumentMeta.setEntityIdField( idField );
    }

    private <T> void appendIndexes( JavaClass jc, RootDocumentMeta<T> m ) {
        RiverIndexes indexes = (RiverIndexes) jc.getAnnotation( RiverIndexes.class );
        if ( indexes == null ) { return; }

        m.appendIndex( createUniqueIdIndex(m) );

        for ( RiverIndex anno : indexes.value() ) {
            m.appendIndex( createIndexMetaFor(m, jc, anno.name(), anno.keys(), anno.type()) );
        }
    }

    private <T> IndexMeta createUniqueIdIndex( RootDocumentMeta<T> m ) {
        IndexMeta<T> indexMeta = new IndexMeta<T>( getPrimaryKeyIndexNameFor(m.getEntityClass()), m );

        indexMeta.setIndexType( IndexType.UNIQUE_INDEX );
        indexMeta.appendField( m.getIdField() );

        return indexMeta;
    }

    private IndexMeta createIndexMetaFor( RootDocumentMeta rootDocumentMeta, JavaClass jc, String indexName, String keys, IndexType indexType ) {
        IndexMeta indexMeta = new IndexMeta( indexName, rootDocumentMeta );

        for ( String propertyName : keys.split( "," ) ) {
            indexMeta.appendField( jc.getField( propertyName ) );
        }

        indexMeta.setIndexType( indexType );
        return indexMeta;
    }
}
