package com.softwaremosaic.river.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mosaic.lang.Closure;
import com.mosaic.lang.reflect.JavaField;
import com.softwaremosaic.river.EntityNotFoundException;
import com.softwaremosaic.river.GISIndex;
import com.softwaremosaic.river.Index;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.UniqueIndex;
import com.softwaremosaic.river.meta.DocumentMeta;
import com.softwaremosaic.river.meta.RiverMetaFactory;
import com.softwaremosaic.river.meta.RootDocumentMeta;

import java.util.Map;
import java.util.Set;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class RepositoryMongoDB implements Repository {
    private DB               db;
    private RiverMetaFactory metaFactory;

    public RepositoryMongoDB( DB db, RiverMetaFactory metaFactory ) {
        this.db          = db;
        this.metaFactory = metaFactory;
    }

    @Override
    public <T> T create( T entity ) {
        RootDocumentMeta rootDocumentMeta = metaFactory.fetchRootDocumentMetaFor( entity.getClass() );
        String       entityName   = rootDocumentMeta.getDocumentModelName();
        DBCollection dbCollection = db.getCollection( entityName );

        DBObject dbObject = asMongoObject( entity );
        dbCollection.insert( dbObject );

        return entity;
    }

    @Override
    public void delete( Object entity ) {
    }

    @Override
    public void update( Object entity ) {
    }

    @Override
    public void clearFirstLevelCache() {
    }

    @Override
    public <T> T fetch( Class<T> entityClass, Object primaryKey ) throws EntityNotFoundException {
        return null;
    }

    @Override
    public <T> T fetchNbl( Class<T> entityClass, Object primaryKey ) {
        return null;
    }

    @Override
    public <T> Index<T> getIndex( Class<T> entityClass, String indexName ) {
        return null;
    }

    @Override
    public <T> UniqueIndex<T> getUniqueIndex( Class<T> entityClass, String indexName ) {
        return null;
    }

    @Override
    public <T> GISIndex<T> getGISIndex( Class<T> entityClass, String indexName ) {
        return null;
    }

    @Override
    public <T> int count( Class<T> entityClass ) {
        return 0;
    }

    @Override
    public <T> int forEach( Class<T> entityClass, Closure<T> closure ) {
        return 0;
    }

//    @Override
    public void close() throws Exception {
        this.db = null;
    }

//    private <T> DBObject asDBObject( RootDocumentMeta rootDocumentMeta, T entity ) {
//        Collection<JavaProperty> properties = rootDocumentMeta.getProperties();
//
//        return asMongoObject( properties, entity );
//    }

    private DBObject asMongoObject( Object o ) {
        DBObject dbObject = new BasicDBObject();

        DocumentMeta documentMeta = metaFactory.fetchDocumentMetaFor( o.getClass() );
        Set<Map.Entry<String,JavaField>> dataFields = documentMeta.getDataFields().entrySet();
        for ( Map.Entry<String,JavaField> entry : dataFields ) {
            String key   = entry.getKey();
            Object value = entry.getValue().getValue( o );

            dbObject.put( key, encode(value) );
        }

        // remove id field
        // add _id field

        return dbObject;
    }

    private Object encode( Object value ) {
        if ( isCompositeObject(value) ) {
            return asMongoObject( value );
        }

        return value;
    }

    private boolean isCompositeObject( Object value ) {
        return value != null && !value.getClass().getName().startsWith( "java.lang." );
    }
}
