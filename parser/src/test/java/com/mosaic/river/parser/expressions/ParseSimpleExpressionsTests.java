package com.mosaic.river.parser.expressions;

import com.mosaic.io.CharPosition;
import com.mosaic.river.compiler.model.RiverType;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.parser.ParseTestUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ParseSimpleExpressionsTests extends ParseTestUtils {

    @Test
    public void parseSingleInt32Value() {
        Expression exp = parseExpression( "1" );

        assertEquals( RiverType.INT32, exp.getType() );
        assertEquals( new CharPosition(0,0,0), exp.getFromNbl() );
        assertEquals( new CharPosition(0,1,1), exp.getToExcNbl() );

        assertExpressionAsJava( exp, "1" );
    }

//    @Test
//    public void minimalClassDeclaration() {
//        Expression exp = parseExpression( "1 + 1" );
//
//        assertExpressionAsJava( exp, "1 + 1" );
//    }

}
