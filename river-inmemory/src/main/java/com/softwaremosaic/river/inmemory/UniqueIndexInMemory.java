package com.softwaremosaic.river.inmemory;

import com.google.common.base.Predicate;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;
import com.softwaremosaic.river.UniqueIndex;
import com.softwaremosaic.river.inmemory.db.UniqueIndexStore;

import java.util.Arrays;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class UniqueIndexInMemory<T> implements UniqueIndex<T> {
    private Repository       owningRepository;
    private UniqueIndexStore indexStore;

    public UniqueIndexInMemory( Repository owningRepository, UniqueIndexStore<T> indexStore ) {
        this.owningRepository = owningRepository;
        this.indexStore       = indexStore;
    }

    @Override
    public T fetch( Object[] fullKey ) {
        T entity = fetchNbl( fullKey );
        if ( entity == null ) {
            throw new IllegalStateException( "no entity found for composite key " + Arrays.asList(fullKey) + " on index " + indexStore );
        }

        return entity;
    }

    @Override
    public T fetchNbl( Object[] fullKey ) {
        Object pk = indexStore.fetchNbl( fullKey );
        if ( pk != null ) {
            return (T) owningRepository.fetchNbl( indexStore.getEntityClass(), pk );
        }

        return null;
    }

    @Override
    public PagedCollection<T> scan( Object[] partialSearchKey, Predicate<T> filter, ScanDirection direction, Object previousRowId, int maxPageSize ) {
        return indexStore.scan( indexStore.getEntityClass(), new PagedCollection<T>(maxPageSize), owningRepository, partialSearchKey, filter, direction, previousRowId );
    }

    @Override
    public boolean contains( Object[] key ) {
        return indexStore.fetchNbl( key ) != null;
    }

    @Override
    public int count( Object[] partialSearchKey, Predicate<T> filter ) {
        return indexStore.count( partialSearchKey, filter );
    }


}
