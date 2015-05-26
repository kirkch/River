package com.mosaic.river.compiler.model.exp;

public enum BinaryOpEnum {
    ADD() {
        public void visitWith( RiverExpressionVisitor visitor ) {
            visitor.add();
        }
    };
// , SUBTRACT,
//    MULTIPLY, DIVIDE,
//    POWER, MODULO,
//    SHIFT_LEFT, SHIFT_RIGHT,
//    ASSIGN;

    public abstract void visitWith( RiverExpressionVisitor visitor );
}
