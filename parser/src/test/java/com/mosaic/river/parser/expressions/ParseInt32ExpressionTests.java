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

        assertExpressionAsJava( exp, "1 + 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
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



// LESS THAN

    @Test
    public void parse1LT2() {
        Expression exp = parseExpression( "1<2" );

        assertExpressionAsJava( exp, "1 < 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1LT2LT3() {
        Expression exp = parseExpression( " 1 < 2 \n < 3" );

        assertExpressionAsJava( exp, "1 < 2 < 3" );
        assertEquals( "((1 < 2) < 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200LTNeg7() {
        Expression exp = parseExpression( "7,200 < -7" );

        assertExpressionAsJava( exp, "7200 < -7" );
        assertEquals("(7200 < -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1LT2LT1() {
        Expression exp = parseExpression( "1 < 2 + 3" );

        assertExpressionAsJava( exp, "1 < 2 + 3" );
        assertEquals("(1 < (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2LT3() {
        Expression exp = parseExpression( "1 + 2 < 3" );

        assertExpressionAsJava( exp, "1 + 2 < 3" );
        assertEquals("((1 + 2) < 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }



// GREATER THAN

    @Test
    public void parse1GTE2() {
        Expression exp = parseExpression( "1>=2" );

        assertExpressionAsJava( exp, "1 >= 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1GTE2GTE3() {
        Expression exp = parseExpression( " 1 >= 2 \n >= 3" );

        assertExpressionAsJava( exp, "1 >= 2 >= 3" );
        assertEquals( "((1 >= 2) >= 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200GTENeg7() {
        Expression exp = parseExpression( "7,200 >= -7" );

        assertExpressionAsJava( exp, "7200 >= -7" );
        assertEquals("(7200 >= -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1GTE2Add1() {
        Expression exp = parseExpression( "1 >= 2 + 3" );

        assertExpressionAsJava( exp, "1 >= 2 + 3" );
        assertEquals("(1 >= (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2GTE3() {
        Expression exp = parseExpression( "1 + 2 >= 3" );

        assertExpressionAsJava( exp, "1 + 2 >= 3" );
        assertEquals("((1 + 2) >= 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// LESS THAN OR EQUAL TO

    @Test
    public void parse1LTE2() {
        Expression exp = parseExpression( "1<=2" );

        assertExpressionAsJava( exp, "1 <= 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1LTE2LTE3() {
        Expression exp = parseExpression( " 1 <= 2 \n <= 3" );

        assertExpressionAsJava( exp, "1 <= 2 <= 3" );
        assertEquals( "((1 <= 2) <= 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200LTENeg7() {
        Expression exp = parseExpression( "7,200 <= -7" );

        assertExpressionAsJava( exp, "7200 <= -7" );
        assertEquals("(7200 <= -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1LTE2Add1() {
        Expression exp = parseExpression( "1 <= 2 + 3" );

        assertExpressionAsJava( exp, "1 <= 2 + 3" );
        assertEquals("(1 <= (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2LTE3() {
        Expression exp = parseExpression( "1 + 2 <= 3" );

        assertExpressionAsJava( exp, "1 + 2 <= 3" );
        assertEquals("((1 + 2) <= 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// IS EQUAL TO

    @Test
    public void parse1Eq2() {
        Expression exp = parseExpression( "1==2" );

        assertExpressionAsJava( exp, "1 == 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1Eq2Eq3() {
        Expression exp = parseExpression( " 1 == 2 \n == 3" );

        assertExpressionAsJava( exp, "1 == 2 == 3" );
        assertEquals( "((1 == 2) == 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200EqNeg7() {
        Expression exp = parseExpression( "7,200 == -7" );

        assertExpressionAsJava( exp, "7200 == -7" );
        assertEquals("(7200 == -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1Eq2Add1() {
        Expression exp = parseExpression( "1 == 2 + 3" );

        assertExpressionAsJava( exp, "1 == 2 + 3" );
        assertEquals("(1 == (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2Eq3() {
        Expression exp = parseExpression( "1 + 2 == 3" );

        assertExpressionAsJava( exp, "1 + 2 == 3" );
        assertEquals("((1 + 2) == 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// NOT EQUAL TO

    @Test
    public void parse1NotEq2() {
        Expression exp = parseExpression( "1!=2" );

        assertExpressionAsJava( exp, "1 != 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1NEq2NEq3() {
        Expression exp = parseExpression( " 1 != 2 \n != 3" );

        assertExpressionAsJava( exp, "1 != 2 != 3" );
        assertEquals( "((1 != 2) != 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200NEqNeg7() {
        Expression exp = parseExpression( "7,200 != -7" );

        assertExpressionAsJava( exp, "7200 != -7" );
        assertEquals("(7200 != -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1NEq2Add1() {
        Expression exp = parseExpression( "1 != 2 + 3" );

        assertExpressionAsJava( exp, "1 != 2 + 3" );
        assertEquals("(1 != (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2NEq3() {
        Expression exp = parseExpression( "1 + 2 != 3" );

        assertExpressionAsJava( exp, "1 + 2 != 3" );
        assertEquals("((1 + 2) != 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// BITSHIFT LEFT

    @Test
    public void parse1BitshiftLeft2() {
        Expression exp = parseExpression( "1<<2" );

        assertExpressionAsJava( exp, "1 << 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitshiftLeft2BitshiftLeft3() {
        Expression exp = parseExpression( " 1 << 2 \n << 3" );

        assertExpressionAsJava( exp, "1 << 2 << 3" );
        assertEquals( "((1 << 2) << 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BitshiftLeftNeg7() {
        Expression exp = parseExpression( "7,200 << -7" );

        assertExpressionAsJava( exp, "7200 << -7" );
        assertEquals("(7200 << -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitshiftLeft2Add1() {
        Expression exp = parseExpression( "1 << 2 + 3" );

        assertExpressionAsJava( exp, "1 << 2 + 3" );
        assertEquals("(1 << (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BitshiftLeft3() {
        Expression exp = parseExpression( "1 + 2 << 3" );

        assertExpressionAsJava( exp, "1 + 2 << 3" );
        assertEquals("((1 + 2) << 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// BITSHIFT RIGHT

    @Test
    public void parse1BitshiftRight2() {
        Expression exp = parseExpression( "1>>2" );

        assertExpressionAsJava( exp, "1 >> 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitshiftRight2BitshiftRight3() {
        Expression exp = parseExpression( " 1 >> 2 \n >> 3" );

        assertExpressionAsJava( exp, "1 >> 2 >> 3" );
        assertEquals( "((1 >> 2) >> 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200RightBitshiftNeg7() {
        Expression exp = parseExpression( "7,200 >> -7" );

        assertExpressionAsJava( exp, "7200 >> -7" );
        assertEquals("(7200 >> -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1RightBitshift2Add1() {
        Expression exp = parseExpression( "1 >> 2 + 3" );

        assertExpressionAsJava( exp, "1 >> 2 + 3" );
        assertEquals("(1 >> (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2RightBitshift3() {
        Expression exp = parseExpression( "1 + 2 >> 3" );

        assertExpressionAsJava( exp, "1 + 2 >> 3" );
        assertEquals("((1 + 2) >> 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// NOT EQUAL TO

    @Test
    public void parse1ZeroFilledRightShift2() {
        Expression exp = parseExpression( "1>>>2" );

        assertExpressionAsJava( exp, "1 >>> 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,5,5), exp.getToExcNbl() );
    }

    @Test
    public void parse1ZeroFilledRightShift2ZeroFilledRightShift3() {
        Expression exp = parseExpression( " 1 >>> 2 \n >>> 3" );

        assertExpressionAsJava( exp, "1 >>> 2 >>> 3" );
        assertEquals( "((1 >>> 2) >>> 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 6, 16 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200ZeroFilledRightShiftNeg7() {
        Expression exp = parseExpression( "7,200 >>> -7" );

        assertExpressionAsJava( exp, "7200 >>> -7" );
        assertEquals("(7200 >>> -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,12,12), exp.getToExcNbl() );
    }

    @Test
    public void parse1ZeroFilledRightShift2Add1() {
        Expression exp = parseExpression( "1 >>> 2 + 3" );

        assertExpressionAsJava( exp, "1 >>> 2 + 3" );
        assertEquals("(1 >>> (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2ZeroFilledRightShift3() {
        Expression exp = parseExpression( "1 + 2 >>> 3" );

        assertExpressionAsJava( exp, "1 + 2 >>> 3" );
        assertEquals("((1 + 2) >>> 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }



// BITWISE AND

    @Test
    public void parse1BitwiseAnd2() {
        Expression exp = parseExpression( "1&2" );

        assertExpressionAsJava( exp, "1 & 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseAnd2BitwiseAnd3() {
        Expression exp = parseExpression( " 1 & 2 \n & 3" );

        assertExpressionAsJava( exp, "1 & 2 & 3" );
        assertEquals( "((1 & 2) & 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BitwiseAndNeg7() {
        Expression exp = parseExpression( "7,200 & -7" );

        assertExpressionAsJava( exp, "7200 & -7" );
        assertEquals("(7200 & -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseAnd2Plus1() {
        Expression exp = parseExpression( "1 & 2 + 3" );

        assertExpressionAsJava( exp, "1 & 2 + 3" );
        assertEquals("(1 & (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BitwiseAnd3() {
        Expression exp = parseExpression( "1 + 2 & 3" );

        assertExpressionAsJava( exp, "1 + 2 & 3" );
        assertEquals("((1 + 2) & 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }



// BITWISE OR

    @Test
    public void parse1BitwiseOr2() {
        Expression exp = parseExpression( "1|2" );

        assertExpressionAsJava( exp, "1 | 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseOr2BitwiseOr3() {
        Expression exp = parseExpression( " 1 | 2 \n | 3" );

        assertExpressionAsJava( exp, "1 | 2 | 3" );
        assertEquals( "((1 | 2) | 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BitwiseOrNeg7() {
        Expression exp = parseExpression( "7,200 | -7" );

        assertExpressionAsJava( exp, "7200 | -7" );
        assertEquals("(7200 | -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseOr2Plus1() {
        Expression exp = parseExpression( "1 | 2 + 3" );

        assertExpressionAsJava( exp, "1 | 2 + 3" );
        assertEquals("(1 | (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BitwiseOr3() {
        Expression exp = parseExpression( "1 + 2 | 3" );

        assertExpressionAsJava( exp, "1 + 2 | 3" );
        assertEquals("((1 + 2) | 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }



// BITSHIFT RIGHT

    @Test
    public void parse1BooleanOr2() {
        Expression exp = parseExpression( "1||2" );

        assertExpressionAsJava( exp, "1 || 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1BooleanOr2BooleanOr3() {
        Expression exp = parseExpression( " 1 || 2 \n || 3" );

        assertExpressionAsJava( exp, "1 || 2 || 3" );
        assertEquals( "((1 || 2) || 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BooleanOrNeg7() {
        Expression exp = parseExpression( "7,200 || -7" );

        assertExpressionAsJava( exp, "7200 || -7" );
        assertEquals("(7200 || -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1BooleanOr2Add1() {
        Expression exp = parseExpression( "1 || 2 + 3" );

        assertExpressionAsJava( exp, "1 || 2 + 3" );
        assertEquals("(1 || (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BooleanOr3() {
        Expression exp = parseExpression( "1 + 2 || 3" );

        assertExpressionAsJava( exp, "1 + 2 || 3" );
        assertEquals("((1 + 2) || 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// BOOLEAN AND

    @Test
    public void parse1BooleanAnd2() {
        Expression exp = parseExpression( "1&&2" );

        assertExpressionAsJava( exp, "1 && 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,4,4), exp.getToExcNbl() );
    }

    @Test
    public void parse1BooleanAnd2BooleanAnd3() {
        Expression exp = parseExpression( " 1 && 2 \n && 3" );

        assertExpressionAsJava( exp, "1 && 2 && 3" );
        assertEquals( "((1 && 2) && 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 5, 14 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BooleanAndNeg7() {
        Expression exp = parseExpression( "7,200 && -7" );

        assertExpressionAsJava( exp, "7200 && -7" );
        assertEquals("(7200 && -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,11,11), exp.getToExcNbl() );
    }

    @Test
    public void parse1BooleanAnd2Add1() {
        Expression exp = parseExpression( "1 && 2 + 3" );

        assertExpressionAsJava( exp, "1 && 2 + 3" );
        assertEquals("(1 && (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BooleanAnd3() {
        Expression exp = parseExpression( "1 + 2 && 3" );

        assertExpressionAsJava( exp, "1 + 2 && 3" );
        assertEquals("((1 + 2) && 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }



// BITWISE EXCLUSIVE OR

    @Test
    public void parse1BitwiseXOr2() {
        Expression exp = parseExpression( "1^2" );

        assertExpressionAsJava( exp, "1 ^ 2" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,3,3), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseXOr2BitwiseXOr3() {
        Expression exp = parseExpression( " 1 ^ 2 \n ^ 3" );

        assertExpressionAsJava( exp, "1 ^ 2 ^ 3" );
        assertEquals( "((1 ^ 2) ^ 3)", exp.toString() );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,1,1), exp.getFromNbl() );
        assertEquals( new CharPosition( 1, 4, 12 ), exp.getToExcNbl() );
    }

    @Test
    public void parse7200BitwiseXOrNeg7() {
        Expression exp = parseExpression( "7,200 ^ -7" );

        assertExpressionAsJava( exp, "7200 ^ -7" );
        assertEquals("(7200 ^ -7)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,10,10), exp.getToExcNbl() );
    }

    @Test
    public void parse1BitwiseXOr2Plus1() {
        Expression exp = parseExpression( "1 ^ 2 + 3" );

        assertExpressionAsJava( exp, "1 ^ 2 + 3" );
        assertEquals("(1 ^ (2 + 3))", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

    @Test
    public void parse1Plus2BitwiseXOr3() {
        Expression exp = parseExpression( "1 + 2 ^ 3" );

        assertExpressionAsJava( exp, "1 + 2 ^ 3" );
        assertEquals("((1 + 2) ^ 3)", exp.toString());

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,9,9), exp.getToExcNbl() );
    }

}
