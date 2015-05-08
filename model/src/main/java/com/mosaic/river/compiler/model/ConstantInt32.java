package com.mosaic.river.compiler.model;

public class ConstantInt32 implements RiverExpression {

    public static ConstantInt32 newValue( int v ) {
        return new ConstantInt32( v );
    }

    private int v;

    public ConstantInt32( int v ) {
        this.v    = v;
    }

    public int getValue() {
        return v;
    }

    public void setValue( int v ) {
        this.v = v;
    }

    public RiverType getType() {
        return RiverType.INT32;
    }

    public void visitWith( RiverExpressionVisitor riverExpressionVisitor ) {
        riverExpressionVisitor.int32Constant( v );
    }
}
