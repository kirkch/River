package com.mosaic.river.compiler.model;

import com.mosaic.parsers.pull.TextPosition;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class RiverObjectFactoryTest {

    private RiverClassFactory factory = new RiverClassFactory();


    @Test
    public void givenNoEvents_callBuild_expectNull() {
        assertNull( factory.build() );
    }

    @Test
    public void givenClassName_callBuild_expectClassWithName() {
        factory.startOfClassDeclaration( new TextPosition(1,1) );
        factory.className( new TextPosition( 1, 1 ), "Account" );
        factory.endOfClassDeclaration( new TextPosition(1,10) );

        RiverClass obj = factory.build();

        assertEquals( "Account", obj.getClassName() );
        assertEquals( null, obj.getPackageName() );
        assertEquals( 0, obj.getIdentityFields().size() );
    }

    @Test
    public void givenIdentityField_callBuild_expectClassToHaveField() {
        factory.startOfClassDeclaration( new TextPosition(1,1) );
        factory.className( new TextPosition( 1, 1 ), "Account" );
        factory.identityField( new TextPosition(1,10), "int", "id" );
        factory.endOfClassDeclaration( new TextPosition(1,10) );

        RiverClass obj = factory.build();

        assertEquals( "Account", obj.getClassName() );
        assertEquals( null, obj.getPackageName() );
        assertEquals( 1, obj.getIdentityFields().size() );
        assertEquals( RiverField.identityField(RiverTypeRef.INT, "id"), obj.getIdentityFields().get("id") );
    }

}
