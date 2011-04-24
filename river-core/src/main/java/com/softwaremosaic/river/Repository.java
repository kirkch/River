package com.softwaremosaic.river;

import com.mosaic.lang.Closure;
import com.mosaic.lang.Listenable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A repository offers Document persistence and lookup facilities. The persistence may occur locally within the same
 * JVM as the instance of this interface or it may be occurring remotely on another machine or machines.
 */
public interface Repository<K,T> extends Listenable<RepositoryListener> { //extends AutoCloseable {

    public void close();

    /**
     * Inserts the specified document into the repository. The document's primary key must be null, as the act
     * of inserting it will generate a primary key and the document will be updated and returned with the primary
     * key set. If the primary key is set, or a unique constraint is violated, or a callback class chooses to veto
     * the insert then this method will throw an exception.
     *
     * @param document the document to be persisted
     *
     * @return an updated copy of the document
     */
    public T create( T document );

    public List<K> createBatch( Iterable<T> document );

    /**
     * Remove the specified document from the repository.
     *
     * @return true if the primary key exists
     */
    public boolean delete( T document );

    public Set<K> deleteBatch( Iterable<T> document );

    /**
     * Removes the specified document from the repository.
     *
     * @return true if the primary key exists
     */
    public boolean deleteByPrimaryKey( K primaryKey );

    public Set<K> deleteByPrimaryKeyBatch( Iterable<K> primaryKey );


    public void update( T document );

    
    public void updateBatch( Iterable<T> document );

    public void clearCaches();

    public boolean isCached( K primaryKey );

    /**
     *
     * @param primaryKey
     * @return
     * @throws DocumentNotFoundException when an document is not found
     */
    public T fetch( K primaryKey ) throws DocumentNotFoundException;

    /**
     *
     * @param primaryKey
     *
     * @return returns null if an document is not found
     */
    public T fetchNbl( K primaryKey ) ;

    public Map<K,T> fetchBatch( Iterable<K> primaryKey ) ;


    public Index<T> getIndex( String indexName );
    public UniqueIndex<T> getUniqueIndex( String indexName );
    public GISIndex<T> getGISIndex( String indexName );


    public int countAll();

    /**
     * Performs a full table scan. For each value of type 'documentClass' closure will be invoked. The order of
     * execution is not guaranteed, depending on implementation it may even happen in parallel locally or distributed.
     * If the closure 'returns' the document, then it will be updated.
     *
     *
     *
     * @param closure will be invoked for every value within the repository of type.. if the closure returns a value
     * then that value will be updated. null will not persist any changes.
     * @return the number of rows modified
     */
    public int forEach( Closure<T> closure );
}
