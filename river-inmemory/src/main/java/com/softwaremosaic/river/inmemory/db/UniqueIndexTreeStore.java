package com.softwaremosaic.river.inmemory.db;

import com.google.common.base.Predicate;
import com.mosaic.lang.Validate;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;
import com.softwaremosaic.river.inmemory.UniqueIndexInMemory;
import com.softwaremosaic.river.meta.IndexMeta;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 */
@SuppressWarnings({"unchecked"})
public class UniqueIndexTreeStore<K,T> implements UniqueIndexStore<K,T> {
    private ConcurrentNavigableMap<Object,UniqueIndexInMemory> rootNode = new ConcurrentSkipListMap();

    private IndexMeta indexMeta;

    private int maxDepth;

    public UniqueIndexTreeStore( IndexMeta indexMeta ) {
        this.indexMeta = indexMeta;
        this.maxDepth  = indexMeta.getKeySize()-2;
    }


    @Override
    public T fetchNbl( Object[] fullKey ) {
        ConcurrentNavigableMap leafTree = fetchOrCreateLeafTree( rootNode, fullKey, 0 );

        return (T) leafTree.get( fullKey[fullKey.length - 1] );
    }


    // Scan works by creating a stack of iterators, where each iterator is an iterator for a node in the storage tree.
    // This stack is used to give an in order walk of the data tree. Skipping and/or resuming parts of the scan is done
    // by creating this stack pre-configured to skip the start of the scan.

    @Override
    public PagedCollection<T> scan( PagedCollection<T> result, Repository<K,T> repo, Object[] partialSearchKey, Predicate<T> filter, ScanDirection direction, Object previousRowId ) {
        Object[] previousRow = extractPreviousRowKeyFrom( repo, previousRowId );
        Iterator<K> it = iterator(partialSearchKey,direction,previousRow);

        while ( result.hasCapacityLeft() && it.hasNext() ) {
            K id = it.next();
            T entity = repo.fetchNbl( id );

            if ( entity == null ) continue;

            if ( filter != null && !filter.apply(entity) ) continue;

            result.add( entity );
        }

        return result;
    }

    @Override
    public int count( Object[] partialSearchKey, Predicate filter ) {
        ConcurrentNavigableMap startingMap = resolveRootOfPartialSearchKey( partialSearchKey );

        // NB considered normalising filter with Predicates.ALWAYS_TRUE if it was null.. however that would
        // prevent us from applying an optimisation as when the filter is null there is no need to iterate over
        // every value within the leaf maps
        return count ( startingMap, filter );
    }

    @Override
    public Class<Object> getEntityClass() {
        return indexMeta.getEntityClass();
    }

    @Override
    public void entityCreated( T entity ) {
        Object[] key = indexMeta.getKeyFor( entity );
        Object   id  = indexMeta.getIdFor( entity );

        this.insert( key, id );
    }

    @Override
    public void entityChanged( T oldValue, T newValue ) {
        Object[] oldKey = indexMeta.getKeyFor( oldValue );
        Object[] newKey = indexMeta.getKeyFor( newValue );

        assert indexMeta.getIdFor(oldValue).equals( indexMeta.getIdFor( newValue ) ) : "id was changed";

        if ( !Arrays.equals( oldKey, newKey ) ) {
            this.move( oldKey, newKey );
        }
    }

    @Override
    public void entityRemoved( T entity ) {
        Object[] key = indexMeta.getKeyFor( entity );
        Object   id  = indexMeta.getIdFor( entity );

        this.remove( key, id );
    }

    @Override
    public String toString() {
        return indexMeta.toString();
    }

    boolean insert( Object[] fullKey, Object id ) {
        ConcurrentNavigableMap leafTree = fetchOrCreateLeafTree( rootNode, fullKey, 0 );
        Object previousValue = leafTree.putIfAbsent( fullKey[fullKey.length - 1], id );

        return previousValue == null;
    }


    boolean remove( Object[] fullKey, Object expectedValue ) {
        ConcurrentNavigableMap leafTree = fetchOrCreateLeafTree( rootNode, fullKey, 0 );

        return leafTree.remove( fullKey[fullKey.length-1], expectedValue );
    }

    boolean move( Object[] srcFullKey, Object[] destFullKey ) {
        ConcurrentNavigableMap srcLeafTree  = fetchOrCreateLeafTree( rootNode, srcFullKey, 0 );

        Object value = srcLeafTree.get( srcFullKey[srcFullKey.length-1] );
        if ( !insert(destFullKey,value) ) {
            return false;
        }

        if ( !remove(srcFullKey,value) ) {
            remove( destFullKey, value );
            return false;
        }

        return true;
    }

    int count( ConcurrentNavigableMap map, Predicate filter ) {
        if ( map.isEmpty() ) return 0;

        Object v = map.firstEntry().getValue();
        if ( v instanceof ConcurrentNavigableMap ) {
            int sum = 0;
            for ( Object child : map.values() ) {
                sum += count( (ConcurrentNavigableMap) child, filter );
            }

            return sum;
        } else if ( filter == null ) {
            return map.size();
        } else {
            int count = 0;
            for ( Object child : map.values() ) {
                if ( filter.apply(child) ) {
                    count++;
                }
            }

            return count;
        }
    }


