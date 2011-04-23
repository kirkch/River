package com.softwaremosaic.river.mongodb;

import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.tests.RiverRepositoryValidator;
import org.junit.Test;

/**
 *
 */
//@Ignore
public class RepositoryManagerMongoDBIntegrationTest {
    @Test
    public void vo() {
        RepositoryManagerMongoDB m = new RepositoryManagerMongoDB( "sandbox" );

        try {
            Repository r = m.newSession();

            RiverRepositoryValidator.validate( r );
        } finally {
            m.close();
        }


    }
}
