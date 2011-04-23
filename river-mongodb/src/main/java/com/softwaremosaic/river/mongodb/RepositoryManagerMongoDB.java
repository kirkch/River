package com.softwaremosaic.river.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mosaic.lang.parser.RuntimeIOException;
import com.softwaremosaic.river.Repository;
import com.softwaremosaic.river.RepositoryManager;
import com.softwaremosaic.river.meta.RiverMetaFactory;
import org.apache.commons.lang3.ObjectUtils;

import java.net.UnknownHostException;

/**
 *
 */
public class RepositoryManagerMongoDB implements RepositoryManager {
    private Mongo mongo;
    private DB    db;

    private String host;
    private int    port;
    private String dbName;

    private RiverMetaFactory metaFactory = new RiverMetaFactory();

    public RepositoryManagerMongoDB( String dbName ) {
        this( "localhost", 27017, dbName );
    }

    public RepositoryManagerMongoDB( String host, int port, String dbName ) {
        this.host   = host;
        this.port   = port;
        this.dbName = dbName;
    }

    public synchronized String getHost() {
        return host;
    }

    public synchronized void setHost( String host ) {
        if ( !ObjectUtils.equals(this.host, host) ) {
            this.host = host;
            this.mongo = null;
            this.db    = null;
        }
    }

    public synchronized int getPort() {
        return port;
    }

    public synchronized void setPort( int port ) {
        if ( this.port != port ) {
            this.port  = port;
            this.mongo = null;
            this.db    = null;
        }
    }

    public synchronized String getDbName() {
        return dbName;
    }

    public synchronized void setDbName( String dbName ) {
        if ( !ObjectUtils.equals(this.dbName, dbName) ) {
            this.dbName = dbName;
            this.db     = null;
        }
    }

    public void authenticate( String userName, String password ) {
        getDB().authenticate( userName, password.toCharArray() );
    }

    public RiverMetaFactory getMetaFactory() {
        return metaFactory;
    }

    public void setMetaFactory( RiverMetaFactory metaFactory ) {
        this.metaFactory = metaFactory;
    }

    @Override
    public Repository newSession() {
        return new RepositoryMongoDB( getDB(), metaFactory );
    }

    @Override
    public synchronized void close() {
        getMongo().close();
        
        this.db    = null;
        this.mongo = null;
    }

    private synchronized DB getDB() {
        if ( db == null ) {
            db = getMongo().getDB( dbName );
        }

        return db;
    }

    private synchronized Mongo getMongo() {
        if ( mongo == null ) {
            try {
                mongo = new Mongo( host, port );
            } catch ( UnknownHostException e ) {
                throw new RuntimeIOException( e );
            }
        }

        return mongo;
    }
}
