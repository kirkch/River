package com.softwaremosaic.river.inmemory.db;

import com.google.common.base.Predicate;
import com.mosaic.gis.LatLon;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.meta.IndexMeta;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class GISIndexInMemoryArrayStore<T> implements GISIndexStore<T> {
    private IndexMeta indexMeta;
    
    private Deque<Node> nodes = new LinkedList<Node>();


    public GISIndexInMemoryArrayStore( IndexMeta<T> indexMeta ) {
        this.indexMeta = indexMeta;
    }

    @Override
    public void entityCreated( T entity ) {
        Object id     = indexMeta.getIdFor( entity );
        LatLon latLon = (LatLon) indexMeta.getKeyFor( entity )[0];

        nodes.add( new Node(id, latLon) );
    }

    @Override
    public void entityChanged( T oldValue, T newValue ) {
        Object id        = indexMeta.getIdFor( oldValue );
        LatLon oldLatLon = (LatLon) indexMeta.getKeyFor( oldValue )[0];
        LatLon newLatLon = (LatLon) indexMeta.getKeyFor( newValue )[0];

        if ( ObjectUtils.equals( oldLatLon, newLatLon ) ) { return; }

        for ( Node node : nodes ) {
            if ( node.considerEntityChangedEvent(id, oldLatLon, newLatLon) ) {
                return;
            }
        }
    }

    @Override
    public void entityRemoved( T entity ) {
        Object id = indexMeta.getIdFor( entity );

        Iterator<Node> it = nodes.iterator();
        while ( it.hasNext() ) {
            Node node = it.next();

            if ( node.getId().equals(id) ) {
                it.remove();
                return;
            }
        }
    }

    @Override
    public Class getEntityClass() {
        return indexMeta.getEntityClass();
    }

    @Override
    public synchronized PagedCollection<T> scan( Repository callingRepository, LatLon centrePoint, long distanceInMeters, Predicate<T> filter, int maxPageCount, Object previousEntityId ) {
        TreeMap<Double,Object> orderedMatches = new TreeMap<Double, Object>();

        for ( Node node : nodes ) {
            if ( node.hasLocation() ) {
                double d = node.getDistanceTo( centrePoint );
                if ( d <= distanceInMeters ) {
                    orderedMatches.put(d,node.getId());
                }
            }
        }

        Class entityClass = indexMeta.getEntityClass();
        PagedCollection<T> results = new PagedCollection<T>( maxPageCount );
        for ( Object id : orderedMatches.values() ) {
            T entity = (T) callingRepository.fetchNbl( entityClass, id );
            if ( entity != null ) {
                results.add( entity );
            }

            if ( results.hasOverflowed() ) { break; }
        }

        return results;
    }

    private static class Node {
        private Object id;
        private LatLon latLonNbl;

        public Node( Object id, LatLon latLon ) {
            this.id = id;
            this.latLonNbl = latLon;
        }

        public Object getId() {
            return id;
        }

        public boolean considerEntityChangedEvent( Object targetId, LatLon oldLatLon, LatLon newLatLon ) {
            if ( this.id.equals(targetId) && ObjectUtils.equals(this.latLonNbl, oldLatLon) ) {
                this.latLonNbl = newLatLon;

                return true;
            } else {
                return false;
            }
        }

        public boolean hasLocation() {
            return latLonNbl != null;
        }

        public double getDistanceTo( LatLon p ) {
            return p.distanceMeters( latLonNbl );
        }
    }
}
