package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_DivTests {

    @Test
    public void divideInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.divideInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 1, m.invoke(1,1) );
        assertEquals( 0, m.invoke(1,2) );
        assertEquals( 2, m.invoke(2,1) );
        assertEquals( -2, m.invoke(2,-1) );
        assertEquals( 2, m.invoke(4,2) );
    }

    @Test
    public void divideLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.divideLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 1L, m.invoke(1L,1L) );
        assertEquals( 0L, m.invoke(1L,2L) );
        assertEquals( 2L, m.invoke(2L,1L) );
        assertEquals( -2L, m.invoke(2L,-1L) );
        assertEquals( 2L, m.invoke(4L,2L) );
    }

    @Test
    public void divideFloat() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(FF)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterFloat( 1 );
                    ops.loadRegisterFloat( 2 );
                    ops.divideFloat();

                    ops.returnFloat();
                }
            }
        );

        assertEquals( 1f, m.invoke(1f,1f) );
        assertEquals( 0.5f, m.invoke(1f,2f) );
        assertEquals( 2f, m.invoke(2f,1f) );
        assertEquals( -2f, m.invoke(2f,-1f) );
        assertEquals( 2f, m.invoke(4f,2f) );
    }

    @Test
    public void divideDouble() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(DD)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterDouble( 1 );
                    ops.loadRegisterDouble( 3 );
                    ops.divideDouble();

                    ops.returnDouble();
                }
            }
        );

        assertEquals( 1.0, m.invoke(1.0,1.0) );
        assertEquals( 0.5, m.invoke(1.0,2.0) );
        assertEquals( 2.0, m.invoke(2.0,1.0) );
        assertEquals( -2.0, m.invoke(2.0,-1.0) );
        assertEquals( 2.0, m.invoke(4.0,2.0) );
    }
    
}
