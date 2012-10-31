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
public class JVMOps6_ShortTests {

    @Test
    public void returnShortConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 0 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 0, m.invoke() );
    }

    @Test
    public void returnShortConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) -1 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) -1, m.invoke() );
    }

    @Test
    public void returnShortConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 2 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 2, m.invoke() );
    }

    @Test
    public void returnShortConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 3 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 3, m.invoke() );
    }

    @Test
    public void returnShortConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 4 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 4, m.invoke() );
    }

    @Test
    public void returnShortConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 5 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 5, m.invoke() );
    }

    @Test
    public void returnShortConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 42 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 42, m.invoke() );
    }

    @Test
    public void returnShortConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( Short.MIN_VALUE );
                    ops.returnShort();
                }
            }
        );

        assertEquals( Short.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( Short.MAX_VALUE );
                    ops.returnShort();
                }
            }
        );

        assertEquals( Short.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantByteMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( Byte.MIN_VALUE );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) Byte.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnShortConstantByteMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( Byte.MAX_VALUE );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) Byte.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyShortArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayShort( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new short[] {}, (short[]) m.invoke() ) );
    }

    @Test
    public void returnTwoShortArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayShort( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new short[] {(short) 0, (short) 0}, (short[]) m.invoke() ) );
    }

    @Test
    public void returnTwoShortArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayShort( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushShort( (short) 3 );
                    ops.setArrayShort();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new short[] {(short) 0, (short) 3}, (short[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "shortArrayField", "[S" );
                    ops.pushInt( 1 );
                    ops.getArrayShort();

                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 42, m.invoke() );
    }

    @Test
    public void returnStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "shortStaticField", "S" );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 0, m.invoke() );
    }

    @Test
    public void setStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushShort( (short) 42 );
                    ops.putStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "shortStaticField", "S" );

                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "shortStaticField", "S" );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 42, m.invoke() );
    }

    @Test
    public void returnFirstParameter() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(S)S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterShort( 1 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( Short.MAX_VALUE, m.invoke(Short.MAX_VALUE) );
    }

    @Test
    public void loadRegisterShort() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(S)S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterShort( 1 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 10, m.invoke((short) 10) );
        assertEquals( (short) 12, m.invoke((short) 12) );
    }

    @Test
    public void storeRegisterShort() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(S)S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterShort( 1 );
                    ops.storeRegisterShort( 2 );
                    ops.loadRegisterShort( 2 );
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 10, m.invoke((short) 10) );
        assertEquals( (short) 12, m.invoke((short) 12) );
    }
    
}
