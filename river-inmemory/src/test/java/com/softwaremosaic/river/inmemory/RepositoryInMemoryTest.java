package com.softwaremosaic.river.inmemory;

import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.idgenerators.IdGeneratorSequence;
import com.softwaremosaic.river.meta.RiverMetaFactory;
import com.softwaremosaic.river.tests.RiverRepositoryValidator;
import org.junit.Test;

/**
 *
 */
public class RepositoryInMemoryTest {
    @Test
    public void validateRepository() {
//        try ( todo waiting for intellij to support java 7 (eta 10.5) ck
        RepositoryManagerInMemory manager = new RepositoryManagerInMemory( new RiverMetaFactory() );
        manager.setIdGenerator( new IdGeneratorSequence(10) );

            Repository repo = manager.newSession();

//        ) {
            RiverRepositoryValidator.validate( repo );
//        }
    }
}
