package com.mosaic.river.parser;

import com.mosaic.collections.FastStack;
import com.mosaic.io.CharPosition;
import com.mosaic.parser.ParseResult;
import com.mosaic.river.compiler.model.RiverType;
import com.mosaic.river.compiler.model.exp.BinaryOp;
import com.mosaic.river.compiler.model.exp.BinaryOpEnum;
import com.mosaic.river.compiler.model.exp.Expression;


/**
 * An expression builder that handles operator precedence and associativity.  Append each
 * part of a larger expression and then call build() to construct a combined tree.
 *
 * For example:   1 * 2 + 3 -> ((1*2) + 3)
 *                1 + 2 * 3 -> (1 + (2*3))
 */
public class ExpressionBuilder {

    // DESIGN
    // This class uses two stacks to manage precedence.  An expression is broken into tokens and
    // appended to this builder, which places operators onto one stack and operands on to another.
    //
    // The goal is to match operators to their arguments, when and how this occurs depends on the
    // operators relative precedence and associativity.  As each operator is appended to the
    // operator stack, it is compared with the previous operator.  If the previous operator was
    // of higher (or equal depending on associativity) then we need to ensure that this operator
    // is executed before the new one.  We do this by allowing the previous op to consume its args
    // off of the operand stack before it itself is placed onto the operand stack as an argument
    // for the new operator that is about to be pushed.
    //
    // Example:  1*2+3
    //
    //  WHAT IS PUSHED            ACTION                   operatorStack          operandStack
    //
    //                           setup                      Nil                    Nil
    //  push 1                   push value                 Nil                    1::Nil
    //  push *                   push op                    *::Nil                 1::Nil
    //  push 2                   push value                 *::Nil                 2::1::Nil
    //  push +                   compare + and *
    //                           + has lower precedence, so * must consume its args
    //                           * consumes its args        *(1,2)::Nil            Nil
    //                           now we can push +          +::*(1,2)::Nil         Nil
    //  push 3                   push value                 +::*(1,2)::Nil         3::Nil
    //  build                    join operators off of operator stack, letting them consume operands as necessary
    //                           pop +, it has no args so take one from operator stack and one from operandStack
    //                           which gives us +(*(1,2), 3) and two empty stacks
    //                           done


    private FastStack<Expression> operatorStack = new FastStack<>();
    private FastStack<Expression> operandStack  = new FastStack<>();

    private CharPosition          initialPos;

    public ExpressionBuilder( CharPosition initialPos ) {
        this.initialPos = initialPos;
    }


    public void append( BinaryOpEnum op ) {
        append( new BinaryOp(op) );
    }

    public void append( Expression exp ) {
        if ( exp.isOperator() ) {
            if ( previousOperatorIsToRunBefore(exp) ) {
                consumeNextOp();
            }

            operatorStack.push( exp );
        } else {
            operandStack.push( exp );
        }
    }

    public ParseResult<Expression> build() {
        if ( operatorStack.isEmpty() && operandStack.isEmpty() ) {
            return ParseResult.noMatch( initialPos );
        } else if ( operatorStack.isEmpty() ) {
            if ( operandStack.size() > 1 ) {
                return ParseResult.matchFailed( "Values must be combined with an operator", initialPos );
            } else {
                Expression exp = operandStack.pop();
                return success( exp );
            }
        }

        while ( operatorStack.hasContents() ) {
            consumeNextOp();
        }

        return success( operandStack.pop() );
    }

    private void consumeNextOp() {
        Expression op = operatorStack.pop();

        if ( op instanceof BinaryOp ) {
            BinaryOp binaryOp = (BinaryOp) op;

            binaryOp.setRHS( operandStack.pop() );
            binaryOp.setLHS( operandStack.pop() );
            binaryOp.setType( RiverType.INT32 );

            operandStack.push( binaryOp );
        } else {
            throw new UnsupportedOperationException( op.getClass().toString() );
        }
    }

    private boolean previousOperatorIsToRunBefore( Expression nextOp ) {
        if ( operatorStack.isEmpty() ) {
            return false;
        }

        Expression prevOp = operatorStack.peek();
        return prevOp.getPrecedence() >= nextOp.getPrecedence();
    }

    private ParseResult<Expression> success( Expression op ) {
        return ParseResult.matchSucceeded( op, op.getFromNbl(), op.getToExcNbl() );
    }

}
