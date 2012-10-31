package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.Label;
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

// OBJECT OPS

    public void newObject( String jvmClassName ) {
        m.visitTypeInsn( Opcodes.NEW, jvmClassName );
    }

// CONTROL FLOW OPS

    public void throwException() {
        m.visitInsn( Opcodes.ATHROW );
//        m.visitLabel(  );
//        m.visitLineNumber(  );
//        m.visitLocalVariable(  );
    }

    public void lineNumber( int lineNumber) {
        Label label = new Label();
        m.visitLabel( label );
        m.visitLineNumber( lineNumber, label );
    }

    public JVMLabel newLabel() {
        return new JVMLabel();
    }

    public void visitLabel( JVMLabel label ) {
        label.errorIfAlreadyVisited();

        m.visitLabel( label.visit() );
    }

    public void visitLabel( JVMLabel label, int lineNumber ) {
        label.errorIfAlreadyVisited();

        Label l0 = label.visit();
        m.visitLabel( l0 );
        m.visitLineNumber( lineNumber, l0 );
    }

    public void ifEqZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFEQ, label.l0 );
    }

    public void ifNEZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFNE, label.l0 );
    }

    public void ifLTZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFLT, label.l0 );
    }

    public void ifLTEZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFLE, label.l0 );
    }

    public void ifGTZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFGT, label.l0 );
    }

    public void ifGTEZero( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFGE, label.l0 );
    }

    public void ifNull( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFNULL, label.l0 );
    }

    public void ifNotNull( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IFNONNULL, label.l0 );
    }

// METHOD OPS

    public void invokeStatic( String ownerDesc, String methodName, String methodSignature ) {
        m.visitMethodInsn( Opcodes.INVOKESTATIC, ownerDesc, methodName, methodSignature );
    }

    public void invokeMethod( String ownerDesc, String methodName, String methodSignature ) {
        m.visitMethodInsn( Opcodes.INVOKEVIRTUAL, ownerDesc, methodName, methodSignature );
    }

    public void invokeSpecial( String ownerDesc, String methodName, String methodSignature ) {
        m.visitMethodInsn( Opcodes.INVOKESPECIAL, ownerDesc, methodName, methodSignature );
    }

    public void invokeInterface( String ownerDesc, String methodName, String methodSignature ) {
        m.visitMethodInsn( Opcodes.INVOKEINTERFACE, ownerDesc, methodName, methodSignature );
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

    public void pushRegisterBoolean( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
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

    public void pushRegisterByte( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
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

    public void pushRegisterChar( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
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

    public void pushRegisterShort( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
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

    public void pushRegisterInt( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
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

    public void pushRegisterLong( int index ) {
        m.visitVarInsn( Opcodes.LLOAD, index );
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

    public void pushRegisterFloat( int index ) {
        m.visitVarInsn( Opcodes.FLOAD, index );
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

    public void pushRegisterDouble( int index ) {
        m.visitVarInsn( Opcodes.DLOAD, index );
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

    public void pushRegisterObject( int index ) {
        m.visitVarInsn( Opcodes.ALOAD, index );
    }
}
