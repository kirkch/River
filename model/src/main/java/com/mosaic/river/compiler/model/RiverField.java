package com.mosaic.river.compiler.model;

import com.mosaic.lang.Lockable;
import com.mosaic.river.compiler.model.exp.ConstantInt32;
import com.mosaic.river.compiler.model.exp.RiverExpression;


/**
 * A 32 bit integer field declaration.
 */
public class RiverField extends Lockable<RiverField> {

    public static RiverField newInt32Field( String name ) {
        return newInt32Field( name, 0 );
    }

    public static RiverField newInt32Field( String name, int initialValue ) {
        return new RiverField( name, ConstantInt32.newValue( initialValue ) );
    }


    private String          name;
    private RiverExpression initialValue;


    public RiverField( String name, RiverExpression initialValue ) {
        setName( name );
        setInitialValue( initialValue );
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        throwIfLocked();

        this.name = name;
    }

    public RiverType getType() {
        return initialValue.getType();
    }

    /**
     * Returns an expression that calculates the initial value.  The expression may be as simple
     * as a constant, or as complicated as calculating the meaning of life (42).
     */
    public RiverExpression getInitialValue() {
        return initialValue;
    }

    /**
     * Specifies an expression that calculates the initial value.  The expression may be as simple
     * as a constant, or as complicated as calculating the meaning of life (42).
     */
    public void setInitialValue( RiverExpression initialValue ) {
        throwIfLocked();

        this.initialValue = initialValue;
    }

}
