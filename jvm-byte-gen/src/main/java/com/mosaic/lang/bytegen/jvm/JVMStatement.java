package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
public interface JVMStatement {

    void appendCodeToMethod( ClassGenerationContext context, MethodVisitor m, StackFrameContext parentStackframe );
}
