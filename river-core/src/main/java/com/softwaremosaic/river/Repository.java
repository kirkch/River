package com.softwaremosaic.river;

import com.mosaic.lang.Closure;

/**
 *
 */
public interface Repository { //extends AutoCloseable {

    public <T> T create( T entity );
    public void delete( Object entity );
    public void update( Object entity );

    public void clearFirstLevelCache();

    /**
     *
     * @param entityClass
     * @param primaryKey
     * @param <T>
     * @return
     * @throws EntityNotFoundException when an entity is not found
     */
    public <T> T fetch( Class<T> entityClass, Object primaryKey ) throws EntityNotFoundException;

    /**
     *
     * @param entityClass
     * @param primaryKey
     * @param <T>
     *
     * @return returns null if an entity is not found
     */
    public <T> T fetchNbl( Class<T> entityClass, Object primaryKey ) ;


//    public <I> I getIndexT( Class<I> indexType, String indexName );

    public <T> Index<T> getIndex( Class<T> entityClass, String indexName );
    public <T> UniqueIndex<T> getUniqueIndex( Class<T> entityClass, String indexName );
    public <T> GISIndex<T> getGISIndex( Class<T> entityClass, String indexName );


    public <T> int count( Class<T> entityClass );

    /**
     * Performs a full table scan. For each value of type 'entityClass' closure will be invoked. The order of
     * execution is not guaranteed, depending on implementation it may even happen in parallel locally or distributed.
     * If the closure 'returns' the entity, then it will be updated.
     *
     *
     *
     * @param entityClass
     * @param closure will be invoked for every value within the repository of type.. if the closure returns a value
     * then that value will be updated. null will not persist any changes.
     * @return the number of rows modified
     */
    public <T> int forEach( Class<T> entityClass, Closure<T> closure );
}
