package com.mosaic.river.compiler.model.exp;

import com.mosaic.river.compiler.model.RiverType;


/*
BinaryOp( lhs:Exp, op:BinaryOp, rhs:Exp ) implements Exp {

  mutable type : RiverType


  visitWith( visitor:RiverExpressionVisitor ) {

  }

}
 */


public class BinaryOp implements RiverExpression {

    private RiverExpression lhs;
    private BinaryOpEnum    op;
    private RiverExpression rhs;

    private RiverType       type;

    public BinaryOp( RiverExpression lhs, BinaryOpEnum op, RiverExpression rhs ) {
        this.lhs = lhs;
        this.op  = op;
        this.rhs = rhs;
    }

    public RiverExpression getLhs() {
        return lhs;
    }

    public void setLhs( RiverExpression lhs ) {
        this.lhs = lhs;
    }

    public BinaryOpEnum getOp() {
        return op;
    }

    public void setOp( BinaryOpEnum op ) {
        this.op = op;
    }

    public RiverExpression getRhs() {
        return rhs;
    }

    public void setRhs( RiverExpression rhs ) {
        this.rhs = rhs;
    }

    public RiverType getType() {
        return type;
    }

    public void setType( RiverType type ) {
        this.type = type;
    }

    public void visitWith( RiverExpressionVisitor visitor ) {
        lhs.visitWith( visitor );
        op.visitWith( visitor );
        rhs.visitWith( visitor );
    }
}
