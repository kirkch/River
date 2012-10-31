package com.mosaic.lang.bytegen.jvm;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;

import static com.mosaic.lang.bytegen.jvm.JVMOpsTestTools.generateMethod;
import static org.junit.Assert.*;

/**
 *
 */
public class JVMOps6_CastTests {

    @Test
    public void checkcast() throws InvocationTargetException, IllegalAccessException {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/Object;)V") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterObject( 1 );
                    ops.checkcast( "java/lang/String" );
                    ops.returnNoValue();
                }
            }
        );

        assertEquals( null, m.invoke("foo") );

        try {
            m.invokeRaw( new Object() );
            fail( "expected exception" );
        } catch ( InvocationTargetException e ) {
            assertEquals( ClassCastException.class, e.getCause().getClass() );
        }
    }

    @Test
    public void isInstanceOf() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(Ljava/lang/Object;)Z") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterObject( 1 );
                    ops.isInstanceOf( "java/lang/String" );
                    ops.returnBoolean();
                }
            }
        );

        assertEquals( true, m.invoke("foo") );
        assertEquals( false, m.invoke(new Object()) );
    }

    @Test
    public void d2f() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(D)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterDouble( 1 );
                    ops.d2f();
                    ops.returnFloat();
                }
            }
        );

        assertEquals( 42.7f, m.invoke(42.7) );
    }

    @Test
    public void d2i() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(D)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterDouble( 1 );
                    ops.d2i();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke(42.7) );
    }

    @Test
    public void d2l() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(D)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterDouble( 1 );
                    ops.d2l();
                    ops.returnLong();
                }
            }
        );

        assertEquals( 42L, m.invoke(42.7) );
    }

    @Test
    public void f2d() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(F)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterFloat( 1 );
                    ops.f2d();
                    ops.returnDouble();
                }
            }
        );

        assertEquals( 42.7, ((Double)m.invoke(42.7f)).doubleValue(), 0.0001 );
    }

    @Test
    public void f2i() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(F)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterFloat( 1 );
                    ops.f2i();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 42, m.invoke(42.7f) );
    }

    @Test
    public void f2l() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(F)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterFloat( 1 );
                    ops.f2l();
                    ops.returnLong();
                }
            }
        );

        assertEquals( 42L, m.invoke(42.7f) );
    }

    @Test
    public void i2b() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)B") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2b();
                    ops.returnByte();
                }
            }
        );

        assertEquals( (byte) 50, m.invoke(50) );
    }

    @Test
    public void i2c() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)C") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2c();
                    ops.returnChar();
                }
            }
        );

        assertEquals( (char) 50, m.invoke(50) );
    }

    @Test
    public void i2d() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2d();
                    ops.returnDouble();
                }
            }
        );

        assertEquals( 50.0, m.invoke(50) );
    }

    @Test
    public void i2f() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2f();
                    ops.returnFloat();
                }
            }
        );

        assertEquals( 50.0f, m.invoke(50) );
    }

    @Test
    public void i2l() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)J") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2l();
                    ops.returnLong();
                }
            }
        );

        assertEquals( 50L, m.invoke(50) );
    }

    @Test
    public void i2s() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(I)S") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterInt( 1 );
                    ops.i2s();
                    ops.returnShort();
                }
            }
        );

        assertEquals( (short) 50, m.invoke(50) );
    }

    @Test
    public void l2d() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(J)D") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterLong( 1 );
                    ops.l2d();
                    ops.returnDouble();
                }
            }
        );

        assertEquals( 50.0, m.invoke(50L) );
    }

    @Test
    public void l2f() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(J)F") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterLong( 1 );
                    ops.l2f();
                    ops.returnFloat();
                }
            }
        );

        assertEquals( 50.0f, m.invoke(50L) );
    }

    @Test
    public void l2i() {
        JVMOpsTestTools.MethodInstanceRef m = generateMethod(
            new JVMOpsTestTools.MethodGenerator("(J)I") {
                public void appendMethod( MethodVisitor m ) {
                    ops.pushRegisterLong( 1 );
                    ops.l2i();
                    ops.returnInt();
                }
            }
        );

        assertEquals( 50, m.invoke(50L) );
    }

}
