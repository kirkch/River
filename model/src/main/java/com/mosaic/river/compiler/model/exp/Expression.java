package com.mosaic.river.compiler.model.exp;

import com.mosaic.io.CharPosition;
import com.mosaic.river.compiler.model.RiverType;


/**
 * An expression represents a value, or how to calculate a value.  For example '32' is an expression,
 * as is '32 + 1', 'x * 2', 'calc() + 1' and so on.
 */
public interface Expression {

    public RiverType getType();

    public void visitWith( ExpressionVisitor visitor );

    public CharPosition getPositionNbl();
    public void setPositionNbl( CharPosition posNbl );
}
