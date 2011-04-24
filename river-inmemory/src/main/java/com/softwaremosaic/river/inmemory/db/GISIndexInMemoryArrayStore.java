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
public class GISIndexInMemoryArrayStore<K,T> implements GISIndexStore<K,T> {
    private IndexMeta indexMeta;
    
    private Deque<Node<K,T>> nodes = new LinkedList<Node<K,T>>();


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

        Iterator<Node<K,T>> it = nodes.iterator();
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
    public synchronized PagedCollection<T> scan( Repository<K,T> callingRepository, LatLon centrePoint, long distanceInMeters, Predicate<T> filter, int maxPageCount, K previousEntityId ) {
        TreeMap<Double,K> orderedMatches = new TreeMap<Double, K>();

        for ( Node<K,T> node : nodes ) {
            if ( node.hasLocation() ) {
                double d = node.getDistanceTo( centrePoint );
                if ( d <= distanceInMeters ) {
                    orderedMatches.put(d,node.getId());
                }
            }
        }

        Class entityClass = indexMeta.getEntityClass();
        PagedCollection<T> results = new PagedCollection<T>( maxPageCount );
        for ( K id : orderedMatches.values() ) {
            T entity = callingRepository.fetchNbl( id );
            if ( entity != null ) {
                results.add( entity );
            }

            if ( results.hasOverflowed() ) { break; }
        }

        return results;
    }

    private static class Node<K,T> {
        private K      id;
        private LatLon latLonNbl;

        public Node( K id, LatLon latLon ) {
            this.id = id;
            this.latLonNbl = latLon;
        }

        public K getId() {
            return id;
        }

        public boolean considerEntityChangedEvent( K targetId, LatLon oldLatLon, LatLon newLatLon ) {
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
