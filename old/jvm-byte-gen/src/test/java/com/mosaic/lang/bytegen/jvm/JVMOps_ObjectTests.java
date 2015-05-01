package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.*;
import static org.junit.Assert.*;

/**
 *
 */
public class JVMOps_ObjectTests {

    @Test
    public void returnNullString() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator( "()Ljava/lang/String;" ) {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushNull();
                    ops.returnObject();
                }
            }
        );

        assertNull( m.invoke() );
    }

    @Test
    public void returnStringConstant() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushString( "a" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "a", m.invoke() );
    }

    @Test
    public void returnEmptyObjectArray() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {  
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 0, "java/lang/String" );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new String[] {}, (String[]) m.invoke() ) );
    }

    @Test
    public void returnTwoObjectArray2() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 2, "java/lang/String" );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals(new String[] {null,null}, (String[]) m.invoke()) );
    }

    @Test
    public void returnTwoObjectArray2_setElement() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 2, "java/lang/String" );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushString( "b" );
                    ops.setArrayObject();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals(new String[] {null,"b"}, (String[]) m.invoke()) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "stringArrayField", "[Ljava/lang/String;" );
                    ops.pushInt( 1 );
                    ops.getArrayObject();

                    ops.returnObject();
                }
            }
        );

        assertEquals( "Hello World", m.invoke() );
    }

    @Test
    public void returnStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "stringStaticField", "Ljava/lang/String;" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( null, m.invoke() );
    }

    @Test
    public void setStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushString( "hello" );
                    ops.putStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "stringStaticField", "Ljava/lang/String;" );

                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "stringStaticField", "Ljava/lang/String;" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "hello", m.invoke() );
    }

    @Test
    public void monitorTests() throws IllegalAccessException {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/String;)V") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterObject(1);
                    ops.dup();
                    ops.lockMonitor();
                    ops.unlockMonitor();
                    ops.returnNoValue();
                }
            }
        );

        // NB does not test that the real monitor is used, as we do not want to block the test thread
        // so this test merely checks that the methods do not error in the standard case and that when passed null it does throw npe.

        assertEquals( null, m.invoke("abc") );

        try {
            m.invokeRaw((String) null);
            fail( "expected exception" );
        } catch ( InvocationTargetException e ) {
            assertEquals( NullPointerException.class, e.getCause().getClass() );
        }
    }

}
