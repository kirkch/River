package com.softwaremosaic.river.inmemory.db;

import com.google.common.base.Predicate;
import com.mosaic.lang.Closure;
import com.softwaremosaic.river.EntityNotFoundException;
import com.softwaremosaic.river.GISIndex;
import com.softwaremosaic.river.Index;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;
import com.softwaremosaic.river.UniqueIndex;
import com.softwaremosaic.river.meta.IndexMeta;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
@SuppressWarnings({"unchecked", "AssertEqualsBetweenInconvertibleTypes"})
public class UniqueIndexTreeStoreTest {
    private IndexMeta<String> indexMetaMock;

    @Before
    public void initMocks() {
        indexMetaMock = Mockito.mock( IndexMeta.class );

        Mockito.when( indexMetaMock.getEntityClass() ).thenReturn( String.class );
    }

    @Test
    public void showThatACompositeKeyCanBeAddedRetrievedMovedAndRemovedOnASingleValueKey() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 1 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        assertEquals( 0, index.count((Object[]) null, null) );

        Object[] originalKey = {1};
        assertTrue( index.insert( originalKey, "Monty Python" ) );
        assertEquals( "Monty Python", index.fetchNbl( originalKey ) );
        assertEquals( 1, index.count((Object[]) null,null) );

        Object[] newKey = {3};
        assertTrue( index.move( originalKey, newKey ) );
        assertNull( index.fetchNbl(originalKey) );
        assertEquals( "Monty Python", index.fetchNbl(newKey) );
        assertEquals( 1, index.count((Object[]) null,null) );

