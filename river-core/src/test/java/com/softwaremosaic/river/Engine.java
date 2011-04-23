package com.softwaremosaic.river;

import java.io.Serializable;

/**
 *
 */
public class Engine implements Serializable {
    private static final long serialVersionUID = 1301555501718L;

    private int cc;

    public Engine() {}
    public Engine( int cc ) {
        this.cc = cc;
    }

    public int getCc() {
        return cc;
    }
}
