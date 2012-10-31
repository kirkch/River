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
    public void ifEqZero() {
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

    @Test
    public void ifNotEqZero() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Z)I") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterBoolean( 1 );
                    ops.ifNEZero( l0 );


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

        assertEquals( 20, m.invoke(true) );
        assertEquals( 10, m.invoke(false) );
    }

    @Test
    public void ifLTZero() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterInt( 1 );
                    ops.ifLTZero( l0 );

                    // if not matched
                    ops.pushString( "gte" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "lt" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "lt", m.invoke(-1) );
        assertEquals( "gte", m.invoke(0) );
        assertEquals( "gte", m.invoke(1) );
    }

    @Test
    public void ifLTEZero() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterInt( 1 );
                    ops.ifLTEZero( l0 );

                    // if not matched
                    ops.pushString( "gt" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "lte" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "lte", m.invoke(-1) );
        assertEquals( "lte", m.invoke(0) );
        assertEquals( "gt", m.invoke(1) );
    }

    @Test
    public void ifGTZero() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterInt( 1 );
                    ops.ifGTZero( l0 );

                    // if not matched
                    ops.pushString( "lte" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "gt" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "lte", m.invoke(-1) );
        assertEquals( "lte", m.invoke(0) );
        assertEquals( "gt", m.invoke(1) );
    }

    @Test
    public void ifGTEZero() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.pushRegisterInt( 1 );
                    ops.ifGTEZero( l0 );

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

        assertEquals( "lt", m.invoke(-1) );
        assertEquals( "gte", m.invoke(0) );
        assertEquals( "gte", m.invoke(1) );
    }

}
