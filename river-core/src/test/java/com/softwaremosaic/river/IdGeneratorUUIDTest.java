package com.softwaremosaic.river;

import com.softwaremosaic.river.idgenerators.IdGeneratorUUID;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class IdGeneratorUUIDTest {
    @Test
    public void testGenerateIdFor() throws Exception {
        IdGenerator<String> gen = new IdGeneratorUUID();


        Set<String> uuids = new HashSet<String>();
        for ( int i=0; i<100; i++ ) {
            String uuid = gen.generateIdFor( null );

            assertNotNull( uuid );
            assertTrue( uuids.add(uuid) );
        }
    }
}
