package com.mosaic.river.compiler.model.exp;

//14.    []   ()   .                           left
//    13.    ++  -- + - ~ ! (cast) new             right  (unary)
//    12.    * / %                                 left
//    11.    + -                                   left
//    10.    << >> >>>                             left
//    9.    > <= < >=                             left
//    8.    == !=                                 left
//    7.    &                                     left
//    6.    ^                                     left
//    5.   |                                     left
//    4.   &&                                    left
//    3.   ||                                    left
//    2.   ? :                                   right
//    1.   = += -= *= /= <<= >>= >>>= &= ^= |=   right

public enum BinaryOpEnum {
    ADD() {
        public int getPrecedence() {return 11;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.add();
        }
        public String toString() { return "+"; }
    },

    SUBTRACT() {
        public int getPrecedence() {return 11;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.subtract();
        }
        public String toString() { return "-"; }
    },

    MULTIPLY() {
        public int getPrecedence() {return 12;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.multiply();
        }
        public String toString() { return "*"; }
    },

    DIVIDE() {
        public int getPrecedence() {return 12;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.divide();
        }
        public String toString() { return "/"; }
    },

    MODULO() {
        public int getPrecedence() {return 12;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.modulo();
        }
        public String toString() { return "%"; }
    },

    GREATER_THAN() {
        public int getPrecedence() {return 9;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.greaterThan();
        }
        public String toString() { return ">"; }
    },

    GTE() {
        public int getPrecedence() {return 9;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.gte();
        }
        public String toString() { return ">="; }
    },

    LESS_THAN() {
        public int getPrecedence() {return 9;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.lessThan();
        }
        public String toString() { return "<"; }
    },

    LTE() {
        public int getPrecedence() {return 9;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.lte();
        }
        public String toString() { return "<="; }
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

    public abstract int getPrecedence();
    public abstract void visitWith( ExpressionVisitor visitor );

}
