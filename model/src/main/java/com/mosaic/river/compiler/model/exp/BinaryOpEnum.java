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
    TWOS_COMPLEMENT() {
        public int getPrecedence() {return 13;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.twosComplement();
        }
        public String toString() { return "~"; }
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

    LEFT_BITSHIFT() {
        public int getPrecedence() {return 10;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.leftBitShift();
        }
        public String toString() { return "<<"; }
    },

    RIGHT_BITSHIFT() {
        public int getPrecedence() {return 10;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.rightBitShift();
        }
        public String toString() { return ">>"; }
    },

    ZEROFILL_RIGHT_BITSHIFT() {
        public int getPrecedence() {return 10;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.zeroFillRightBitShift();
        }
        public String toString() { return ">>>"; }
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
    },

    EQUALS_TO() {
        public int getPrecedence() {return 8;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.equalTo();
        }
        public String toString() { return "=="; }
    },

    NOT_EQUAL_TO() {
        public int getPrecedence() {return 8;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.notEqualTo();
        }
        public String toString() { return "!="; }
    },

    BIT_AND() {
        public int getPrecedence() {return 7;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.bitAnd();
        }
        public String toString() { return "&"; }
    },

    BIT_OR() {
        public int getPrecedence() {return 7;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.bitOr();
        }
        public String toString() { return "&"; }
    },

    BITWISE_EXCLUSIVE_OR() {
        public int getPrecedence() {return 6;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.bitwiseExclusiveOr();
        }
        public String toString() { return "^"; }
    },

    BOOLEAN_AND() {
        public int getPrecedence() {return 4;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.booleanAnd();
        }
        public String toString() { return "&&"; }
    },

    BOOLEAN_OR() {
        public int getPrecedence() {return 3;}
        public void visitWith( ExpressionVisitor visitor ) {
            visitor.booleanOr();
        }
        public String toString() { return "||"; }
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
