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

// FIELD TYPES

    public void getField( String ownerDesc, String fieldName, String fieldType ) {
        m.visitFieldInsn( Opcodes.GETFIELD, ownerDesc, fieldName, fieldType );
    }

    public void putField( String ownerDesc, String fieldName, String fieldType ) {
        m.visitFieldInsn( Opcodes.PUTFIELD, ownerDesc, fieldName, fieldType );
    }

    public void getStaticField( String ownerDesc, String fieldName, String fieldType ) {
        m.visitFieldInsn( Opcodes.GETSTATIC, ownerDesc, fieldName, fieldType );
    }

    public void putStaticField( String ownerDesc, String fieldName, String fieldType ) {
        m.visitFieldInsn( Opcodes.PUTSTATIC, ownerDesc, fieldName, fieldType );
    }

    public void arrayLength() {
        m.visitInsn( Opcodes.ARRAYLENGTH );
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

    public void getArrayBoolean() {
        m.visitInsn( Opcodes.BALOAD );
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

    public void getArrayByte() {
        m.visitInsn( Opcodes.BALOAD );
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

    public void getArrayChar() {
        m.visitInsn( Opcodes.CALOAD );
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

    public void getArrayShort() {
        m.visitInsn( Opcodes.SALOAD );
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
                } else if ( v >= Short.MIN_VALUE && v <= Short.MAX_VALUE ) {
                    m.visitIntInsn( Opcodes.SIPUSH, v );
                } else {
                    m.visitLdcInsn( v );
                }
        }
    }

    public void returnInt() {
        m.visitInsn( Opcodes.IRETURN );
    }

    public void newArrayInt( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_INT );
    }

    public void setArrayInt() {
        m.visitInsn( Opcodes.IASTORE );
    }

    public void getArrayInt() {
        m.visitInsn( Opcodes.IALOAD );
    }

// LONG OPS

    public void pushLong( long v ) {
        if ( v == 0 ) {
                m.visitInsn( Opcodes.LCONST_0 );
        } else if ( v == 1 ) {
                m.visitInsn( Opcodes.LCONST_1 );
        } else {
            m.visitLdcInsn( v );
        }
    }

    public void returnLong() {
        m.visitInsn( Opcodes.LRETURN );
    }

    public void newArrayLong( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_LONG );
    }

    public void setArrayLong() {
        m.visitInsn( Opcodes.LASTORE );
    }

    public void getArrayLong() {
        m.visitInsn( Opcodes.LALOAD );
    }

// FLOAT OPS

    public void pushFloat( float v ) {
        m.visitLdcInsn( v );
    }

    public void returnFloat() {
        m.visitInsn( Opcodes.FRETURN );
    }

    public void newArrayFloat( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_FLOAT );
    }

    public void setArrayFloat() {
        m.visitInsn( Opcodes.FASTORE );
    }

    public void getArrayFloat() {
        m.visitInsn( Opcodes.FALOAD );
    }


// DOUBLE OPS

    public void pushDouble( double v ) {
        m.visitLdcInsn( v );
    }

    public void returnDouble() {
        m.visitInsn( Opcodes.DRETURN );
    }

    public void newArrayDouble( int length ) {
        assert length >= 0;

        pushInt(length);
        m.visitIntInsn( Opcodes.NEWARRAY, Opcodes.T_DOUBLE );
    }

    public void setArrayDouble() {
        m.visitInsn( Opcodes.DASTORE );
    }

    public void getArrayDouble() {
        m.visitInsn( Opcodes.DALOAD );
    }

// OBJECT OPS

    public void pushNull() {
        m.visitInsn( Opcodes.ACONST_NULL );
    }

    public void pushString( String v ) {
        m.visitLdcInsn( v );
    }

    public void returnObject() {
        m.visitInsn( Opcodes.ARETURN );
    }

    public void newArrayObject( int length, String refDesc ) {
        pushInt( length );
        m.visitTypeInsn( Opcodes.ANEWARRAY, refDesc );
    }

    public void setArrayObject() {
        m.visitInsn( Opcodes.AASTORE );
    }

    public void getArrayObject() {
        m.visitInsn( Opcodes.AALOAD );
    }

    public void pushVariableObject( int index ) {
        m.visitVarInsn( Opcodes.ALOAD, index );
    }
}
