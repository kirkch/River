package com.mosaic.lang.bytegen.jvm;

import java.io.IOException;

/**
 *
 */
public class ASMBox {

    public int f( float f ) throws IOException{
        if ( f == 42.1f ) {
            return 10;
        } else {
            return 20;
        }
    }

}
