package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMClassTest {

    private DynamicLoader loader = new DynamicLoader();


    @Test
    public void generateEmptyClass() throws ClassNotFoundException {
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
    }


}
