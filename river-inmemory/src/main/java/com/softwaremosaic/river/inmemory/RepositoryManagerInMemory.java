package com.softwaremosaic.river.inmemory;

import com.google.inject.Inject;
import com.mosaic.lang.Cloner;
import com.softwaremosaic.river.IdGenerator;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.RepositoryManager;
import com.softwaremosaic.river.inmemory.db.InMemoryDB;
import com.softwaremosaic.river.meta.RiverMetaFactory;

import java.io.IOException;

/**
 *
 */
public class RepositoryManagerInMemory implements RepositoryManager {
    private InMemoryDB db;

    @Inject
    public RepositoryManagerInMemory( RiverMetaFactory metaFactory ) {
        db = new InMemoryDB( metaFactory );
    }

    @Inject
    public void setCloner( Cloner cloner ) {
        db.setCloner( cloner );
    }

    @Inject
    public void setIdGenerator( IdGenerator idGenerator ) {
        db.setIdGenerator( idGenerator );
    }

    @Override
    public Repository newSession() {
        return new RepositoryInMemory( db );
    }

    @Override
    public void close() throws IOException {
    }
}
