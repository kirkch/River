package com.mosaic.river.compiler.model;

import com.mosaic.io.CharPosition;
import com.mosaic.lang.Lockable;
import com.mosaic.parser.HasCharPosition;
import com.mosaic.river.compiler.model.exp.Expression;


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


    protected void updatePositionConsidering( Expression exp ) {
        if ( this.fromNbl == null ) {
            this.fromNbl = exp.getFromNbl();
        } else {
            this.fromNbl = this.fromNbl.min( exp.getFromNbl() );
        }

        if ( this.toExcNbl == null ) {
            this.toExcNbl = exp.getToExcNbl();
        } else {
            this.toExcNbl = this.toExcNbl.max( exp.getToExcNbl() );
        }
    }
}
