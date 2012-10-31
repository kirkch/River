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
public class JVMOps6_LongTests {

    @Test
    public void returnLongConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 0 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 0L, m.invoke() );
    }

    @Test
    public void returnLongConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( -1 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( -1L, m.invoke() );
    }

    @Test
    public void returnLongConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 2 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 2L, m.invoke() );
    }

    @Test
    public void returnLongConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 3 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 3L, m.invoke() );
    }

    @Test
    public void returnLongConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 4 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 4L, m.invoke() );
    }

    @Test
    public void returnLongConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 5 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 5L, m.invoke() );
    }

    @Test
    public void returnLongConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( 42 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( 42L, m.invoke() );
    }

    @Test
    public void returnLongConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Long.MIN_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( Long.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnByteConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Byte.MAX_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Byte.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnByteConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Byte.MIN_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Byte.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Short.MAX_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Short.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Short.MIN_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Short.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnIntConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Integer.MAX_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Integer.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnIntConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Integer.MIN_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) Integer.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnLongConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( Long.MAX_VALUE );
                    ops.returnLong();
                }
            }
        );

        assertEquals( Long.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyLongArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayLong( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new long[] {}, (long[]) m.invoke() ) );
    }

    @Test
    public void returnTwoLongArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayLong( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new long[] {0, 0}, (long[]) m.invoke() ) );
    }

    @Test
    public void returnTwoLongArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayLong( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushLong(3);
                    ops.setArrayLong();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new long[] {0, 3}, (long[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "longArrayField", "[J" );
                    ops.pushInt( 1 );
                    ops.getArrayLong();

                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) 42, m.invoke() );
    }

    @Test
    public void returnStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "longStaticField", "J" );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) 0, m.invoke() );
    }

    @Test
    public void setStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushLong( (long) 42 );
                    ops.putStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "longStaticField", "J" );

                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "longStaticField", "J" );
                    ops.returnLong();
                }
            }
        );

        assertEquals( (long) 42, m.invoke() );
    }

    @Test
    public void returnFirstParameter() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(J)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterLong( 1 );
                    ops.returnLong();
                }
            }
        );

        assertEquals( Long.MAX_VALUE, m.invoke(Long.MAX_VALUE) );
    }

}
