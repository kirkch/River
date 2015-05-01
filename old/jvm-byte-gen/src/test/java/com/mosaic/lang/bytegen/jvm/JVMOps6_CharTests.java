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
public class JVMOps6_CharTests {

    @Test
    public void returnCharConstant0() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 0 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 0, m.invoke() );
    }

    @Test
    public void returnCharConstant1() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) -1 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) -1, m.invoke() );
    }

    @Test
    public void returnCharConstant2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 2 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 2, m.invoke() );
    }

    @Test
    public void returnCharConstant3() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 3 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 3, m.invoke() );
    }

    @Test
    public void returnCharConstant4() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 4 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 4, m.invoke() );
    }

    @Test
    public void returnCharConstant5() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 5 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 5, m.invoke() );
    }

    @Test
    public void returnCharConstant42() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 42 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 42, m.invoke() );
    }

    @Test
    public void returnCharConstantMin() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( Character.MIN_VALUE );
                    ops.returnChar();
                }
            }
        );

        assertEquals( Character.MIN_VALUE, m.invoke() );
    }

    @Test
    public void returnCharConstantMax() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( Character.MAX_VALUE );
                    ops.returnChar();
                }
            }
        );

        assertEquals( Character.MAX_VALUE, m.invoke() );
    }

    @Test
    public void returnEmptyCharArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayChar( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new char[] {}, (char[]) m.invoke() ) );
    }

    @Test
    public void returnTwoCharArray2() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayChar( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new char[] {(char) 0, (char) 0}, (char[]) m.invoke() ) );
    }

    @Test
    public void returnTwoCharArray2_setElement() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()[C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayChar( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushChar( (char) 3 );
                    ops.setArrayChar();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new char[] {(char) 0, (char) 3}, (char[]) m.invoke() ) );
    }

    @Test
    public void readValueFromArray() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushThis();
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "charArrayField", "[C" );
                    ops.pushInt( 1 );
                    ops.getArrayChar();

                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 42, m.invoke() );
    }

    @Test
    public void returnStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "charStaticField", "C" );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 0, m.invoke() );
    }

    @Test
    public void setStaticFieldValue() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("()C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushChar( (char) 42 );
                    ops.putStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "charStaticField", "C" );

                    ops.getStaticField( JVMOpsTestTools.JVM_CLASS_NAME, "charStaticField", "C" );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 42, m.invoke() );
    }

    @Test
    public void returnFirstParameter() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(C)C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterChar( 1 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( 'a', m.invoke('a') );
    }

    @Test
    public void loadRegisterChar() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(C)C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterChar( 1 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 10, m.invoke((char) 10) );
        assertEquals( (char) 12, m.invoke((char) 12) );
    }

    @Test
    public void storeRegisterChar() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(C)C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.loadRegisterChar( 1 );
                    ops.storeRegisterChar( 2 );
                    ops.loadRegisterChar( 2 );
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 10, m.invoke((char) 10) );
        assertEquals( (char) 12, m.invoke((char) 12) );
    }

}
