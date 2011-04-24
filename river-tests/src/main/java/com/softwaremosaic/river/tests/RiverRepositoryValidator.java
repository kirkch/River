package com.softwaremosaic.river.tests;

import com.mosaic.gis.LatLon;
import com.mosaic.lang.Closure;
import com.softwaremosaic.river.DocumentNotFoundException;
import com.softwaremosaic.river.GISIndex;
import com.softwaremosaic.river.PagedCollection;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.ScanDirection;
import com.softwaremosaic.river.UniqueIndex;

import javax.security.auth.login.AccountException;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 */
@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "unchecked"})
public class RiverRepositoryValidator {
    public static void validate( Repository repo ) {
        testCreate( repo );
        testDelete( repo );
        testForEach( repo );
        testUniqueIndexUsage( repo );
        testGISIndexUsage( repo );
    }

    private static void testCreate( Repository repo ) {
        showThatRepositoryStartsEmpty( repo );
        showThatAnObjectStoredCanBeRetrievedByKey( repo );
        showThatFetchingANoneExistantEntityFromFetchNblErrors( repo );
        showThatModifyingAnEntityAfterCreationOrFetchingDoesNotModifyThePersistedEntity( repo );
    }

    private static void testDelete( Repository repo ) {
        showThatAnEntityCanBeRemoved( repo );
    }

    private static void testForEach( Repository repo ) {
        showThatEachEntityWillBeIteratedOverModifyNone( repo );
        showThatEachEntityWillBeIteratedOverModifyAll( repo );
    }

    private static void testUniqueIndexUsage( Repository repo ) {
        showThatUniqueIndexCanBeRead( repo );
//        showThatUniqueIndexIsUpdatedWhenEntityIsChanged( repo ); todo
//        showThatRemovingAEntityRemovesItFromTheIndex( repo );
//        showThatIndexCanBePaged( repo );
    }


    private static void testGISIndexUsage( Repository repo ) {
        demoGISIndexByScanningTwoNearbyPlaces( repo );
    }

    private static void showThatRepositoryStartsEmpty( Repository repo ) {
        assertNull( repo.fetchNbl(AccountException.class, "10") );
    }

    private static void showThatAnObjectStoredCanBeRetrievedByKey( Repository repo )  {
        repo.create( new Account("a", "b") );

        assertNotNull( repo.fetchNbl( Account.class, "10" ) );
        assertNotNull( repo.fetch( Account.class, "10" ) );
        assertNull( repo.fetchNbl( Account.class, "11" ) );
    }

    private static void showThatFetchingANoneExistantEntityFromFetchNblErrors( Repository repo ) {
        try {
            repo.fetch( Account.class, "5" );
            fail( "expected EntityNotFoundException" );
        } catch ( DocumentNotFoundException e ) {
            assertEquals( Account.class, e.getEntityClass() );
            assertEquals( "5", e.getKey() );
        }
    }

    private static void showThatModifyingAnEntityAfterCreationOrFetchingDoesNotModifyThePersistedEntity( Repository repo ) {
        Account a1 = repo.fetch( Account.class, "10" );
        assertEquals( "a", a1.getName() );

        a1.setName( "Jim" );
        assertEquals( "Jim", a1.getName() );

        repo.clearCaches();

        Account a2 = repo.fetch( Account.class, "10" );
        assertTrue( a1 != a2 );
        assertEquals( "a", a2.getName() );

        repo.update( a1 );
        repo.clearCaches();

        Account a3 = repo.fetch( Account.class, "10" );
        assertEquals( "Jim", a3.getName() );

        a1.setName( "Jane" );
        assertEquals( "Jim", a3.getName() );
    }

    private static void showThatAnEntityCanBeRemoved( Repository repo ) {
        assertEquals( 1, repo.count(Account.class) );

        repo.delete( repo.fetch(Account.class, "10") );
        assertEquals( 0, repo.count(Account.class) );
        assertNull( repo.fetchNbl( Account.class, "10" ) );
    }

    private static void showThatEachEntityWillBeIteratedOverModifyNone( Repository repo ) {
        Account a1Original = repo.create( new Account("a1", "a") );
        Account a2Original = repo.create( new Account("a2", "a") );

        final AtomicInteger invokeCount = new AtomicInteger();
        int modCount = repo.forEach( Account.class, new Closure<Account>() {
            @Override
            public Account invoke( Account acc ) {
                invokeCount.incrementAndGet();
                acc.setName( acc.getName() + "1" );

                return null;
            }
        } );

        assertEquals( 0, modCount );
        assertEquals( 2, invokeCount.get() );

        // ensure that the username changes from the closure were not persisted
        assertEquals( "a1", repo.fetch(Account.class,a1Original.getId()).getName() );
        assertEquals( "a2", repo.fetch(Account.class,a2Original.getId()).getName() );

        // reset repo after test is complete
        repo.delete( a1Original );
        repo.delete( a2Original );
        assertEquals( 0, repo.count( Account.class ) );
    }


    private static void showThatEachEntityWillBeIteratedOverModifyAll( Repository repo ) {
        Account a1Original = repo.create( new Account("a1", "t") );
        Account a2Original = repo.create( new Account("a2", "r") );

        assertEquals( 2, repo.count(Account.class) );

        final AtomicInteger invokeCount = new AtomicInteger();
        int modCount = repo.forEach( Account.class, new Closure<Account>() {
            @Override
            public Account invoke( Account acc ) {
                invokeCount.incrementAndGet();
                acc.setName( acc.getName() + "1" );

                return acc;
            }
        } );

        assertEquals( 2, modCount );
        assertEquals( 2, invokeCount.get() );

        // ensure that the username changes from the closure were persisted
        assertEquals( "a11", repo.fetch(Account.class,a1Original.getId()).getName() );
        assertEquals( "a21", repo.fetch(Account.class,a2Original.getId()).getName() );
    }


    private static void showThatUniqueIndexCanBeRead( Repository repo ) {
        repo.create( new Account("jimbo", "savings") );
        UniqueIndex<Account> index = repo.getUniqueIndex( Account.class, "nameTypeUniqueIndex" );

        Account account = index.fetch( new Object[] {"jimbo", "savings"} );
        assertEquals( "jimbo", account.getName() );

        PagedCollection<Account> accounts = index.scan( null, null, ScanDirection.ASC, null, 10 );
        System.out.println( "accounts = " + accounts );
    }

    public static final LatLon WARREN_STREET_TUBE_STATION = new LatLon( "51.52478", "-0.13763" );
    public static final LatLon WATER_RATS_PUB             = new LatLon( "51.529091", "-0.11962" );

    private static void demoGISIndexByScanningTwoNearbyPlaces( Repository repo ) {
        Account a = new Account("warrenStreet", "tube");
        a.setLocation( WARREN_STREET_TUBE_STATION );

        Account b = new Account("waterRats", "bar");
        b.setLocation( WATER_RATS_PUB );

        repo.create( a );
        repo.create( b );

        GISIndex index = repo.getGISIndex( Account.class, "locationGISIndex" );
        PagedCollection<Account> results = index.scan( WATER_RATS_PUB, 10000, null, 10, null );

        assertEquals( 2, results.size() );
        assertEquals( "waterRats", results.get(0).getName() );
        assertEquals( "warrenStreet", results.get(1).getName() );


        results = index.scan( WATER_RATS_PUB, 500, null, 10, null );

        assertEquals( 1, results.size() );
        assertEquals( "waterRats", results.get(0).getName() );
    }
}
