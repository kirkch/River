package com.softwaremosaic.river;

import java.util.ArrayList;

/**
 *
 */
public class PagedCollection<T> extends ArrayList<T> {
    private int     maxSize;
    private boolean maxSizeExceeded;

    public PagedCollection( int maxSize ) {
        super( maxSize );

        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean hasOverflowed() {
        return maxSizeExceeded;
    }

    @Override
    public void add( int index, T element ) {
        if ( size() >= maxSize ) {
            this.maxSizeExceeded = true;
            return;
        }

        super.add( index, element );
    }

    @Override
    public boolean add( T t ) {
        if ( size() >= maxSize ) {
            this.maxSizeExceeded = true;
            return false;
        }

        return super.add( t );
    }

    public T getFirst() {
        return get(0);
    }

    public T getLast() {
        return get(size()-1);
    }

    public boolean hasCapacityLeft() {
        return !maxSizeExceeded;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
