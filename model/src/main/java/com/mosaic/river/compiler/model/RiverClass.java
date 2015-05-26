package com.mosaic.river.compiler.model;


import com.mosaic.io.CharPosition;
import com.mosaic.lang.QA;

import java.util.ArrayList;
import java.util.List;


public class RiverClass {

    private String           name;
    private CharPosition     posNbl;
    private List<RiverField> fields = new ArrayList<>();


    public RiverClass() {}

    public RiverClass( String name ) {
        setName(name);
    }

    public void setName( String name ) {
        // todo validate name
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addField( RiverField field ) {
        QA.notNull( field, "field" );

        // todo validate the field

        fields.add( field );
    }

    public boolean hasFields() {
        return !fields.isEmpty();
    }

    public List<RiverField> getFields() {
        return fields;
    }

    public CharPosition getPositionNbl() {
        return posNbl;
    }

    public void setPositionNbl( CharPosition posNbl ) {
        this.posNbl = posNbl;
    }
}
