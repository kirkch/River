package com.mosaic.river.parser;

import com.mosaic.io.CharPosition;
import com.mosaic.parser.ParseResult;
import com.mosaic.river.compiler.model.exp.BinaryOp;
import com.mosaic.river.compiler.model.exp.BinaryOpEnum;
import com.mosaic.river.compiler.model.exp.ConstantInt32;
import com.mosaic.river.compiler.model.exp.Expression;
import org.junit.Test;

import static org.junit.Assert.*;


public class ExpressionBuilderTest {

    private ExpressionBuilder builder = new ExpressionBuilder(new CharPosition(1,2,3));
    private ConstantInt32     v1      = new ConstantInt32( 1 );
    private ConstantInt32     v2      = new ConstantInt32( 2 );
    private ConstantInt32     v3      = new ConstantInt32( 3 );


    @Test
    public void givenNoParts_expectBuilderToReportNoMatch() {
        ParseResult<Expression> result = builder.build();

        assertTrue( result.noMatch() );
    }

    @Test
    public void singleOperand_expectSingleElementTree() {
        ConstantInt32 value = new ConstantInt32( 42 );
        builder.append( value );

        ParseResult<Expression> result = builder.build();
        assertTrue( result.matched() );
        assertSame( value, result.getParsedValueNbl() );
    }

    @Test
    public void appendTwoValues_thenBuild_expectErrorAsTwoValuesByThemselvesIsMeaningless() {
        builder.append( v1 );
        builder.append( v2 );

        ParseResult<Expression> result = builder.build();
        assertTrue( result.errored() );
        assertEquals( "Values must be combined with an operator", result.getErrorMessageNbl() );
    }

    @Test
    public void givenOnePlusTwo_expectOnePlusTwo() {
        builder.append( v1 );
        builder.append( BinaryOpEnum.ADD );
        builder.append( v2 );

        ParseResult<Expression> result = builder.build();
        assertTrue( result.matched() );
        assertEquals( "(1 + 2)", result.getParsedValueNbl().toString() );
    }

}