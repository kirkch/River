package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.*;
import static org.junit.Assert.*;

/**
 *
 */
public class JVMOps_ObjectTests {

    @Test
    public void returnNullString() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator( "()Ljava/lang/String;" ) {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushNull();
                    ops.returnObject();
                }
            }
        );

        assertNull( m.invoke() );
    }

    @Test
    public void returnStringConstant() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushString( "a" );
                    ops.returnObject();
                }
            }
        );

        assertEquals( "a", m.invoke() );
    }

    @Test
    public void returnEmptyObjectArray() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {  
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 0, "java/lang/String" );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals(new String[] {}, (String[]) m.invoke()) );
    }

    @Test
    public void returnTwoObjectArray2() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 2, "java/lang/String" );
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals(new String[] {null,null}, (String[]) m.invoke()) );
    }

    @Test
    public void returnTwoObjectArray2_setElement() {
        MethodInstanceRef m = generateMethod(
            new MethodGenerator("()[Ljava/lang/String;") {
                public void appendMethod( MethodVisitor m ) {
                    ops.newArrayObject( 2, "java/lang/String" );
                    ops.dup();
                    ops.pushInt(1);
                    ops.pushString( "b" );
                    ops.setArrayObject();
                    ops.returnObject();
                }
            }
        );

        assertTrue( Arrays.equals(new String[] {null,"b"}, (String[]) m.invoke()) );
    }

}
