package com.softwaremosaic.river.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mosaic.lang.Codec;
import com.mosaic.lang.reflect.JavaField;
import com.softwaremosaic.river.meta.DocumentMeta;
import com.softwaremosaic.river.meta.RiverMetaFactory;

import java.util.Map;
import java.util.Set;

/**
 *
 */
@SuppressWarnings({"unchecked"})
class CodecMongoDBObject implements Codec<Object,DBObject> {
    private RiverMetaFactory metaFactory;

    public CodecMongoDBObject( RiverMetaFactory metaFactory ) {
        this.metaFactory = metaFactory;
    }

    @Override
    public DBObject encode( Object o ) {
        if ( o == null ) {
            return null;
        }
        
        DBObject dbObject = new BasicDBObject();

        DocumentMeta documentMeta = metaFactory.fetchDocumentMetaFor( o.getClass() );
        Set<Map.Entry<String,JavaField>> dataFields = documentMeta.getDataFields().entrySet();
        for ( Map.Entry<String,JavaField> entry : dataFields ) {
            String key   = entry.getKey();
            Object value = entry.getValue().getValue( o );

            dbObject.put( key, encode(value) );
        }

        // remove id field
        // add _id field

        return dbObject;
    }

    @Override
    public Object decode( DBObject decodedCopy ) {
        return null;
    }

    @Override
    public boolean supportsDecoding() {
        return true;
    }

    @Override
    public boolean supportsEncoding() {
        return true;
    }
}
