package com.mosaic.river.compiler.model;

import com.mosaic.io.CharPosition;
import com.mosaic.lang.Lockable;
import com.mosaic.parser.HasCharPosition;


public abstract class BaseModelClass<T extends Lockable<T>> extends Lockable<T> implements HasCharPosition {

    private CharPosition fromNbl;
    private CharPosition toExcNbl;


    public CharPosition getFromNbl() {
        return fromNbl;
    }

    public CharPosition getToExcNbl() {
        return toExcNbl;
    }

    public void setPosition( CharPosition from, CharPosition toExc ) {
        throwIfLocked();
        
        this.fromNbl  = from;
        this.toExcNbl = toExc;
    }

}
