package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps6_NegateTests {

    @Test
    public void negateInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.negateInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(0) );
        assertEquals( -1, m.invoke(1) );
        assertEquals( 1, m.invoke(-1) );
        assertEquals( -(Integer.MAX_VALUE-1), m.invoke(Integer.MAX_VALUE-1) );
    }

    @Test
    public void negateLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(J)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.negateLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke(0L) );
        assertEquals( -1L, m.invoke(1L) );
        assertEquals( 1L, m.invoke(-1L) );
        assertEquals( -(Long.MAX_VALUE-1), m.invoke(Long.MAX_VALUE-1) );
    }

    @Test
    public void negateFloat() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(F)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterFloat( 1 );
                    ops.negateFloat();

                    ops.returnFloat();
                }
            }
        );

        assertEquals( -0.0f, m.invoke(0.0f) );
        assertEquals( -1.0f, m.invoke(1.0f) );
        assertEquals( 1.0f, m.invoke(-1.0f) );
        assertEquals( -(Float.MAX_VALUE-1), m.invoke(Float.MAX_VALUE-1) );
    }

    @Test
    public void negateDouble() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(D)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterDouble( 1 );
                    ops.negateDouble();

                    ops.returnDouble();
                }
            }
        );

        assertEquals( -0.0, m.invoke(0.0) );
        assertEquals( -1.0, m.invoke(1.0) );
        assertEquals( 1.0, m.invoke(-1.0) );
        assertEquals( -(Double.MAX_VALUE-1), m.invoke(Double.MAX_VALUE-1) );
    }
    
}
