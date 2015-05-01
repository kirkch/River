package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
public interface JVMStatement {

    void appendCodeToMethod( MethodVisitor m, StackFrameContext parentStackframe );
}
