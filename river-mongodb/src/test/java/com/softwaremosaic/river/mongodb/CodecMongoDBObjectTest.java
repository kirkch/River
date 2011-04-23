package com.softwaremosaic.river.mongodb;

import com.mongodb.DBObject;
import com.mosaic.lang.Codec;
import com.softwaremosaic.river.meta.RiverMetaFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 *
 */
public class CodecMongoDBObjectTest {
    private RiverMetaFactory       metaFactoryMock;
    private Codec<Object,DBObject> codec;


    @Before
    public void init() {
        this.metaFactoryMock = mock( RiverMetaFactory.class );

        codec = new CodecMongoDBObject( metaFactoryMock );
    }
    
    @Test
    public void showThatEncodingNullReturnsNull() {
        assertTrue( codec.supportsEncoding() );
        assertNull( codec.encode(null) );
    }

    @Test
    public void showThatASimplePOJOValueObjectCanBeEncoded() {
        
    }
}