        assertTrue( index.remove( newKey, "Monty Python" ) );
        assertNull( index.fetchNbl( originalKey ) );
        assertNull( index.fetchNbl(newKey) );
        assertEquals( 0, index.count((Object[]) null,null) );
    }

    @Test
    public void showThatACompositeKeyCanBeAddedRetrievedMovedAndRemovedOnACompositeKey() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 3 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        Object[] originalKey = {1, 2, 3};
        assertTrue( index.insert( originalKey, "Monty Python" ) );
        assertEquals( "Monty Python", index.fetchNbl( originalKey ) );

        Object[] newKey = {3, 2, 3};
        assertTrue( index.move( originalKey, newKey ) );
        assertNull( index.fetchNbl(originalKey) );
        assertEquals( "Monty Python", index.fetchNbl(newKey) );

        assertTrue( index.remove( newKey, "Monty Python" ) );
        assertNull( index.fetchNbl( originalKey ) );
        assertNull( index.fetchNbl(newKey) );
    }

    @Test
    public void showThatAValueCannotBeInsertedIfTheKeyIsAlreadyUsed() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 3 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        Object[] originalKey = {1, 2, 3};
        assertNull( index.fetchNbl( originalKey ) );
        assertTrue( index.insert( originalKey, "Monty Python" ) );
        assertFalse( index.insert(originalKey, "Songify") );

        assertEquals( "Monty Python", index.fetchNbl( originalKey ) );
    }

    @Test
    public void showThatAValueCannotBeMovedOntoAnotherKeyThatAlreadyExists() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 3 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        Object[] key1 = {1, 2, 3};
        Object[] key2 = {1, 5, 3};

        assertTrue( index.insert( key1, "Monty Python" ) );
        assertTrue( index.insert( key2, "Songify" ) );


        assertFalse( index.move(key1, key2) );
        assertEquals( "Monty Python", index.fetchNbl( key1 ) );
        assertEquals( "Songify", index.fetchNbl( key2 ) );
    }

    @Test
    public void showThatAValueCannotBeRemovedIfItIsNotTheExpectedValue() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 3 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        Object[] fullKey = {1, 2, 3};
        assertTrue( index.insert(fullKey, "Monty Python") );
        assertFalse( index.remove( fullKey, "monty python" ) );

        assertEquals( "Monty Python", index.fetchNbl(fullKey) );
    }

    @Test
    public void demoAnAscendingFullTableScan() {
        UniqueIndexTreeStore index = createAndInitNewStore();


        Repository repoMock = new MyMockRepository();

        PagedCollection<String> result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.ASC, null );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1111)", result.get(0) );
        assertEquals( "E(1112)", result.get(1) );
        assertEquals( "E(1113)", result.get(2) );
        assertEquals( "E(1121)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.ASC, new Comparable[] {1,1,2,1} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1122)", result.get(0) );
        assertEquals( "E(1123)", result.get(1) );
        assertEquals( "E(1131)", result.get(2) );
        assertEquals( "E(1132)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.ASC, new Comparable[] {1,1,3,2} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1133)", result.get(0) );
        assertEquals( "E(1211)", result.get(1) );
        assertEquals( "E(1212)", result.get(2) );
        assertEquals( "E(1213)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.ASC, new Comparable[] {1,2,1,3} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1221)", result.get(0) );
        assertEquals( "E(1222)", result.get(1) );
        assertEquals( "E(1223)", result.get(2) );
        assertEquals( "E(2111)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.ASC, new Comparable[] {2,1,1,1} );

        assertFalse( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(2112)", result.get(0) );
        assertEquals( "E(2113)", result.get(1) );
    }

    @Test
    public void demoAnAscendingAPartialTableScan() {
        UniqueIndexTreeStore index = createAndInitNewStore();


        Repository repoMock = new MyMockRepository();

        PagedCollection<String> result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, null, ScanDirection.ASC, null );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1211)", result.get(0) );
        assertEquals( "E(1212)", result.get(1) );
        assertEquals( "E(1213)", result.get(2) );
        assertEquals( "E(1221)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, null, ScanDirection.ASC, new Comparable[] {1,2,2,1} );

        assertFalse( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(1222)", result.get(0) );
        assertEquals( "E(1223)", result.get(1) );
    }

    @Test
    public void demoADescendingFullTableScan() {
        UniqueIndexTreeStore index = createAndInitNewStore();


        Repository repoMock = new MyMockRepository();

        PagedCollection<String> result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, null );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(2113)", result.get(0) );
        assertEquals( "E(2112)", result.get(1) );
        assertEquals( "E(2111)", result.get(2) );
        assertEquals( "E(1223)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, new Comparable[] {1,2,2,3} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1222)", result.get(0) );
        assertEquals( "E(1221)", result.get(1) );
        assertEquals( "E(1213)", result.get(2) );
        assertEquals( "E(1212)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, new Comparable[] {1,2,1,2} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1211)", result.get(0) );
        assertEquals( "E(1133)", result.get(1) );
        assertEquals( "E(1132)", result.get(2) );
        assertEquals( "E(1131)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, new Comparable[] {1,1,3,1} );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1123)", result.get(0) );
        assertEquals( "E(1122)", result.get(1) );
        assertEquals( "E(1121)", result.get(2) );
        assertEquals( "E(1113)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, new Comparable[] {1,1,1,3} );

        assertFalse( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(1112)", result.get(0) );
        assertEquals( "E(1111)", result.get(1) );
    }

    @Test
    public void demoADescendingPartialTableScan() {
        UniqueIndexTreeStore index = createAndInitNewStore();


        Repository repoMock = new MyMockRepository();

        PagedCollection<String> result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, null, ScanDirection.DESC, null );

        assertTrue( result.hasOverflowed() );
        assertEquals( 4, result.size() );
        assertEquals( "E(1223)", result.get(0) );
        assertEquals( "E(1222)", result.get(1) );
        assertEquals( "E(1221)", result.get(2) );
        assertEquals( "E(1213)", result.get(3) );


        result = new PagedCollection<String>(4);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, null, ScanDirection.DESC, new Comparable[] {1,2,1,3} );

        assertFalse( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(1212)", result.get(0) );
        assertEquals( "E(1211)", result.get(1) );
    }

    @Test
    public void demoFilteringAResultList() {
        UniqueIndexTreeStore index = createAndInitNewStore();


        Repository repoMock = new MyMockRepository();

        Predicate<String> filter = new Predicate<String>() {
            @Override
            public boolean apply( String input ) {
                return input.contains("21");
            }
        };

        PagedCollection<String> result = new PagedCollection<String>(2);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, filter, ScanDirection.DESC, null );

        assertTrue( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(1221)", result.get(0) );
        assertEquals( "E(1213)", result.get(1) );



        result = new PagedCollection<String>(2);
        index.scan( String.class, result, repoMock, new Comparable[] {1,2}, filter, ScanDirection.DESC, new Comparable[] {1,2,1,3} );

        assertFalse( result.hasOverflowed() );
        assertEquals( 2, result.size() );
        assertEquals( "E(1212)", result.get( 0 ) );
        assertEquals( "E(1211)", result.get(1) );
    }

    /**
     * This test was added because the implementation of the index stores empty maps within a tree of maps. When iterating
     * over these empty maps some bugs crept in where iterators would return true for hasNext (because the iterator held
     * a map) but error on next because the map was empty.
     */
    @Test
    public void scanAnIndexThatUsedToHoldValues() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 4 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );
        Repository repoMock = new MyMockRepository();

        index.insert( new Object[] {1,1,1,1}, "1111" );
        assertEquals( 1, index.count((Object[]) null,null) );
        index.insert( new Object[] {2,1,1,2}, "2112" );
        assertEquals( 2, index.count((Object[]) null,null) );
        index.insert( new Object[] {2,3,1,2}, "2312" );
        assertEquals( 3, index.count((Object[]) null,null) );

        index.remove( new Object[] {1,1,1,1}, "1111" );
        assertEquals( 2, index.count((Object[]) null,null) );
        index.remove( new Object[] {2,1,1,2}, "2112" );
        assertEquals( 1, index.count((Object[]) null,null) );
        index.remove( new Object[] {2,3,1,2}, "2312" );
        assertEquals( 0, index.count((Object[]) null,null) );



        PagedCollection<String> result = new PagedCollection<String>(2);
        index.scan( String.class, result, repoMock, null, null, ScanDirection.DESC, null );

        assertEquals( 0, result.size() );

    }

    private UniqueIndexTreeStore createAndInitNewStore() {
        Mockito.when( indexMetaMock.getKeySize() ).thenReturn( 4 );

        UniqueIndexTreeStore index = new UniqueIndexTreeStore( indexMetaMock );

        index.insert( new Object[] {1,1,1,1}, "1111" );
        index.insert( new Object[] {1,1,1,2}, "1112" );
        index.insert( new Object[] {1,1,1,3}, "1113" );

        index.insert( new Object[] {1,1,2,1}, "1121" );
        index.insert( new Object[] {1,1,2,2}, "1122" );
        index.insert( new Object[] {1,1,2,3}, "1123" );

        index.insert( new Object[] {1,1,3,1}, "1131" );
        index.insert( new Object[] {1,1,3,2}, "1132" );
        index.insert( new Object[] {1,1,3,3}, "1133" );

        index.insert( new Object[] {1,2,1,1}, "1211" );
        index.insert( new Object[] {1,2,1,2}, "1212" );
        index.insert( new Object[] {1,2,1,3}, "1213" );

        index.insert( new Object[] {1,2,2,1}, "1221" );
        index.insert( new Object[] {1,2,2,2}, "1222" );
        index.insert( new Object[] {1,2,2,3}, "1223" );

        index.insert( new Object[] {2,1,1,1}, "2111" );
        index.insert( new Object[] {2,1,1,2}, "2112" );
        index.insert( new Object[] {2,1,1,3}, "2113" );

        assertEquals( 18, index.count((Object[]) null,null) );

        return index;
    }

    private static class MyMockRepository implements Repository  {

        @Override
        public <T> T create( T entity ) {
            return null;
        }

        @Override
        public void delete( Object entity ) {
        }

        @Override
        public void update( Object entity ) {
        }

        @Override
        public void clearFirstLevelCache() {
        }

        @Override
        public <T> T fetch( Class<T> entityClass, Object primaryKey ) throws EntityNotFoundException {
            return null;
        }

        @Override
        public <T> T fetchNbl( Class<T> entityClass, Object primaryKey ) {
            return (T) ("E(" + primaryKey + ")");
        }

        @Override
        public <T> Index<T> getIndex( Class<T> entityClass, String indexName ) {
            return null;
        }

        @Override
        public <T> UniqueIndex<T> getUniqueIndex( Class<T> entityClass, String indexName ) {
            return null;
        }

        @Override
        public <T> GISIndex<T> getGISIndex( Class<T> expectedType, String indexName ) {
            return null;
        }

        @Override
        public <T> int count( Class<T> entityClass ) {
            return 0;
        }

        @Override
        public <T> int forEach( Class<T> entityClass, Closure<T> closure ) {
            return 0;
        }

        public void close() throws Exception {
        }
    }
}
