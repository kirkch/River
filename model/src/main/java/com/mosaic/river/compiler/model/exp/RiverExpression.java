package com.mosaic.river.compiler.model.exp;

import com.mosaic.river.compiler.model.RiverType;


/**
 * An expression represents a value, or how to calculate a value.  For example '32' is an expression,
 * as is '32 + 1', 'x * 2', 'calc() + 1' and so on.
 */
public interface RiverExpression {

    public RiverType getType();

    public void visitWith( RiverExpressionVisitor visitor );
}
