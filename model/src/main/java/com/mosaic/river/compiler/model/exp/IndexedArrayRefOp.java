package com.mosaic.river.compiler.model.exp;

import com.mosaic.lang.QA;
import com.mosaic.river.compiler.model.BaseModelClass;


public class IndexedArrayRefOp extends BaseModelClass<BinaryOp> implements Expression {

    private Expression exp;
    private Expression arrayIndexExp;


    public IndexedArrayRefOp() {}

    public IndexedArrayRefOp( Expression arrayIndex ) {
        QA.notNull( arrayIndex, "arrayIndex" );

        setArrayIndex( arrayIndex );
    }

    public int getPrecedence() {
        return 100;
    }

    public void setExp( Expression exp ) {
        this.exp = exp;

        updatePositionConsidering( exp );
    }

    public Expression getExp() {
        return exp;
    }

    public void setArrayIndex( Expression arrayIndex ) {
        this.arrayIndexExp = arrayIndex;

        updatePositionConsidering( arrayIndex );
    }

    public Expression getArrayIndex() {
        return arrayIndexExp;
    }


    public void visitWith( ExpressionVisitor visitor ) {
        arrayIndexExp.visitWith( visitor );
    }

    public boolean isOperator() {
        return true;
    }


    public String toString() {
        return exp + "[" + arrayIndexExp + "]";
    }

}
