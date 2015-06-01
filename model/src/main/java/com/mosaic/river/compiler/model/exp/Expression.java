package com.mosaic.river.compiler.model.exp;

import com.mosaic.parser.HasCharPosition;
import com.mosaic.river.compiler.model.RiverType;


/**
 * An expression represents a value, or how to calculate a value.  For example '32' is an expression,
 * as is '32 + 1', 'x * 2', 'calc() + 1' and so on.
 */
public interface Expression extends HasCharPosition {

    public RiverType getType();
    public void setType( RiverType t );

    public void visitWith( ExpressionVisitor visitor );

    /**
     * Returns true if this node of the expression tree is an operator, such as '+' or '-'.  False
     * implies that the node is a value, such as '42' or 'a'.
     */
    public boolean isOperator();
}
