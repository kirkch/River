package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_BitOperatorTests {

    @Test
    public void andInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitAndInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 1, m.invoke(1,1) );
        assertEquals( 0, m.invoke(1,2) );
        assertEquals( 0, m.invoke(2,1) );
        assertEquals( 0, m.invoke(4,3) );
        assertEquals( 0, m.invoke(4,2) );
        assertEquals( 4, m.invoke(4,5) );
    }

    @Test
    public void andLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.bitAndLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 1L, m.invoke(1L,1L) );
        assertEquals( 0L, m.invoke(1L,2L) );
        assertEquals( 0L, m.invoke(2L,1L) );
        assertEquals( 0L, m.invoke(4L,3L) );
        assertEquals( 0L, m.invoke(4L,2L) );
        assertEquals( 4L, m.invoke(4L,5L) );
    }

    @Test
    public void orInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitOrInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 1, m.invoke(1,1) );
        assertEquals( 3, m.invoke(1,2) );
        assertEquals( 3, m.invoke(2,1) );
        assertEquals( 7, m.invoke(4,3) );
        assertEquals( 6, m.invoke(4,2) );
        assertEquals( 5, m.invoke(4,5) );
    }

    @Test
    public void orLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.bitOrLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 1L, m.invoke(1L,1L) );
        assertEquals( 3L, m.invoke(1L,2L) );
        assertEquals( 3L, m.invoke(2L,1L) );
        assertEquals( 7L, m.invoke(4L,3L) );
        assertEquals( 6L, m.invoke(4L,2L) );
        assertEquals( 5L, m.invoke(4L,5L) );
    }

    @Test
    public void xorInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitXorInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(1,1) );
        assertEquals( 3, m.invoke(1,2) );
        assertEquals( 3, m.invoke(2,1) );
        assertEquals( 7, m.invoke(4,3) );
        assertEquals( 6, m.invoke(4,2) );
        assertEquals( 1, m.invoke(4,5) );
    }

    @Test
    public void xorLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.bitXorLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke(1L,1L) );
        assertEquals( 3L, m.invoke(1L,2L) );
        assertEquals( 3L, m.invoke(2L,1L) );
        assertEquals( 7L, m.invoke(4L,3L) );
        assertEquals( 6L, m.invoke(4L,2L) );
        assertEquals( 1L, m.invoke(4L,5L) );
    }

    @Test
    public void shiftLeftInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitShiftLeftInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 2, m.invoke(1,1) );
        assertEquals( 4, m.invoke(1,2) );
        assertEquals( 4, m.invoke(2,1) );
        assertEquals( 32, m.invoke(4,3) );
        assertEquals( 16, m.invoke(4,2) );
        assertEquals( 128, m.invoke(4,5) );
    }

    @Test
    public void shiftLeftLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JI)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterInt( 3 );
                    ops.bitShiftLeftLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 2L, m.invoke(1L,1) );
        assertEquals( 4L, m.invoke(1L,2) );
        assertEquals( 4L, m.invoke(2L,1) );
        assertEquals( 32L, m.invoke(4L,3) );
        assertEquals( 16L, m.invoke(4L,2) );
        assertEquals( 128L, m.invoke(4L,5) );
    }

    @Test
    public void shiftRightInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitShiftRightInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(1,1) );
        assertEquals( 0, m.invoke(1,2) );
        assertEquals( 1, m.invoke(2,1) );
        assertEquals( 0, m.invoke(4,3) );
        assertEquals( 2, m.invoke(4,1) );
        assertEquals( 1, m.invoke(4,2) );
        assertEquals( 0, m.invoke(4,5) );
        assertEquals( -90 >> 1, m.invoke(-90,1) );
    }

    @Test
    public void shiftRightLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JI)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterInt( 3 );
                    ops.bitShiftRightLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke(1L,1) );
        assertEquals( 0L, m.invoke(1L,2) );
        assertEquals( 1L, m.invoke(2L,1) );
        assertEquals( 0L, m.invoke(4L,3) );
        assertEquals( 2L, m.invoke(4L,1) );
        assertEquals( 1L, m.invoke(4L,2) );
        assertEquals( 0L, m.invoke(4L,5) );
        assertEquals( -90L >> 1, m.invoke(-90L,1) );
    }

    @Test
    public void shiftRightUnsignedInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.bitShiftRightUnsignedInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(1,1) );
        assertEquals( 0, m.invoke(1,2) );
        assertEquals( 1, m.invoke(2,1) );
        assertEquals( 0, m.invoke(4,3) );
        assertEquals( 2, m.invoke(4,1) );
        assertEquals( 1, m.invoke(4,2) );
        assertEquals( 0, m.invoke(4,5) );
        assertEquals( -90 >>> 1, m.invoke(-90,1) );
    }

    @Test
    public void shiftRightUnsignedLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JI)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterInt( 3 );
                    ops.bitShiftRightUnsignedLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke(1L,1) );
        assertEquals( 0L, m.invoke(1L,2) );
        assertEquals( 1L, m.invoke(2L,1) );
        assertEquals( 0L, m.invoke(4L,3) );
        assertEquals( 2L, m.invoke(4L,1) );
        assertEquals( 1L, m.invoke(4L,2) );
        assertEquals( 0L, m.invoke(4L,5) );
        assertEquals( -90L >>> 1, m.invoke(-90L,1) );
    }

}
