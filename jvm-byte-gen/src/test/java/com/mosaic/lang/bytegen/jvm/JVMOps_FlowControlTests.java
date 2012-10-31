package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_FlowControlTests {

    @Test
    public void invokeStaticMethod() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Z)I") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterBoolean( 1 );
                    ops.ifEqZero( l0 );


                    // if true
                    ops.pushInt( 10 );
                    ops.returnInt();
                    ops.visitLabel( l0 );

                    // else
                    ops.pushInt( 20 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 10, m.invoke(true) );
        assertEquals( 20, m.invoke(false) );
    }

}
