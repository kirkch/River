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
public class JVMOps6_FloatTests {

    @Test
    public void returnFloatConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 0 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 0, m.invoke() );
    }

    @Test
    public void returnFloatConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) -1 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) -1, m.invoke() );
    }

    @Test
    public void returnFloatConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 2 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 2, m.invoke() );
    }

    @Test
    public void returnFloatConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 3 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 3, m.invoke() );
    }

    @Test
    public void returnFloatConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 4 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 4, m.invoke() );
    }

    @Test
    public void returnFloatConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 5 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 5, m.invoke() );
    }

    @Test
    public void returnFloatConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 42 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 42, m.invoke() );
    }

    @Test
    public void returnFloatConstant4dot2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( (float) 4.2 );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 4.2, m.invoke() );
    }

    @Test
    public void returnFloatConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( Float.MIN_VALUE );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( Float.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnFloatConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushFloat( Float.MAX_VALUE );
                    ops.returnFloat();
                }
            }
        );

        assertEquals( Float.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyFloatArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayFloat( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new float[] {}, (float[]) m.invoke() ) );
    }

    @Test
    public void returnTwoFloatArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayFloat( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new float[] {(float) 0, (float) 0}, (float[]) m.invoke() ) );
    }

    @Test
    public void returnTwoFloatArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayFloat( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushFloat( (float) 3 );
                    ops.setArrayFloat();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new float[] {(float) 0, (float) 3}, (float[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "floatArrayField", "[F" );
                    ops.pushInt( 1 );
                    ops.getArrayFloat();

                    ops.returnFloat();
                }
            }
        );

        assertEquals( (float) 42, m.invoke() );
    }

}
