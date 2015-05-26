package com.mosaic.river.compiler.model.exp;

import com.mosaic.io.CharPosition;
import com.mosaic.river.compiler.model.RiverType;


public class ConstantInt32 implements Expression {

    public static ConstantInt32 newValue( int v ) {
        return new ConstantInt32( v );
    }

    private int          v;
    private CharPosition posNbl;


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

    public void visitWith( ExpressionVisitor riverExpressionVisitor ) {
        riverExpressionVisitor.int32Constant( v );
    }

    public CharPosition getPositionNbl() {
        return posNbl;
    }

    public void setPositionNbl( CharPosition posNbl ) {
        this.posNbl = posNbl;
    }
}
