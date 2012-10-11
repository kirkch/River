package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 *
 */
public class JVMClassTest {

    private DynamicLoader loader = new DynamicLoader();


    @Test
    public void generateEmptyClassNoPackage() throws ClassNotFoundException {
        JVMClass node = new JVMClass( null, "H1" );
        loader.declareJavaClass( node );


        Class c = loader.loadClass( "H1" );
        assertEquals( "H1", c.getName() );

        assertEquals( 0, c.getDeclaredFields().length );
    }

    @Test
    public void generateEmptyClassWithPackage() throws ClassNotFoundException {
        JVMClass node = new JVMClass( "pm/test", "H1" );
        loader.declareJavaClass( node );


        Class c = loader.loadClass( "pm.test.H1" );
        assertEquals( "pm.test.H1", c.getName() );

        assertEquals( 0, c.getDeclaredFields().length );
    }

    @Test
    public void generateClassWithOneStringField() throws ClassNotFoundException {
        JVMClass node  = new JVMClass( "pm/test", "H1" );
        JVMField field = new JVMField( JVMType.object("java/lang/String"), "name" );

        node.appendField( field );

        loader.declareJavaClass( node );


        Class c = loader.loadClass( "pm.test.H1" );
        assertEquals( "pm.test.H1", c.getName() );

        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );
        assertEquals( "name", fields[0].getName() );
        assertEquals( String.class, fields[0].getType() );
        assertTrue( Modifier.isPublic( fields[0].getModifiers() ) );
    }

    @Test
    public void generatePrivateBooleanField() throws ClassNotFoundException {
        JVMClass node  = new JVMClass( "pm/test", "H2" );
        JVMField field = new JVMField( JVMType.BOOLEAN, "flag" );

        field.setScope( JVMScope.PRIVATE );

        node.appendField( field );

        loader.declareJavaClass( node );


        Class c = loader.loadClass( "pm.test.H2" );
        assertEquals( "pm.test.H2", c.getName() );

        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );
        assertEquals( "flag", fields[0].getName() );
        assertEquals( Boolean.TYPE, fields[0].getType() );
        assertTrue( Modifier.isPrivate( fields[0].getModifiers() ) );
    }


}
