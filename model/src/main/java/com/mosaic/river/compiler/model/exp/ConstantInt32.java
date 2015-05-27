package com.mosaic.river.compiler.model.exp;

import com.mosaic.river.compiler.model.BaseModelClass;
import com.mosaic.river.compiler.model.RiverType;


public class ConstantInt32 extends BaseModelClass<ConstantInt32> implements Expression {

    public static ConstantInt32 newValue( int v ) {
        return new ConstantInt32( v );
    }

    private int          v;


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

    public String toString() {
        return Integer.toString( v );
    }
}
