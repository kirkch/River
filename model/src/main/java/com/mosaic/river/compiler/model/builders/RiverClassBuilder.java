package com.mosaic.river.compiler.model.builders;


import com.mosaic.river.compiler.model.RiverClass;


public class RiverClassBuilder {
    private final RiverClass c = new RiverClass();

    public RiverClassBuilder( String name ) {
        c.setName( name );
    }

    public RiverClass build() {
        return c;
    }
}


