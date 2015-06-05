package com.mosaic.river.compiler.model.exp;


public interface ExpressionVisitor {

    public void int32Constant( int v );

    /**
     * Binary op '~'.
     */
    public void twosComplement();

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

    /**
     * Binary op '<<'.
     */
    public void leftBitShift();

    /**
     * Binary op '>>'.
     */
    public void rightBitShift();

    /**
     * Binary op '>>>'.
     */
    public void zeroFillRightBitShift();

    /**
     * Binary op '>'.
     */
    public void greaterThan();

    /**
     * Binary op '>='.
     */
    public void gte();

    /**
     * Binary op '<'.
     */
    public void lessThan();

    /**
     * Binary op '<='.
     */
    public void lte();

    /**
     * Binary op '=='.
     */
    public void equalTo();

    /**
     * Binary op '!='.
     */
    public void notEqualTo();

    /**
     * Binary op '&'.
     */
    public void bitAnd();

    /**
     * Binary op '|'.
     */
    public void bitOr();

    /**
     * Binary op '^'.
     */
    public void bitwiseExclusiveOr();

    /**
     * Binary op '&&'.
     */
    public void booleanAnd();

    /**
     * Binary op '||'.
     */
    public void booleanOr();

}
