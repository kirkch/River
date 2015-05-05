package com.mosaic.river.compiler.model;

public class ConstantInt32 {

    private String name;
    private int v;

    public ConstantInt32( String name, int v ) {
        this.name = name;
        this.v    = v;
    }

    public int getV() {
        return v;
    }

    public void setV( int v ) {
        this.v = v;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
