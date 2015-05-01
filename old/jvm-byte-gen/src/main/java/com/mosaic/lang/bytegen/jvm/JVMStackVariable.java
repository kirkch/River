package com.mosaic.lang.bytegen.jvm;

/**
 * A variable stored within a methods stack frame.
 */
public interface JVMStackVariable{

    public JVMType getType();
    public String  getName();

}
