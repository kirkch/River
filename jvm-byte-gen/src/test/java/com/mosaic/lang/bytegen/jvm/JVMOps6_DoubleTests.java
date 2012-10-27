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
public class JVMOps6_DoubleTests {

    @Test
    public void returnDoubleConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 0 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 0, m.invoke() );
    }

    @Test
    public void returnDoubleConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) -1 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) -1, m.invoke() );
    }

    @Test
    public void returnDoubleConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 2 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 2, m.invoke() );
    }

    @Test
    public void returnDoubleConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 3 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 3, m.invoke() );
    }

    @Test
    public void returnDoubleConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 4 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 4, m.invoke() );
    }

    @Test
    public void returnDoubleConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 5 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 5, m.invoke() );
    }

    @Test
    public void returnDoubleConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 42 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 42, m.invoke() );
    }

    @Test
    public void returnDoubleConstant4dot2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( (double) 4.2 );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 4.2, m.invoke() );
    }

    @Test
    public void returnDoubleConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( Double.MIN_VALUE );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( Double.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnDoubleConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushDouble( Double.MAX_VALUE );
                    ops.returnDouble();
                }
            }
        );

        assertEquals( Double.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyDoubleArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayDouble( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new double[] {}, (double[]) m.invoke() ) );
    }

    @Test
    public void returnTwoDoubleArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayDouble( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new double[] {(double) 0, (double) 0}, (double[]) m.invoke() ) );
    }

    @Test
    public void returnTwoDoubleArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayDouble( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushDouble( (double) 3 );
                    ops.setArrayDouble();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new double[] {(double) 0, (double) 3}, (double[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "doubleArrayField", "[D" );
                    ops.pushInt( 1 );
                    ops.getArrayDouble();

                    ops.returnDouble();
                }
            }
        );

        assertEquals( (double) 42, m.invoke() );
    }

}
