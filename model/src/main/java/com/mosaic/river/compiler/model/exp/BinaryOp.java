package com.mosaic.river.compiler.model.exp;

import com.mosaic.io.CharPosition;
import com.mosaic.lang.QA;
import com.mosaic.river.compiler.model.BaseModelClass;
import com.mosaic.river.compiler.model.RiverType;


/*
BinaryOp( lhs:Exp, op:BinaryOp, rhs:Exp ) implements Exp {

  mutable type : RiverType


  visitWith( visitor:RiverExpressionVisitor ) {

  }

}
 */


public class BinaryOp extends BaseModelClass<BinaryOp> implements Expression {

    private Expression   lhs;
    private BinaryOpEnum op;
    private Expression   rhs;

    private RiverType    type;
    private CharPosition posNbl;


    public BinaryOp( BinaryOpEnum op ) {
        this.op = op;
    }

    public BinaryOp( Expression lhs, BinaryOpEnum op, Expression rhs ) {
        QA.notNull( lhs, "lhs" );
        QA.notNull( op, "op" );
        QA.notNull( rhs, "rhs" );

        this.lhs = lhs;
        this.op  = op;
        this.rhs = rhs;
    }

    public int getPrecedence() {
        return op.getPrecedence();
    }

    public Expression getLHS() {
        return lhs;
    }

    public void setLHS( Expression lhs ) {
        this.lhs = lhs;

        updatePositionConsidering(lhs);
    }

    public BinaryOpEnum getOp() {
        return op;
    }

    public void setOp( BinaryOpEnum op ) {
        this.op = op;
    }

    public Expression getRHS() {
        return rhs;
    }

    public void setRHS( Expression rhs ) {
        this.rhs = rhs;

        updatePositionConsidering(rhs);
    }

    public RiverType getType() {
        return type;
    }

    public void setType( RiverType type ) {
        this.type = type;
    }

    public void visitWith( ExpressionVisitor visitor ) {
        lhs.visitWith( visitor );
        op.visitWith( visitor );
        rhs.visitWith( visitor );
    }

    public boolean isOperator() {
        return true;
    }

    public CharPosition getPositionNbl() {
        return posNbl;
    }

    public void setPositionNbl( CharPosition posNbl ) {
        this.posNbl = posNbl;
    }

    public String toString() {
        return "(" + lhs + " " + op + " " + rhs + ")";
    }
}
