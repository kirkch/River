package com.mosaic.lang.bytegen.jvm;

import java.io.IOException;

/**
 *
 */
public class ASMBox {

    public int f() throws IOException{
        throw new IOException("fudge");
    }

}
