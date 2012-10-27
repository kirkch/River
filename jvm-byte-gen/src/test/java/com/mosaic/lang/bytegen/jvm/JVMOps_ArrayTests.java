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

}
