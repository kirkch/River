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

                    ops.loadRegisterBoolean( 1 );
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

                    ops.loadRegisterBoolean( 1 );
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

                    ops.loadRegisterInt( 1 );
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

                    ops.loadRegisterInt( 1 );
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

                    ops.loadRegisterInt( 1 );
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

                    ops.loadRegisterInt( 1 );
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

    @Test
    public void ifNull() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/String;)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterObject( 1 );
                    ops.ifNull( l0 );

                    // if not matched
                    ops.pushString( "not-null" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "was-null" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "was-null", m.invoke((String) null) );
        assertEquals( "not-null", m.invoke("hello") );
    }

    @Test
    public void ifNotNull() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/String;)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterObject( 1 );
                    ops.ifNotNull( l0 );

                    // if not matched
                    ops.pushString( "was-null" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "not-null" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "was-null", m.invoke((String) null) );
        assertEquals( "not-null", m.invoke("hello") );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifEqualObjectRefs() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterObject( 1 );
                    ops.loadRegisterObject( 2 );
                    ops.ifEqualObjectRefs( l0 );

                    // if not matched
                    ops.pushString( "ne" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "eq" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "eq", m.invoke("a","a") );
        assertEquals( "ne", m.invoke("a","b") );
        assertEquals( "ne", m.invoke("a",new String("a")) );
        assertEquals( "eq", m.invoke(null,null) );
        assertEquals( "ne", m.invoke(null,"b") );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifNotEqualObjectRefs() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterObject( 1 );
                    ops.loadRegisterObject( 2 );
                    ops.ifNotEqualObjectRefs( l0 );

                    // if not matched
                    ops.pushString( "eq" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "ne" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "eq", m.invoke("a","a") );
        assertEquals( "ne", m.invoke("a","b") );
        assertEquals( "ne", m.invoke("a",new String("a")) );
        assertEquals( "eq", m.invoke(null,null) );
        assertEquals( "ne", m.invoke(null,"b") );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifIntsEqual() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsEqual( l0 );

                    // if not matched
                    ops.pushString( "ne" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "eq" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "eq", m.invoke(1,1) );
        assertEquals( "ne", m.invoke(1,2) );
        assertEquals( "ne", m.invoke(2,1) );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifIntsNotEqual() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsNotEqual( l0 );

                    // if not matched
                    ops.pushString( "eq" );
                    ops.returnObject();


                    // else
                    ops.visitLabel( l0 );
                    ops.pushString( "ne" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "eq", m.invoke(1,1) );
        assertEquals( "ne", m.invoke(1,2) );
        assertEquals( "ne", m.invoke(2,1) );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifIntsLT() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsLT( l0 );

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

        assertEquals( "gte", m.invoke(1,1) );
        assertEquals( "lt", m.invoke(1,2) );
        assertEquals( "gte", m.invoke(2,1) );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifIntsLTE() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsLTE( l0 );

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

        assertEquals( "lte", m.invoke(1,1) );
        assertEquals( "lte", m.invoke(1,2) );
        assertEquals( "gt", m.invoke(2,1) );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    public void ifIntsGT() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(II)Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    JVMLabel l0 = ops.newLabel();

                    ops.loadRegisterInt( 1 );
                    ops.loadRegisterInt( 2 );
                    ops.ifIntsGT( l0 );

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

        assertEquals( "lte", m.invoke(1,1) );
        assertEquals( "lte", m.invoke(1,2) );
        assertEquals( "gt", m.invoke(2,1) );
    }

    @SuppressWarnings("RedundantStringConstructorCall")
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

    @Test
    public void cmpLong() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(JJ)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterLong(1);
                    ops.loadRegisterLong(3);
                    ops.cmpLong();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(0,0) );
        assertEquals( 0, m.invoke(10,10) );
        assertEquals( 0, m.invoke(-10,-10) );
        assertEquals( -1, m.invoke(-10,10) );
        assertEquals( 1, m.invoke(10,-10) );
    }

    @Test
    public void cmpFloat() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(FF)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterFloat(1);
                    ops.loadRegisterFloat(2);
                    ops.cmpFloat();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(0f,0f) );
        assertEquals( 0, m.invoke(10f,10f) );
        assertEquals( 0, m.invoke(-10f,-10f) );
        assertEquals( -1, m.invoke(-10f,10f) );
        assertEquals( 1, m.invoke(10f,-10f) );
    }

    @Test
    public void cmpDouble() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(DD)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterDouble(1);
                    ops.loadRegisterDouble(3);
                    ops.cmpDouble();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(0.0,0.0) );
        assertEquals( 0, m.invoke(10.0,10.0) );
        assertEquals( 0, m.invoke(-10.0,-10.0) );
        assertEquals( -1, m.invoke(-10.0,10.0) );
        assertEquals( 1, m.invoke(10.0,-10.0) );
    }

}
