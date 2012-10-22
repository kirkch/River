package com.mosaic.lang.bytegen.jvm;

/**
 * Represents the runtime generation of a stack frame. Stack frames can be nested. Handles the dynamic allocation
 * of variables to indexes.
 */
public class StackFrameContext {
    public StackFrameContext createChildFrame() {
        return null;
    }
}
