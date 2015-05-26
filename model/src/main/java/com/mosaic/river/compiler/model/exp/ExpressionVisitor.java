package com.mosaic.river.compiler.model.exp;


public interface ExpressionVisitor {

    public void int32Constant( int v );

    /**
     * Binary op '+'.
     */
    public void add();

    /**
     * Binary op '-'.
     */
    public void subtract();

    /**
     * Binary op '*'.
     */
    public void multiply();

    /**
     * Binary op '/'.
     */
    public void divide();

    /**
     * Binary op '%'.
     */
    public void modulo();
}
