package com.mosaic.river.compiler.model.exp;

public enum BinaryOpEnum {
    ADD() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.add();
        }
    },

    SUBTRACT() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.subtract();
        }
    },

    MULTIPLY() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.multiply();
        }
    },

    DIVIDE() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.divide();
        }
    },

    MODULO() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.modulo();
        }
    },

    GREATER_THAN() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.greaterThan();
        }
    },

    LESS_THAN() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.lessThan();
        }
    };

//    POWER,
//    SHIFT_LEFT, SHIFT_RIGHT,
//    ASSIGN;

    public abstract void visitWith( ExpressionVisitor visitor );
}
