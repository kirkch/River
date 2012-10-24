package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
class JVMOps {


    public static final JVMOp RETURN = new JVMOp() {
        public void appendCodeToMethod( MethodVisitor m, StackFrameContext stackframe ) {
            m.visitInsn( Opcodes.RETURN );
        }
    };

    public static JVMOp load( JVMStackVariable var ) {
        return new JVMOp() {
            public void appendCodeToMethod( MethodVisitor m, StackFrameContext stackframe ) {
//                m.visitVarInsn( Opcodes.loadVariableFromStackFrameByIndexOp(var), stackframe.getIndexFor(var) );
            }
        };
    }

}
