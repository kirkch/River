package com.softwaremosaic.river.inmemory;

import com.mosaic.lang.Closure;
import com.softwaremosaic.river.DocumentNotFoundException;
import com.softwaremosaic.river.GISIndex;
import com.softwaremosaic.river.Index;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;
import com.softwaremosaic.river.UniqueIndex;
import com.softwaremosaic.river.inmemory.db.InMemoryDB;
import com.softwaremosaic.river.meta.RootDocumentMeta;
import com.softwaremosaic.river.meta.RiverMetaFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class RepositoryInMemory implements Repository {
    private int defaultPageSize = 20;

    private InMemoryDB db;

    private ConcurrentMap<Class,ConcurrentMap> sessionCache = new ConcurrentHashMap<Class, ConcurrentMap>();

    public RepositoryInMemory( InMemoryDB db ) {
        this.db = db;
    }

    @Override
    public <T> T create( T entity ) {
        entity = db.create( entity );

        Object id = fetchIdFor( entity );
        fetchSessionCacheFor(entity).put( id, entity );

        return entity;
    }

    @Override
    public void delete( Object entity ) {
        db.delete( entity );

        Object id = fetchIdFor( entity );
        fetchSessionCacheFor(entity).remove( id );
    }

    @Override
    public void update( Object entity ) {
        db.update( entity );

        Object id = fetchIdFor( entity );
        fetchSessionCacheFor(entity).put( id, entity );
    }

    @Override
    public void clearCaches() {
        sessionCache.clear();
    }

    @Override
    public <T> T fetch( Class<T> entityClass, Object primaryKey ) throws DocumentNotFoundException {
        ConcurrentMap entityCache  = fetchSessionCacheFor( entityClass );
        T             cachedEntity = (T) entityCache.get( primaryKey );

        if ( cachedEntity == null ) {
            T entity = db.fetch( entityClass, primaryKey );

            entityCache.putIfAbsent( primaryKey, entity );
            return entity;
        }

        return cachedEntity;
    }

    @Override
    public <T> T fetchNbl( Class<T> entityClass, Object primaryKey ) {
        ConcurrentMap entityCache  = fetchSessionCacheFor( entityClass );
        T             cachedEntity = (T) entityCache.get( primaryKey );

        if ( cachedEntity == null ) {
            T entity = db.fetchNbl( entityClass, primaryKey );

            if ( entity != null ) {
                entityCache.putIfAbsent( primaryKey, entity );
                return entity;
            }
        }

        return cachedEntity;
    }

    @Override
    public <T> Index<T> getIndex( Class<T> entityClass, String indexName ) {
//        return db.getIndex( entityClass, indexName );
        throw new NotImplementedException();
    }

    @Override
    public <T> UniqueIndex<T> getUniqueIndex( Class<T> entityClass, String indexName ) {
        return new UniqueIndexInMemory<T>( this, db.getUniqueIndex(entityClass, indexName) );
    }

    @Override
    public <T> GISIndex<T> getGISIndex( Class<T> entityClass, String indexName ) {
        return new GISIndexInMemory<T>( this, db.getGISIndex(entityClass, indexName) );
    }

    @Override
    public <T> int count( Class<T> entityClass ) {
        return db.count( entityClass );
    }

    @Override
    public <T> int forEach( Class<T> entityClass, Closure<T> closure ) {
        final RiverMetaFactory metaFactory = db.getMetaFactory();
        final RootDocumentMeta rootDocumentMeta = metaFactory.fetchRootDocumentMetaFor( entityClass );
        final UniqueIndex<T>   idIndex     = getUniqueIndex( entityClass, metaFactory.getPrimaryKeyIndexNameFor(entityClass) );

        int count = 0;
        PagedCollection<T> page = idIndex.scan( null, null, ScanDirection.ASC, null, defaultPageSize );
        while ( page.isNotEmpty() ) {
            for ( T e : page ) {
                T modifiedEntity = closure.invoke( db.getCloner().deepCopy( e ) );
                if ( modifiedEntity != null ) {
                    assert rootDocumentMeta.getIdFor( e ).equals( rootDocumentMeta.getIdFor( modifiedEntity )) : "id was changed";

                    update( modifiedEntity );
                    count++;
                }
            }

            Object idOfLastEntry = rootDocumentMeta.getIdFor( page.getLast() );
            page = idIndex.scan( null, null, ScanDirection.ASC, idOfLastEntry, defaultPageSize );
        }
        
        return count;
    }

//    @Override
    public void close() throws Exception {
        db.close();
    }

    private <T> Object fetchIdFor( T entity ) {
        return db.getMetaFactory().fetchRootDocumentMetaFor( (Class<T>) entity.getClass() ).getIdFor( entity );
    }

    private ConcurrentMap fetchSessionCacheFor( Class entityClass ) {
        ConcurrentMap entityCache = sessionCache.get( entityClass );
        if ( entityCache == null ) {
            entityCache = new ConcurrentHashMap();

            sessionCache.put( entityClass, entityCache );
        }

        return entityCache;
    }

    private ConcurrentMap fetchSessionCacheFor( Object entity ) {
        return fetchSessionCacheFor( entity.getClass() );
    }
}
