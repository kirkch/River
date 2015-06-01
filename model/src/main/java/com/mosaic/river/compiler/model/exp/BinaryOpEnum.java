package com.mosaic.river.compiler.model.exp;

public enum BinaryOpEnum {
    ADD() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.add();
        }
        public String toString() { return "+"; }
    },

    SUBTRACT() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.subtract();
        }
        public String toString() { return "-"; }
    },

    MULTIPLY() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.multiply();
        }
        public String toString() { return "*"; }
    },

    DIVIDE() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.divide();
        }
        public String toString() { return "/"; }
    },

    MODULO() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.modulo();
        }
        public String toString() { return "%"; }
    },

    GREATER_THAN() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.greaterThan();
        }
        public String toString() { return ">"; }
    },

    LESS_THAN() {
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.lessThan();
        }
        public String toString() { return "<"; }
    };


    public static BinaryOpEnum selectFromSymbol( String opSymbol ) {
        for ( BinaryOpEnum enumValue : BinaryOpEnum.values() ) {
            if ( enumValue.toString().equals(opSymbol) ) {
                return enumValue;
            }
        }

        return null;
    }


//    POWER,
//    SHIFT_LEFT, SHIFT_RIGHT,
//    ASSIGN;

    public abstract void visitWith( ExpressionVisitor visitor );

}
