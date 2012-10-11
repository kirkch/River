package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 *
 */
public class JVMMethodTest {

    private DynamicLoader loader = new DynamicLoader();

    @Test
    public void noMethods() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 0, methods.length );
    }

    @Test
    public void declareASingleMethodThatReturnsVoidAndHasAnEmptyCodeBody() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod( new JVMMethod( "onEvent" ) );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );

        assertEquals( "onEvent", methods[0].getName() );
        assertEquals( Void.TYPE, methods[0].getReturnType() );
        assertEquals( 0, methods[0].getAnnotations().length );
        assertEquals( Modifier.PUBLIC, methods[0].getModifiers() );
    }

    @Test
    public void declareASingleMethodThatReturnsVoidAndHasAnEmptyCodeBody_invokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod( new JVMMethod( "onEvent" ) );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o );

        assertNull( r );
    }

}
