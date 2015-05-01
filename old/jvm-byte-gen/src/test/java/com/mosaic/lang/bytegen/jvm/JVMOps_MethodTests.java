package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.*;
import static org.junit.Assert.*;

/**
 *
 */
public class JVMOps_MethodTests {

    @Test
    public void invokeStaticMethod() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.invokeStatic( JVM_CLASS_NAME, "staticIntMethod", "()I" );

                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke() );
    }

    @Test
    public void invokeMethod() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.invokeMethod( JVM_CLASS_NAME, "intMethod", "()I" );

                    ops.returnInt();
                }
            }
        );

        assertEquals( -42, m.invoke() );
    }

    @Test
    public void invokeSpecial() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.invokeMethod( JVM_CLASS_NAME, "privateIntMethod", "()I" );

                    ops.returnInt();
                }
            }
        );

        assertEquals( 2, m.invoke() );
    }

    @Test
    public void invokeInterface() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.invokeInterface( JVM_INTERFACE_NAME, "interfaceIntMethod", "()I" );

                    ops.returnInt();
                }
            }
        );

        assertEquals( 7, m.invoke() );
    }

    @Test
    public void createAndThrowException() throws IllegalAccessException {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newObject( "java/lang/RuntimeException" );
                    ops.dup();
                    ops.invokeSpecial( "java/lang/RuntimeException", "<init>", "()V" );
                    ops.throwException();
                }
            }
        );

        try {
            assertEquals( 7, m.invokeRaw() );
            fail("Expected exception");
        } catch ( InvocationTargetException e ) {
            assertEquals( RuntimeException.class, e.getCause().getClass() );

            StackTraceElement[] st = e.getCause().getStackTrace();
            assertEquals( "GeneratedClass.java", st[0].getFileName() );
            assertEquals( "generatedMethod", st[0].getMethodName() );
            assertEquals( -1, st[0].getLineNumber() );
        }
    }

    @Test
    public void declareLineNumber() throws IllegalAccessException {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.lineNumber(11);
                    ops.newObject( "java/lang/RuntimeException" );
                    ops.dup();
                    ops.invokeSpecial( "java/lang/RuntimeException", "<init>", "()V" );
                    ops.throwException();
                }
            }
        );

        try {
            assertEquals( 7, m.invokeRaw() );
            fail("Expected exception");
        } catch ( InvocationTargetException e ) {
            assertEquals( RuntimeException.class, e.getCause().getClass() );

            StackTraceElement[] st = e.getCause().getStackTrace();
            assertEquals( "GeneratedClass.java", st[0].getFileName() );
            assertEquals( "generatedMethod", st[0].getMethodName() );
            assertEquals( 11, st[0].getLineNumber() );
        }
    }

}
