package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_ArrayTests {

    @Test
    public void readBooleanArrayLength() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 2 );
                    ops.arrayLength();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 2, m.invoke() );
    }

    @Test
    public void newMultiArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[[I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 2 );
                    ops.pushInt( 4 );
                    ops.newMultiArray( "[[I", 2 );

                    ops.returnObject();
                }
            }
        );

        int[][] v = (int[][]) m.invoke();

        assertEquals( 2, v.length );
        assertEquals( 4, v[0].length );
    }

}
