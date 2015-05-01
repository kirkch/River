package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.Opcodes;

/**
 *
 */
public enum JVMScope {
    PUBLIC(Opcodes.ACC_PUBLIC),
    PROTECTED(Opcodes.ACC_PROTECTED),
//    PACKAGE(Opcodes.ACC_PROTECTED), // todo look this one up
    PRIVATE(Opcodes.ACC_PRIVATE);

    private int asmCode;

    private JVMScope( int asmCode ) {

        this.asmCode = asmCode;
    }

    int getASMCode() {
        return asmCode;
    }
}
