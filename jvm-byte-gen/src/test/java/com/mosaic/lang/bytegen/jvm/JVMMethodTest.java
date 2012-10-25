package com.mosaic.lang.bytegen.jvm;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;
import static com.mosaic.lang.bytegen.jvm.StatementHelper.*;

/**
 *
 */
@Ignore // disabled while I work on JVMOps class
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
            .withMethod(
                new JVMMethod("onEvent")
                    .withCode(
                        returnNoValue()
                    )
            );

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
            .withMethod(
                new JVMMethod("onEvent")
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertFalse( Modifier.isStatic(methods[0].getModifiers()) );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o );

        assertNull( r );
    }

    @Test
    public void declareStaticMethodWithEmptyBodyAndInvokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("onEvent")
                    .withIsStatic(true)
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertTrue( Modifier.isStatic(methods[0].getModifiers()) );


        Object r = methods[0].invoke( null );

        assertNull( r );
    }

    @Test
    public void declareFinalMethodWithEmptyBodyAndInvokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("onEvent")
                    .withIsFinal(true)
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertFalse( Modifier.isStatic(methods[0].getModifiers()) );
        assertTrue( Modifier.isFinal(methods[0].getModifiers()) );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o );

        assertNull( r );
    }

    @Test
    public void declareStaticFinalMethodWithEmptyBodyAndInvokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("onEvent")
                    .withIsStatic(true)
                    .withIsFinal( true )
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertTrue( Modifier.isStatic(methods[0].getModifiers()) );
        assertTrue( Modifier.isFinal(methods[0].getModifiers()) );


        Object r = methods[0].invoke( null );

        assertNull( r );
    }

    @Test
    public void declareAbstractMethod() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("onEvent")
                    .withIsAbstract(true)
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertTrue( Modifier.isAbstract(methods[0].getModifiers()) );
    }

    @Test
    public void declareMethodWithSingleParameter_invokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("setName")
                    .withParameter( JVMType.STRING, "name" )
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertEquals( 1, methods[0].getParameterTypes().length );
        assertEquals( String.class, methods[0].getParameterTypes()[0] );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o, "Jim" );

        assertNull( r );
    }

    @Test
    public void declareMethodWithTwoParameters_invokeIt() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("notify")
                    .withParameter( JVMType.BYTE, "flag" )
                    .withParameter( JVMType.FLOAT, "length" )
                    .withCode(
                        returnNoValue()
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );
        assertEquals( "notify", methods[0].getName() );
        assertEquals( 2, methods[0].getParameterTypes().length );
        assertEquals( Byte.TYPE, methods[0].getParameterTypes()[0] );
        assertEquals( Float.TYPE, methods[0].getParameterTypes()[1] );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o, (byte) 10, 0.43f );

        assertNull( r );
    }

    @Test
    public void declareAndInvokeEchoMethodThatTakesCharAndReturnsChar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        JVMMethodParameter v = new JVMMethodParameter(JVMType.CHAR, "v");

        JVMClass jc = new JVMClass( "a/b", "Foo" )
            .withMethod(
                new JVMMethod("echo")
                    .withParameter( v )
                    .withReturnType( JVMType.CHAR )
                    .withCode(
                        returnValue( v )
                    )
            );

        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Method[] methods = c.getDeclaredMethods();
        assertEquals( 1, methods.length );


        Object o = c.newInstance();
        Object r = methods[0].invoke( o );

        assertNull( r );
    }

}
