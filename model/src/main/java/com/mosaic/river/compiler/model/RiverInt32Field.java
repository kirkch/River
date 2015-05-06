package com.mosaic.river.compiler.model;

/**
 * A 32 bit integer field declaration.
 */
public class RiverInt32Field implements RiverField {

    private String name;
    private int    initialValue;

    public RiverInt32Field( String name, int initialValue ) {
        this.name         = name;
        this.initialValue = initialValue;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue( int initialValue ) {
        this.initialValue = initialValue;
    }
}
