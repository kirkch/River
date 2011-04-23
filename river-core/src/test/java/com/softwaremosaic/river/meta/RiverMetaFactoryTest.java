package com.softwaremosaic.river.meta;

import com.mosaic.lang.reflect.JavaField;
import com.softwaremosaic.river.BigCar;
import com.softwaremosaic.river.Car;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 *
 */
public class RiverMetaFactoryTest {
    @Test
    public void generateMetaForASimpleTwoObjectDocument() {
        RiverMetaFactory metaFactory = new RiverMetaFactory();

        RootDocumentMeta<Car> carMeta = metaFactory.fetchRootDocumentMetaFor( Car.class );
        assertEquals( "Car", carMeta.getDocumentModelName() );

        Map<String,JavaField<Car>> carDataFields = carMeta.getDataFields();
        assertEquals( 3, carDataFields.size() );
        assertTrue( carDataFields.containsKey( "id" ) );
        assertTrue( carDataFields.containsKey( "engine" ) );
        assertTrue( carDataFields.containsKey("numberOfWheels") );
    }

    @Test
    public void showThatInheritanceIsSupportedOnTheRootDocument() {
        RiverMetaFactory metaFactory = new RiverMetaFactory();

        RootDocumentMeta<BigCar> carMeta = metaFactory.fetchRootDocumentMetaFor( BigCar.class );
        assertEquals( "BigCar", carMeta.getDocumentModelName() );

        Map<String,JavaField<BigCar>> carDataFields = carMeta.getDataFields();
        assertEquals( 4, carDataFields.size() );
        assertTrue( carDataFields.containsKey( "id" ) );
        assertTrue( carDataFields.containsKey( "engine" ) );
        assertTrue( carDataFields.containsKey("numberOfWheels") );
    }

    @Test
    public void showThatDuplicateFieldNamesIsNotSupportedEvenWhenOnParentClasses() {
        RiverMetaFactory metaFactory = new RiverMetaFactory();

        try {
            metaFactory.fetchRootDocumentMetaFor( ClassWithDuplicateFieldName.class );
            fail( "Expected IllegalStateException" );
        } catch ( IllegalStateException e ) {
            assertEquals( "'numberOfWheels' is declared twice, once on ClassWithDuplicateFieldName and again on Car", e.getMessage() );
        }
    }

    @Test
    public void showThatDocumentObjectNameDefaultsToClassName() {
        RiverMetaFactory metaFactory = new RiverMetaFactory();

        RootDocumentMeta<BigCar> carMeta = metaFactory.fetchRootDocumentMetaFor( BigCar.class );
        assertEquals( "BigCar", carMeta.getDocumentModelName() );
    }

    // todo showThatDocumentObjectNameCanBeSpecifiedByAnnotation
    // todo showThatFieldNameCanBeSpecifiedByAnnotation


    public static class ClassWithDuplicateFieldName extends Car {
        private int numberOfWheels;

        public int getNumberOfWheels() {
            return numberOfWheels;
        }

        public void setNumberOfWheels( int numberOfWheels ) {
            this.numberOfWheels = numberOfWheels;
        }
    }
}
