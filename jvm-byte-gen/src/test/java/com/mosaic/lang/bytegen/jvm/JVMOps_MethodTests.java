package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.*;
import static org.junit.Assert.assertEquals;

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

}
