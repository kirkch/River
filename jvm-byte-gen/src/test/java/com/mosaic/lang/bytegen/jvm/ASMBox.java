package com.mosaic.lang.bytegen.jvm;

/**
 *
 */
public class ASMBox {

    public int f( int v ) {
        int a = 0;

        switch (v) {
            case 7: a++;
            case 20: a++;
            case 8: a++;
        }

        a++;

        return a;
    }

//    public void f() {
//        synchronized (this) {
//            System.out.println("h");
//        }
//    }
}
