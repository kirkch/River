package com.mosaic.river.compiler.model.exp;


public interface ExpressionVisitor {

    public void int32Constant( int v );

    /**
     * Binary op '+'.
     */
    public void add();
}
