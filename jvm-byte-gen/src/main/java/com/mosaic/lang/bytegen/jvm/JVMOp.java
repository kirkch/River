package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
public interface JVMOp {
    public void appendCodeToMethod( ClassGenerationContext context, MethodVisitor m, StackFrameContext stackframe );
}
