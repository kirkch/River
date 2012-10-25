package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.Opcodes;

/**
 *
 */
public class JVMOps6 extends JVMOps {

// MISC STACK OPS

    public void dup() {
        m.visitInsn( Opcodes.DUP );
    }

// VOID OPS

    /**
     *
     */
    public void returnNoValue() {
        m.visitInsn( Opcodes.RETURN );
    }


// BOOLEAN OPS
    public void pushBoolean( boolean v ) {
        if ( v ) {
            m.visitInsn( Opcodes.ICONST_1 );
        } else {
            m.visitInsn( Opcodes.ICONST_0 );
        }
    }

    public void returnBoolean() {
        m.visitInsn( Opcodes.IRETURN );
    }

    public void newArrayBoolean( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_BOOLEAN );
    }

    public void setArrayBoolean() {
        m.visitInsn( Opcodes.BASTORE );
    }

// BYTE OPS

    public void pushByte( byte v ) {
        switch (v) {
            case -1:
                m.visitInsn( Opcodes.ICONST_M1 );
                break;
            case 0:
                m.visitInsn( Opcodes.ICONST_0 );
                break;
            case 1:
                m.visitInsn( Opcodes.ICONST_1 );
                break;
            case 2:
                m.visitInsn( Opcodes.ICONST_2 );
                break;
            case 3:
                m.visitInsn( Opcodes.ICONST_3 );
                break;
            case 4:
                m.visitInsn( Opcodes.ICONST_4 );
                break;
            case 5:
                m.visitInsn( Opcodes.ICONST_5 );
                break;
            default:
                m.visitIntInsn( Opcodes.BIPUSH, v );
        }
    }

    public void returnByte() {
        m.visitInsn( Opcodes.IRETURN );
    }

    public void newArrayByte( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_BYTE );
    }

    public void setArrayByte() {
        m.visitInsn( Opcodes.BASTORE );
    }

// CHAR OPS

    public void pushChar( char v ) {
        pushInt(v);
    }

    public void returnChar() {
        m.visitInsn( Opcodes.IRETURN );
    }

    public void newArrayChar( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_CHAR );
    }

    public void setArrayChar() {
        m.visitInsn( Opcodes.CASTORE );
    }

// SHORT OPS

    public void pushShort( short v ) {
        switch (v) {
            case -1:
                m.visitInsn( Opcodes.ICONST_M1 );
                break;
            case 0:
                m.visitInsn( Opcodes.ICONST_0 );
                break;
            case 1:
                m.visitInsn( Opcodes.ICONST_1 );
                break;
            case 2:
                m.visitInsn( Opcodes.ICONST_2 );
                break;
            case 3:
                m.visitInsn( Opcodes.ICONST_3 );
                break;
            case 4:
                m.visitInsn( Opcodes.ICONST_4 );
                break;
            case 5:
                m.visitInsn( Opcodes.ICONST_5 );
                break;
            default:
                if ( v >= Byte.MIN_VALUE && v <= Byte.MAX_VALUE ) {
                    m.visitIntInsn( Opcodes.BIPUSH, v );
                } else {
                    m.visitIntInsn( Opcodes.SIPUSH, v );
                }
        }
    }

    public void returnShort() {
        m.visitInsn( Opcodes.IRETURN );
    }

    public void newArrayShort( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_SHORT );
    }

    public void setArrayShort() {
        m.visitInsn( Opcodes.SASTORE );
    }

// INT OPS

    public void pushInt( int v ) {
        switch (v) {
            case -1:
                m.visitInsn( Opcodes.ICONST_M1 );
                break;
            case 0:
                m.visitInsn( Opcodes.ICONST_0 );
                break;
            case 1:
                m.visitInsn( Opcodes.ICONST_1 );
                break;
            case 2:
                m.visitInsn( Opcodes.ICONST_2 );
                break;
            case 3:
                m.visitInsn( Opcodes.ICONST_3 );
                break;
            case 4:
                m.visitInsn( Opcodes.ICONST_4 );
                break;
            case 5:
                m.visitInsn( Opcodes.ICONST_5 );
                break;
            default:
                if ( v >= Byte.MIN_VALUE && v <= Byte.MAX_VALUE ) {
                    m.visitIntInsn( Opcodes.BIPUSH, v );
// todo smaller sizes
                } else {
                    m.visitLdcInsn( v );
                }
        }
    }

// OBJECT OPS

    public void returnObject() {
        m.visitInsn( Opcodes.ARETURN );
    }
}
