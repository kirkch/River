package com.mosaic.river.parser;

import com.mosaic.collections.FastStack;
import com.mosaic.io.CharPosition;
import com.mosaic.parser.ParseResult;
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
    // This class uses two stacks to manage precedence.  As operands are appended, they get
    // pushed onto the operand stack.  Before an operator is pushed, its precedence is
    // compared with the operator at the top of the operator stack.  If the operator being
    // pushed has lower precedence, then it will not be pushed until the higher precedence op
    // has consumed its values from the operand stack creating a new subtree.  This new subtree
    // is then pushed on to the operator stack.  However if the operator being pushed has higher
    // precedence then it goes straight onto the operator stack and where it waits.  Thus precedence
    // is a decision between pushing straight away or building a subtree.
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

        return mergeOps();
    }

    private ParseResult<Expression> mergeOps() {
//        while ( !operandStack.isEmpty() ) {
            BinaryOp op = (BinaryOp) operatorStack.pop();

            Expression rhs = operandStack.pop();
            Expression lhs = operandStack.pop();

            op.setLHS( lhs );
            op.setRHS( rhs );

            operandStack.push( op );
//        }

//        Expression exp = operandStack.pop();
        return success( op );
    }

    private ParseResult<Expression> success( Expression op ) {
        return ParseResult.matchSucceeded( op, op.getFromNbl(), op.getToExcNbl() );
    }

}
