package com.softwaremosaic.river;

import com.google.common.base.Predicate;

/**
 *
 */
public interface UniqueIndex<T> {
    public T fetch( Object[] key );
    public T fetchNbl( Object[] key );


    /**
     *
     * @param partialSearchKey
     * @param filter
     * @param direction
     * @param previousRowId either the pk or the composite key used on the index, uses the index key if previousRowId is
     *   an array of objects else it treats the previousRowId as the previous rows guid.
     * @param maxPageSize
     *
     * @return
     */
    public PagedCollection<T> scan( Object[] partialSearchKey, Predicate<T> filter, ScanDirection direction, Object previousRowId, int maxPageSize );

    public boolean contains( Object[] key );
    public int count( Object[] partialSearchKey, Predicate<T> filter );
}
