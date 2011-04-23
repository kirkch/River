package com.softwaremosaic.river.inmemory;

import com.google.common.base.Predicate;
import com.mosaic.gis.LatLon;
import com.softwaremosaic.river.GISIndex;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.inmemory.db.GISIndexStore;

/**
 * Stores all lat lons in an array. Performs a full index scan for every search. This index will not scale, however it
 * does offer a quick solution for low volumes of traffic.
 */
@SuppressWarnings({"unchecked"})
public class GISIndexInMemory<T> implements GISIndex<T> {
    private Repository       callingRepository;
    private GISIndexStore<T> indexStore;

    public GISIndexInMemory( Repository callingRepository, GISIndexStore<T> indexStore ) {
        this.callingRepository = callingRepository;
        this.indexStore = indexStore;
    }

    @Override
    public synchronized PagedCollection<T> scan( LatLon centrePoint, long distanceInMeters, Predicate<T> filter, int maxPageCount, Object previousEntityId ) {
        return indexStore.scan( callingRepository, centrePoint, distanceInMeters, filter, maxPageCount, previousEntityId );
    }

}
