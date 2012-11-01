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

    public void newMultiArray( String jvmType, int dimensionCount ) {
        m.visitMultiANewArrayInsn( jvmType, dimensionCount );
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

    public void ifEqualObjectRefs( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ACMPEQ, label.l0 );
    }

    public void ifNotEqualObjectRefs( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ACMPNE, label.l0 );
    }

    public void ifIntsEqual( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPEQ, label.l0 );
    }

    public void ifIntsNotEqual( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPNE, label.l0 );
    }

    public void ifIntsLT( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPLT, label.l0 );
    }

    public void ifIntsLTE( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPLE, label.l0 );
    }

    public void ifIntsGT( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPGT, label.l0 );
    }

    public void ifIntsGTE( JVMLabel label ) {
        m.visitJumpInsn( Opcodes.IF_ICMPGE, label.l0 );
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

    public void loadRegisterBoolean( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
    }

    public void storeRegisterBoolean( int index ) {
        m.visitVarInsn( Opcodes.ISTORE, index );
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

    public void loadRegisterByte( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
    }

    public void storeRegisterByte( int index ) {
        m.visitVarInsn( Opcodes.ISTORE, index );
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

    public void loadRegisterChar( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
    }

    public void storeRegisterChar( int index ) {
        m.visitVarInsn( Opcodes.ISTORE, index );
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

    public void loadRegisterShort( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
    }

    public void storeRegisterShort( int index ) {
        m.visitVarInsn( Opcodes.ISTORE, index );
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

    public void loadRegisterInt( int index ) {
        m.visitVarInsn( Opcodes.ILOAD, index );
    }

    public void storeRegisterInt( int index ) {
        m.visitVarInsn( Opcodes.ISTORE, index );
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

    public void loadRegisterLong( int index ) {
        m.visitVarInsn( Opcodes.LLOAD, index );
    }

    public void storeRegisterLong( int index ) {
        m.visitVarInsn( Opcodes.LSTORE, index );
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

    public void loadRegisterFloat( int index ) {
        m.visitVarInsn( Opcodes.FLOAD, index );
    }

    public void storeRegisterFloat( int index ) {
        m.visitVarInsn( Opcodes.FSTORE, index );
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

    public void loadRegisterDouble( int index ) {
        m.visitVarInsn( Opcodes.DLOAD, index );
    }

    public void storeRegisterDouble( int index ) {
        m.visitVarInsn( Opcodes.DSTORE, index );
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

    public void loadRegisterObject( int index ) {
        m.visitVarInsn( Opcodes.ALOAD, index );
    }

    public void storeRegisterObject( int index ) {
        m.visitVarInsn( Opcodes.ASTORE, index );
    }

// CASTING

    public void checkcast( String jvmClass ) {
        m.visitTypeInsn( Opcodes.CHECKCAST, jvmClass );
    }

    public void isInstanceOf( String jvmClass ) {
        m.visitTypeInsn( Opcodes.INSTANCEOF, jvmClass );
    }

    public void d2f() {
        m.visitInsn( Opcodes.D2F );
    }

    public void d2i()  {
        m.visitInsn( Opcodes.D2I );
    }

    public void d2l() {
        m.visitInsn( Opcodes.D2L );
    }

    public void f2d() {
        m.visitInsn( Opcodes.F2D );
    }

    public void f2i() {
        m.visitInsn( Opcodes.F2I );
    }

    public void f2l() {
        m.visitInsn( Opcodes.F2L );
    }

    public void i2b() {
        m.visitInsn( Opcodes.I2B );
    }

    public void i2c() {
        m.visitInsn( Opcodes.I2C );
    }

    public void i2d() {
        m.visitInsn( Opcodes.I2D );
    }

    public void i2f() {
        m.visitInsn( Opcodes.I2F );
    }

    public void i2l() {
        m.visitInsn( Opcodes.I2L );
    }

    public void i2s() {
        m.visitInsn( Opcodes.I2S );
    }

    public void l2d() {
        m.visitInsn( Opcodes.L2D );
    }

    public void l2f() {
        m.visitInsn( Opcodes.L2F );
    }

    public void l2i() {
        m.visitInsn( Opcodes.L2I );
    }

// EQUATION OPS

    public void addInt() {
        m.visitInsn( Opcodes.IADD );
    }

    public void addLong() {
        m.visitInsn( Opcodes.LADD );
    }

    public void addFloat() {
        m.visitInsn( Opcodes.FADD );
    }

    public void addDouble() {
        m.visitInsn( Opcodes.DADD );
    }

    public void subtractInt() {
        m.visitInsn( Opcodes.ISUB );
    }

    public void subtractLong() {
        m.visitInsn( Opcodes.LSUB );
    }

    public void subtractFloat() {
        m.visitInsn( Opcodes.FSUB );
    }

    public void subtractDouble() {
        m.visitInsn( Opcodes.DSUB );
    }

    public void multiplyInt() {
        m.visitInsn( Opcodes.IMUL );
    }

    public void multiplyLong() {
        m.visitInsn( Opcodes.LMUL );
    }

    public void multiplyFloat() {
        m.visitInsn( Opcodes.FMUL );
    }

    public void multiplyDouble() {
        m.visitInsn( Opcodes.DMUL );
    }

    public void divideInt() {
        m.visitInsn( Opcodes.IDIV );
    }

    public void divideLong() {
        m.visitInsn( Opcodes.LDIV );
    }

    public void divideFloat() {
        m.visitInsn( Opcodes.FDIV );
    }

    public void divideDouble() {
        m.visitInsn( Opcodes.DDIV );
    }

    public void negateInt() {
        m.visitInsn( Opcodes.INEG );
    }

    public void negateLong() {
        m.visitInsn( Opcodes.LNEG );
    }

    public void negateFloat() {
        m.visitInsn( Opcodes.FNEG );
    }

    public void negateDouble() {
        m.visitInsn( Opcodes.DNEG );
    }

    public void remainderInt() {
        m.visitInsn( Opcodes.IREM );
    }
    public void remainderLong() {
        m.visitInsn( Opcodes.LREM );
    }
    public void remainderFloat() {
        m.visitInsn( Opcodes.FREM );
    }
    public void remainderDouble() {
        m.visitInsn( Opcodes.DREM );
    }

}
