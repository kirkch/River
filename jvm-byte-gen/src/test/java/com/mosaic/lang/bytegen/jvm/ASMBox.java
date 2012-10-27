package com.mosaic.lang.bytegen.jvm;

/**
 *
 */
public class ASMBox {

    private final byte[] foo=new byte[] {(byte)10,(byte)42};

    public int f() {
        return (new boolean[2]).length;
    }

}
