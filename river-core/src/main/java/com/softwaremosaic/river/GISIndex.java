package com.softwaremosaic.river;

import com.google.common.base.Predicate;
import com.mosaic.gis.LatLon;

/**
 *
 */
public interface GISIndex<T> {
    public PagedCollection<T> scan( LatLon point, long distanceInMeters, Predicate<T> filter, int maxPageCount, Object previousEntityId );
}
