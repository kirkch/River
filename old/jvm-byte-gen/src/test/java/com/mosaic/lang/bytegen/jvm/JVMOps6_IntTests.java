package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class JVMOps6_IntTests {

    @Test
    public void returnIntConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( -1 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( -1, m.invoke() );
    }

    @Test
    public void returnIntConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 0 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke() );
    }


    @Test
    public void returnIntConstant1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 1 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 1, m.invoke() );
    }

    @Test
    public void returnIntConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 2 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 2, m.invoke() );
    }

    @Test
    public void returnIntConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 3 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 3, m.invoke() );
    }

    @Test
    public void returnIntConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 4 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 4, m.invoke() );
    }

    @Test
    public void returnIntConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 5 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 5, m.invoke() );
    }

    @Test
    public void returnIntConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 42 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke() );
    }

    @Test
    public void returnIntConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Integer.MIN_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( Integer.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnByteConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Byte.MAX_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( (int) Byte.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnByteConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Byte.MIN_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( (int) Byte.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Short.MAX_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( (int) Short.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Short.MIN_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( (int) Short.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnIntConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( Integer.MAX_VALUE );
                    ops.returnInt();
                }
            }
        );

        assertEquals( Integer.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyIntArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayInt( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new int[] {}, (int[]) m.invoke() ) );
    }

    @Test
    public void returnTwoIntArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayInt( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new int[] {0, 0}, (int[]) m.invoke() ) );
    }

    @Test
    public void returnTwoIntArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayInt( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushInt( 3 );
                    ops.setArrayInt();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new int[] {0, 3}, (int[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "intArrayField", "[I" );
                    ops.pushInt( 1 );
                    ops.getArrayInt();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke() );
    }

    @Test
    public void returnStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "intStaticField", "I" );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke() );
    }

    @Test
    public void setStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushInt( 42 );
                    ops.putStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "intStaticField", "I" );

                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "intStaticField", "I" );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke() );
    }

    @Test
    public void returnFirstParameter() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke(42) );
    }

    @Test
    public void loadRegisterInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 10, m.invoke((int) 10) );
        assertEquals( 12, m.invoke((int) 12) );
    }

    @Test
    public void storeRegisterInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterInt( 1 );
                    ops.storeRegisterInt( 2 );
                    ops.loadRegisterInt( 2 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 10, m.invoke((int) 10) );
        assertEquals( 12, m.invoke((int) 12) );
    }

    @Test
    public void incInt() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.incInt( 1, (byte) 1 );
                    ops.loadRegisterInt( 1 );
                    ops.returnInt();
                }
            }
        );

        assertEquals( 0, m.invoke(-1) );
        assertEquals( 1, m.invoke(0) );
        assertEquals( 2, m.invoke(1) );
        assertEquals( 3, m.invoke(2) );
    }

}
