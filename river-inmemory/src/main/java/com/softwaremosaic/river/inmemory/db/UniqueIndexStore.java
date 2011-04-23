package com.softwaremosaic.river.inmemory.db;

import com.google.common.base.Predicate;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;

/**
 *
 */
public interface UniqueIndexStore<T> extends IndexStore<T> {
    public T fetchNbl( Object[] fullKey );

    public PagedCollection<T> scan( Class<T> expectedEntityClass, PagedCollection<T> result, Repository repo, Object[] partialSearchKey, Predicate<T> filter, ScanDirection direction, Object previousRowId );

    public int count( Object[] partialSearchKey, Predicate filter );
}