    private Iterator iterator( Object[] partialSearchKey, final ScanDirection direction, Object[] previousRow ) {
        final Deque<Iterator> stack = initialiseScanStack( partialSearchKey, direction, previousRow );

        return new Iterator() {
            private Object nextValue = pullNextFromIterators();


            @Override
            public boolean hasNext() {
                return nextValue != null;
            }

            @Override
            public Object next() {
                Object r = nextValue;
                nextValue = pullNextFromIterators();

                return r;
            }

            private Object pullNextFromIterators() {
                Iterator it = getNextNonEmptyIterator();
                if ( it == null ) { return null; }

                Object v = it.next();
                if ( v instanceof ConcurrentNavigableMap ) {
                    ConcurrentNavigableMap nextChildMap = (ConcurrentNavigableMap) v;

                    if ( !nextChildMap.isEmpty() ) {
                        nextChildMap = direction == ScanDirection.ASC ? nextChildMap : nextChildMap.descendingMap();

                        stack.addLast( nextChildMap.values().iterator() );
                    }

                    return this.pullNextFromIterators();
                } else {
                    return v;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private Iterator getNextNonEmptyIterator() {
                if ( stack.isEmpty() ) return null;

                Iterator it = stack.getLast();
                if ( !it.hasNext() ) {
                    stack.removeLast();
                    return this.getNextNonEmptyIterator();
                }

                return it;
            }
        };
    }


    private Deque<Iterator> initialiseScanStack( Object[] partialSearchKey, ScanDirection direction, Object[] previousRow ) {
        ConcurrentNavigableMap startingMap = resolveRootOfPartialSearchKey( partialSearchKey );

        final Deque<Iterator> stack = new ArrayDeque<Iterator>();

        if ( previousRow == null ) {
            ConcurrentNavigableMap orderedMap = direction.isAscending() ? startingMap : startingMap.descendingMap();
            stack.addLast( orderedMap.values().iterator() );
        } else {
            final int maxLength = indexMeta.getKeySize();
            Validate.isEqualTo( previousRow.length, maxLength, "The specified previousRow key is of length %d, we expected %d" );

            Object next = startingMap;
            int partialSearchKeyLength = partialSearchKey == null ? 0 : partialSearchKey.length;
            for ( int i= partialSearchKeyLength; i<maxLength; i++ ) {
                ConcurrentNavigableMap nextMap = (ConcurrentNavigableMap) next;

                ConcurrentNavigableMap restrictedMap;
                if ( direction.isAscending() ) {
                    restrictedMap = nextMap.subMap( previousRow[i], false, nextMap.lastKey(), true );
                } else {
                    restrictedMap = nextMap.subMap( nextMap.firstKey(), true, previousRow[i], false );
                }

                ConcurrentNavigableMap restrictedOrderedMap = direction.isAscending() ? restrictedMap : restrictedMap.descendingMap();
                stack.addLast( restrictedOrderedMap.values().iterator() );

                next = nextMap.get( previousRow[i] );
            }
        }

        return stack;
    }



    private ConcurrentNavigableMap resolveRootOfPartialSearchKey( Object[] partialSearchKey ) {
        if ( partialSearchKey == null || partialSearchKey.length == 0 ) { return rootNode; }

        Validate.isLT( partialSearchKey.length, indexMeta.getKeySize(), "specified partial search key is too large for this index, the specified key was of length %d while the max length that is supported is %d" );

        ConcurrentNavigableMap currentNode = rootNode;
        int numElementsToCompare = partialSearchKey.length;
        for ( int i=0; i<numElementsToCompare; i++ ) {
            Object key = partialSearchKey[i];

            currentNode = (ConcurrentNavigableMap) currentNode.get( key );
        }

        return currentNode;
    }

    private Object[] extractPreviousRowKeyFrom( Repository<K,T> repo, Object previousRowId ) {
        if ( previousRowId == null ) return null;
        if ( previousRowId.getClass().isArray() ) return (Object[]) previousRowId;

        Object entity = repo.fetchNbl( (K) previousRowId );
        return entity == null ? null : indexMeta.getKeyFor( entity );
    }

    private ConcurrentNavigableMap fetchOrCreateLeafTree( ConcurrentNavigableMap parentStore, Object[] fullKey, int depth ) {
        Validate.isEqualTo( indexMeta.getKeySize(), fullKey.length, "%d is not equal to %d" );

        if ( depth > maxDepth ) {
            return parentStore;
        }

        ConcurrentNavigableMap childStore = (ConcurrentNavigableMap) parentStore.get(fullKey[depth]);
        if ( childStore == null ) {
            childStore = new ConcurrentSkipListMap();
            parentStore.put( fullKey[depth], childStore );
        }

        return fetchOrCreateLeafTree( childStore, fullKey, depth+1 );
    }
}
