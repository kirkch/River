package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JVMOps_ExpressionInstructionsTests {

    @Test
    public void ifIntsGTE() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsGTE( l0 );

                    // if not matched
                    ops.pushString( "lt" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "gte" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "gte", m.invoke(1,1) );
        assertEquals( "lt", m.invoke(1,2) );
        assertEquals( "gte", m.invoke(2,1) );
    }

}
