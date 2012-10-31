package com.mosaic.lang.bytegen.jvm;

import java.io.IOException;

/**
 *
 */
public class ASMBox {

    public int f( boolean flag) throws IOException{
        if ( flag ) {
            return 10;
        } else {
            return 20;
        }
    }

}
