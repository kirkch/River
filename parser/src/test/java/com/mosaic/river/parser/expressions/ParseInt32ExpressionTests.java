package com.mosaic.river.parser.expressions;

import com.mosaic.io.CharPosition;
import com.mosaic.river.compiler.model.RiverType;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.parser.ParseTestUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ParseInt32ExpressionTests extends ParseTestUtils {

    @Test
    public void parseSingleInt32Value() {
        Expression exp = parseExpression( "1" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,1,1), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1" );
    }

    @Test
    public void parseSingleInt32ValuePrefixedWithPlus() {
        Expression exp = parseExpression( "+1" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,2,2), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1" );
    }

    @Test
    public void parseSingleInt32ValueWithCommas() {
        Expression exp = parseExpression( "+1,001" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,6,6), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1001" );
    }

    @Test
    public void parseMaxInt32Value() {
        String     maxValueStr = Integer.toString( Integer.MAX_VALUE );
        Expression exp         = parseExpression( maxValueStr );

        assertExpressionAsJava( exp, maxValueStr );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition( 0, maxValueStr.length(), maxValueStr.length() ), exp.getToExcNbl() );
    }

    @Test
    public void parseMinInt32Value() {
        String     minValueStr = Integer.toString(Integer.MIN_VALUE);
        Expression exp         = parseExpression( minValueStr );

        assertExpressionAsJava( exp, minValueStr );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition( 0, minValueStr.length(), minValueStr.length() ), exp.getToExcNbl() );
    }

    @Test
    public void parseSingleInt32ValueWithWhiteSpace() {
        Expression exp = parseExpression( "  \n\t1\t " );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition( 1, 2, 4 ), exp.getFromNbl() );
        assertEquals( new CharPosition(1,3,5), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1" );
    }


// ADDITION

    @Test
    public void parse1Plus2() {
        Expression exp = parseExpression( "1+2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1 + 2" );
    }

    @Test
    public void parse1Plus2Plus3() {
        Expression exp = parseExpression( " 1 + 2 \n + 3" );

        assertExpressionAsJava( exp, "1 + 2 + 3" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200PlusNeg7() {
        Expression exp = parseExpression( "7,200 + -7" );

        assertExpressionAsJava( exp, "7200 + -7" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }


// SUBTRACTION

    @Test
    public void parse1Minus2() {
        Expression exp = parseExpression( "1-2" );

        assertExpressionAsJava( exp, "1 - 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2Minus3() {
        Expression exp = parseExpression( " 1 + 2 \n - 3" );

        assertExpressionAsJava( exp, "1 + 2 - 3" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200MinusNeg7() {
        Expression exp = parseExpression( "7,200 - -7" );

        assertExpressionAsJava( exp, "7200 - -7" );
        assertEquals("(7200 - -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// MULTIPLICATION

    @Test
    public void parse1Mult2() {
        Expression exp = parseExpression( "1*2" );

        assertExpressionAsJava( exp, "1 * 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1Mult2Mult3() {
        Expression exp = parseExpression( " 1 * 2 \n * 3" );

        assertExpressionAsJava( exp, "1 * 2 * 3" );
        assertEquals( "((1 * 2) * 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200MultNeg7() {
        Expression exp = parseExpression( "7,200 * -7" );

        assertExpressionAsJava( exp, "7200 * -7" );
        assertEquals("(7200 * -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Mult2Plus1() {
        Expression exp = parseExpression( "1 * 2 + 1" );

        assertExpressionAsJava( exp, "1 * 2 + 1" );
        assertEquals("((1 * 2) + 1)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2Mult3() {
        Expression exp = parseExpression( "1 + 2 * 3" );

        assertExpressionAsJava( exp, "1 + 2 * 3" );
        assertEquals("(1 + (2 * 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }


// DIVISION

    @Test
    public void parse1Div2() {
        Expression exp = parseExpression( "1/2" );

        assertExpressionAsJava( exp, "1 / 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1Div2Div3() {
        Expression exp = parseExpression( " 1 / 2 \n / 3" );

        assertExpressionAsJava( exp, "1 / 2 / 3" );
        assertEquals( "((1 / 2) / 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200DivNeg7() {
        Expression exp = parseExpression( "7,200 / -7" );

        assertExpressionAsJava( exp, "7200 / -7" );
        assertEquals("(7200 / -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Div2Div1() {
        Expression exp = parseExpression( "1 / 2 + 1" );

        assertExpressionAsJava( exp, "1 / 2 + 1" );
        assertEquals("((1 / 2) + 1)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2Div3() {
        Expression exp = parseExpression( "1 + 2 / 3" );

        assertExpressionAsJava( exp, "1 + 2 / 3" );
        assertEquals("(1 + (2 / 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }


// MODULO

    @Test
    public void parse1Modulo2() {
        Expression exp = parseExpression( "1%2" );

        assertExpressionAsJava( exp, "1 % 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1Mod2Mod3() {
        Expression exp = parseExpression( " 1 % 2 \n % 3" );

        assertExpressionAsJava( exp, "1 % 2 % 3" );
        assertEquals( "((1 % 2) % 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200ModNeg7() {
        Expression exp = parseExpression( "7,200 % -7" );

        assertExpressionAsJava( exp, "7200 % -7" );
        assertEquals("(7200 % -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Mod2Mod1() {
        Expression exp = parseExpression( "1 % 2 + 1" );

        assertExpressionAsJava( exp, "1 % 2 + 1" );
        assertEquals("((1 % 2) + 1)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2Mod3() {
        Expression exp = parseExpression( "1 + 2 % 3" );

        assertExpressionAsJava( exp, "1 + 2 % 3" );
        assertEquals("(1 + (2 % 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }



// GREATER THAN

    @Test
    public void parse1GT2() {
        Expression exp = parseExpression( "1>2" );

        assertExpressionAsJava( exp, "1 > 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1GT2GT3() {
        Expression exp = parseExpression( " 1 > 2 \n > 3" );

        assertExpressionAsJava( exp, "1 > 2 > 3" );
        assertEquals( "((1 > 2) > 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200GTNeg7() {
        Expression exp = parseExpression( "7,200 > -7" );

        assertExpressionAsJava( exp, "7200 > -7" );
        assertEquals("(7200 > -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1GT2GT1() {
        Expression exp = parseExpression( "1 > 2 + 3" );

        assertExpressionAsJava( exp, "1 > 2 + 3" );
        assertEquals("(1 > (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2GT3() {
        Expression exp = parseExpression( "1 + 2 > 3" );

        assertExpressionAsJava( exp, "1 + 2 > 3" );
        assertEquals("((1 + 2) > 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

}
