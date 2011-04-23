package com.softwaremosaic.river.inmemory.db;

import com.google.inject.Inject;
import com.mosaic.lang.Cloner;
import com.softwaremosaic.river.EntityNotFoundException;
import com.softwaremosaic.river.IdGenerator;
import com.softwaremosaic.river.idgenerators.IdGeneratorSequence;
import com.softwaremosaic.river.meta.RootDocumentMeta;
import com.softwaremosaic.river.meta.IndexMeta;
import com.softwaremosaic.river.meta.RiverMetaFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
public class InMemoryDB {
    private RiverMetaFactory factory;
    private Cloner           cloner      = new Cloner();
    private IdGenerator      idGenerator = new IdGeneratorSequence();


    private ConcurrentMap<Class,ConcurrentMap>                    entityStores = new ConcurrentHashMap<Class, ConcurrentMap>(21);
    private ConcurrentMap<Class,ConcurrentMap<String,IndexStore>> indexes      = new ConcurrentHashMap<Class, ConcurrentMap<String,IndexStore>>();

    @Inject
    public InMemoryDB( RiverMetaFactory factory ) {
        this.factory = factory;
    }

    @Inject
    public void setCloner( Cloner cloner ) {
        this.cloner = cloner;
    }

    public Cloner getCloner() {
        return cloner;
    }

    @Inject
    public void setIdGenerator( IdGenerator idGenerator ) {
        this.idGenerator = idGenerator;
    }

    public RiverMetaFactory getMetaFactory() {
        return factory;
    }

    public synchronized <T> T create( T entity ) {
        Class      entityClass = entity.getClass();
        RootDocumentMeta rootDocumentMeta = factory.fetchRootDocumentMetaFor( entityClass );

        if ( rootDocumentMeta.getIdFor( entity ) != null ) {
            throw new IllegalStateException( entity + " already persisted in " + this );
        }

        Object key = idGenerator.generateIdFor( entity );
        rootDocumentMeta.setIdOnEntity( entity, key );

        Object clonedEntity = cloner.deepCopy( entity );

        ConcurrentMap store = fetchStoreFor( entityClass );
        Object previousValue = store.putIfAbsent( key, clonedEntity );

        if ( previousValue != null ) {
            throw new IllegalStateException( "Entity already exists: " + entity );
        }

        Collection<IndexStore<T>> values = getIndexesFor(entityClass).values();
        for ( IndexStore<T> index : values ) {
            index.entityCreated( entity );
        }

        return entity;
    }

    public synchronized void delete( Object entity ) {
        Class         entityClass = entity.getClass();
        RootDocumentMeta rootDocumentMeta = factory.fetchRootDocumentMetaFor( entityClass );
        ConcurrentMap store       = fetchStoreFor( entityClass );
        Object        key         = rootDocumentMeta.getIdFor( entity );

        Object previousValue = store.remove( key );
        if ( previousValue == null ) {
            return;
        }

        Collection<IndexStore> values = getIndexesFor(entityClass).values();
        for ( IndexStore index : values ) {
            index.entityRemoved( entity );
        }
    }

    public synchronized void update( Object entity ) {
        Class      entityClass = entity.getClass();
        RootDocumentMeta rootDocumentMeta = factory.fetchRootDocumentMetaFor( entityClass );

        Object key = rootDocumentMeta.getIdFor( entity );
        Object clonedEntity = cloner.deepCopy( entity );

        ConcurrentMap store = fetchStoreFor( entityClass );
        Object previousValue = store.put( key, clonedEntity );


        Collection<IndexStore> values = getIndexesFor(entityClass).values();
        for ( IndexStore index : values ) {
            index.entityChanged( previousValue, entity );
        }
    }

    public synchronized <T> T fetch( Class<T> entityClass, Object primaryKey ) throws EntityNotFoundException {
        T entity = fetchNbl( entityClass, primaryKey );
        if ( entity == null ) {
            throw new EntityNotFoundException( entityClass, primaryKey );
        }

        return entity;
    }

    public synchronized <T> T fetchNbl( Class<T> entityClass, Object primaryKey ) {
        Map store = fetchStoreFor( entityClass );

        return cloner.deepCopy( (T) store.get(primaryKey) );
    }

    public synchronized <T> IndexStore<T> getIndex( Class<T> entityClass, String indexName ) {
        try {
            return (IndexStore<T>) getIndexesFor(entityClass).get( indexName );
        } catch ( ClassCastException e ) {
            throw new IllegalArgumentException( "Index '"+indexName+"' on " + entityClass + " is not a range index");
        }
    }

    public <T> UniqueIndexStore<T> getUniqueIndex( Class<T> entityClass, String indexName ) {
        try {
            return (UniqueIndexStore<T>) getIndexesFor(entityClass).get( indexName );
        } catch ( ClassCastException e ) {
            throw new IllegalArgumentException( "Index '"+indexName+"' on " + entityClass + " is not a unique index");
        }
    }

    public <T> GISIndexStore<T> getGISIndex( Class<T> entityClass, String indexName ) {
        try {
            return (GISIndexStore<T>) getIndexesFor(entityClass).get( indexName );
        } catch ( ClassCastException e ) {
            throw new IllegalArgumentException( "Index '"+indexName+"' on " + entityClass + " is not a GIS index");
        }
    }

    public synchronized <T> int count( Class<T> entityClass ) {
        Map store = fetchStoreFor( entityClass );

        return store == null ? 0 : store.size();
    }


    public synchronized void close() throws Exception {
    }

    private <T> ConcurrentMap<String,IndexStore> getIndexesFor( Class<T> entityClass ) {
        ConcurrentMap<String,IndexStore> entityIndexes = indexes.get(entityClass);

        if ( entityIndexes == null ) {
            RootDocumentMeta rootDocumentMeta = factory.fetchRootDocumentMetaFor( entityClass );

            entityIndexes = new ConcurrentHashMap<String, IndexStore>();
            for ( IndexMeta<T> indexMeta : ((Collection<IndexMeta<T>>) rootDocumentMeta.indexes()) ) {
                IndexStore<T> index = createIndexFor( indexMeta );

                entityIndexes.put( indexMeta.getName(), index );
            }

            indexes.put( entityClass, entityIndexes );
        }

        return entityIndexes;
    }

    private <T> IndexStore<T> createIndexFor( IndexMeta<T> indexMeta ) {
        switch ( indexMeta.getIndexType() ) {
            case RANGE_INDEX:

            case UNIQUE_INDEX:
                return new UniqueIndexTreeStore<T>( indexMeta );
            case GIS_INDEX:
                return new GISIndexInMemoryArrayStore<T>( indexMeta );
            default:
                throw new UnsupportedOperationException( indexMeta.getIndexType().name() );
        }
    }

    private ConcurrentMap fetchStoreFor( Class entityClass ) {
        ConcurrentMap store = entityStores.get( entityClass );
        if ( store == null ) {
            store = new ConcurrentHashMap();
            ConcurrentMap previousValue = entityStores.putIfAbsent( entityClass, store );
            if ( previousValue != null ) { // in event of concurrent modifications, avoid having different stores
                store = previousValue;
            }
        }

        return store;
    }
}
