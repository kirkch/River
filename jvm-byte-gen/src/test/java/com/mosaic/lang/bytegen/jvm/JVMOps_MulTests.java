package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_MulTests {

    @Test
    public void multiplyInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.multiplyInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 1, m.invoke(1,1) );
        assertEquals( 2, m.invoke(1,2) );
        assertEquals( 2, m.invoke(2,1) );
        assertEquals( -2, m.invoke(2,-1) );
    }

    @Test
    public void multiplyLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.multiplyLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 1L, m.invoke(1L,1L) );
        assertEquals( 2L, m.invoke(1L,2L) );
        assertEquals( 2L, m.invoke(2L,1L) );
        assertEquals( -2L, m.invoke(2L,-1L) );
    }

    @Test
    public void multiplyFloat() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(FF)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterFloat( 1 );
                    ops.loadRegisterFloat( 2 );
                    ops.multiplyFloat();

                    ops.returnFloat();
                }
            }
        );

        assertEquals( 1f, m.invoke(1f,1f) );
        assertEquals( 2f, m.invoke(1f,2f) );
        assertEquals( 2f, m.invoke(2f,1f) );
        assertEquals( -2f, m.invoke(2f,-1f) );
    }

    @Test
    public void multiplyDouble() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(DD)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterDouble( 1 );
                    ops.loadRegisterDouble( 3 );
                    ops.multiplyDouble();

                    ops.returnDouble();
                }
            }
        );

        assertEquals( 1.0, m.invoke(1.0,1.0) );
        assertEquals( 2.0, m.invoke(1.0,2.0) );
        assertEquals( 2.0, m.invoke(2.0,1.0) );
        assertEquals( -2.0, m.invoke(2.0,-1.0) );
    }

}
