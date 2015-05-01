package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_RemainderTests {

    @Test
    public void remainderInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.remainderInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(1,1) );
        assertEquals( 1, m.invoke(1,2) );
        assertEquals( 0, m.invoke(2,1) );
        assertEquals( 0, m.invoke(2,-1) );
    }

    @Test
    public void remainderLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong( 1 );
                    ops.loadRegisterLong( 3 );
                    ops.remainderLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke(1L,1L) );
        assertEquals( 1L, m.invoke(1L,2L) );
        assertEquals( 0L, m.invoke(2L,1L) );
        assertEquals( 0L, m.invoke(2L,-1L) );
    }

    @Test
    public void remainderFloat() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(FF)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterFloat( 1 );
                    ops.loadRegisterFloat( 2 );
                    ops.remainderFloat();

                    ops.returnFloat();
                }
            }
        );

        assertEquals( 0f, m.invoke(1f,1f) );
        assertEquals( 1f, m.invoke(1f,2f) );
        assertEquals( 0f, m.invoke(2f,1f) );
        assertEquals( 0f, m.invoke(2f,-1f) );
    }

    @Test
    public void remainderDouble() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(DD)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterDouble( 1 );
                    ops.loadRegisterDouble( 3 );
                    ops.remainderDouble();

                    ops.returnDouble();
                }
            }
        );

        assertEquals( 0.0, m.invoke(1.0,1.0) );
        assertEquals( 1.0, m.invoke(1.0,2.0) );
        assertEquals( 0.0, m.invoke(2.0,1.0) );
        assertEquals( 0.0, m.invoke(2.0,-1.0) );
    }
    
}
