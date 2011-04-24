package com.softwaremosaic.river.inmemory.db;

import com.google.common.base.Predicate;
import com.mosaic.gis.LatLon;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;

/**
 *
 */
public interface GISIndexStore<K,T> extends IndexStore<T> {
    public PagedCollection<T> scan( Repository<K,T> callingRepository, LatLon centrePoint, long distanceInMeters, Predicate<T> filter, int maxPageCount, K previousEntityId );
}
