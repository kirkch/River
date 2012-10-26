package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.*;
import static org.junit.Assert.*;

/**
 *
 */
public class JVMOps6_BooleanTests {

    @Test
    public void returnNoValueOp() {
        MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator( "()V" ) {
                public void appendMethod( MethodVisitor m ) {
                    ops.returnNoValue();
                }
            }
        );

        assertNull( m.invoke() );
    }

    @Test
    public void returnTrueConstant() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.pushBoolean( true );
                    ops.returnBoolean();
                }
            }
        );

        assertEquals( true, m.invoke() );
    }

    @Test
    public void returnFalseConstant() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.pushBoolean( false );
                    ops.returnBoolean();
                }
            }
        );

        assertEquals( false, m.invoke() );
    }

    @Test
    public void returnEmptyBooleanArray() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 0 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new boolean[] {}, (boolean[]) m.invoke() ) );
    }

    @Test
    public void returnTwoBooleanArray2() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 2 );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new boolean[] {false, false}, (boolean[]) m.invoke() ) );
    }

    @Test
    public void returnTwoBooleanArray2_setElement() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 2 );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushBoolean( true );
                    ops.setArrayBoolean();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals( new boolean[] {false, true}, (boolean[]) m.invoke() ) );
    }

    @Test
    public void returnFieldValue() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.loadVariableObject( 0 ); // this
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "booleanField", "Z" );
                    ops.returnBoolean();
                }
            }
        );

        assertEquals( false, m.invoke() );
    }

    @Test
    public void setFieldValue() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.loadVariableObject( 0 ); // this
                    ops.pushBoolean( true );
                    ops.putField( JVMOpsTestTools.JVM_CLASS_NAME, "booleanField", "Z" );

                    ops.loadVariableObject( 0 ); // this
                    ops.getField( JVMOpsTestTools.JVM_CLASS_NAME, "booleanField", "Z" );
                    ops.returnBoolean();
                }
            }
        );

        assertEquals( true, m.invoke() );
    }

    @Test
    public void readValueFromArray() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Z") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 2 );
                    ops.pushInt(1);
                    ops.getArrayBoolean();

                    ops.returnBoolean();
                }
            }
        );

        assertEquals( false, m.invoke() );
    }

    @Test
    public void readBooleanArrayLength() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()I") {  // Z -> Boolean
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayBoolean( 2 );
                    ops.arrayLength();

                    ops.returnInt();
                }
            }
        );

        assertEquals( 2, m.invoke() );
    }
    
}
