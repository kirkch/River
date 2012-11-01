package com.mosaic.lang.bytegen.jvm;

/**
 *
 */
public class ASMBox {

    public boolean f( boolean a, boolean b ) {
        synchronized (this ) {
            return a && b;
        }
    }

}
