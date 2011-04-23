package com.softwaremosaic.river;


import com.google.common.base.Predicate;

/**
 *
 */
public interface Index<T> {
    public void setMaxPageSize( int maxRows );
    public int getMaxPageSize();



    public PagedCollection<T> scan( Predicate<T> filter, ScanDirection direction, Object...previousRow );
    
    public boolean contains( Object...key );

    public int count( Predicate<T> filter, Object...key );
}
