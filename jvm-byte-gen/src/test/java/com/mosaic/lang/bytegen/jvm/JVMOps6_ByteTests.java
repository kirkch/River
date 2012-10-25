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
public class JVMOps6_ByteTests {

    @Test
    public void returnByteConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 0 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 0, m.invoke() );
    }

    @Test
    public void returnByteConstantM1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) -1 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) -1, m.invoke() );
    }

    @Test
    public void returnByteConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 2 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 2, m.invoke() );
    }

    @Test
    public void returnByteConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 3 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 3, m.invoke() );
    }

    @Test
    public void returnByteConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 4 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 4, m.invoke() );
    }

    @Test
    public void returnByteConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 5 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 5, m.invoke() );
    }

    @Test
    public void returnByteConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( (byte) 42 );
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 42, m.invoke() );
    }

    @Test
    public void returnByteConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( Byte.MIN_VALUE );
                    ops.returnByte();
                }
            }
        );

        assertEquals( Byte.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnByteConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushByte( Byte.MAX_VALUE );
                    ops.returnByte();
                }
            }
        );

        assertEquals( Byte.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyByteArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayByte( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new byte[] {}, (byte[]) m.invoke() ) );
    }

    @Test
    public void returnTwoByteArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayByte( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new byte[] {(byte) 0, (byte) 0}, (byte[]) m.invoke() ) );
    }

    @Test
    public void returnTwoByteArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayByte( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushByte( (byte) 3 );
                    ops.setArrayByte();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new byte[] {(byte) 0, (byte) 3}, (byte[]) m.invoke() ) );
    }

}
