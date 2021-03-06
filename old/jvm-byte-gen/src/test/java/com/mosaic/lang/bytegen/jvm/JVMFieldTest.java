package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMFieldTest {

    private DynamicLoader loader = new DynamicLoader();


    @Test
    public void noField() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 0, fields.length );
    }


    @Test
    public void singleStringWithDefaultScope_expectPublic() throws ClassNotFoundException {
        JVMClass jc    = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object("java.lang.String"), "f1" );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "f1", fields[0].getName() );
        assertEquals( String.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PUBLIC, fields[0].getModifiers() );
    }

    @Test
    public void singlePublicString() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object(String.class), "f1" );
        field.withScope( JVMScope.PUBLIC );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "f1", fields[0].getName() );
        assertEquals( String.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PUBLIC, fields[0].getModifiers() );
    }

    @Test
    public void primitiveType() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.INT, "age" );
        field.withScope( JVMScope.PUBLIC );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "age", fields[0].getName() );
        assertEquals( Integer.TYPE, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PUBLIC, fields[0].getModifiers() );
    }

    @Test
    public void protectedIntObject() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object("java/lang/Integer"), "age" );
        field.withScope( JVMScope.PROTECTED );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "age", fields[0].getName() );
        assertEquals( Integer.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PROTECTED, fields[0].getModifiers() );
    }

    @Test
    public void finalField() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object("java/lang/Integer"), "age" );
        field.withScope( JVMScope.PROTECTED );
        field.withIsFinal( true );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "age", fields[0].getName() );
        assertEquals( Integer.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PROTECTED | Modifier.FINAL, fields[0].getModifiers() );
    }

    @Test
    public void staticField() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object(Integer.class), "age" );
        field.withScope( JVMScope.PROTECTED );
        field.withIsStatic( true );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "age", fields[0].getName() );
        assertEquals( Integer.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PROTECTED | Modifier.STATIC, fields[0].getModifiers() );
    }

    @Test
    public void staticFinalField() throws ClassNotFoundException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object(Integer.class), "age" );
        field.withScope( JVMScope.PROTECTED );
        field.withIsStatic( true );
        field.withIsFinal( true );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "age", fields[0].getName() );
        assertEquals( Integer.class, fields[0].getType() );
        assertEquals( 0, fields[0].getAnnotations().length );
        assertEquals( Modifier.PROTECTED | Modifier.FINAL | Modifier.STATIC, fields[0].getModifiers() );
    }

    @Test
    public void initialObjectValueFromStaticField() throws ClassNotFoundException, IllegalAccessException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object(String.class), "age" );
        field.withInitialValue( "26" );
        field.withIsStatic( true );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( "26", fields[0].get( null ) );
    }

    @Test
    public void initialObjectValueFromInstanceField() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.object(String.class), "age" );
        field.withInitialValue( "42" );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        Object v = c.newInstance();
        assertEquals( "42", fields[0].get(v) );
    }

    @Test
    public void initialPrimitiveValueFromStaticField() throws ClassNotFoundException, IllegalAccessException {
        JVMClass jc = new JVMClass( "a/b", "Foo" );
        JVMField field = new JVMField( JVMType.INT, "age" );
        field.withInitialValue( 26 );
        field.withIsStatic( true );


        jc.withField( field );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        assertEquals( 26, fields[0].get( null ) );
    }

    @Test
    public void initialPrimitiveValueFromInstanceField() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JVMClass jc    = new JVMClass( "a/b", "Foo" )
            .withField(
                new JVMField( JVMType.INT, "age" )
                    .withInitialValue(12)
            );


        loader.declareJavaClass( jc );

        Class c = loader.findClass( "a.b.Foo" );
        Field[] fields = c.getDeclaredFields();
        assertEquals( 1, fields.length );

        Object v = c.newInstance();
        assertEquals( 12, fields[0].get(v) );
    }

}
