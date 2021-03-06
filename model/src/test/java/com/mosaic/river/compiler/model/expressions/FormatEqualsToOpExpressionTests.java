package com.mosaic.river.compiler.model.expressions;

import com.mosaic.river.compiler.model.RiverAssertions;
import com.mosaic.river.compiler.model.exp.BinaryOp;
import com.mosaic.river.compiler.model.exp.BinaryOpEnum;
import com.mosaic.river.compiler.model.exp.ConstantInt32;
import com.mosaic.river.compiler.model.exp.Expression;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class FormatEqualsToOpExpressionTests extends RiverAssertions {

    @Test
    public void parseASingleTwoConstantInt32Expression() {
        Expression exp = new BinaryOp( new ConstantInt32(1), BinaryOpEnum.EQUALS_TO, new ConstantInt32(2) );

        List<String> expectedRiverCode = Arrays.asList( "1 == 2" );
        List<String> expectedJavaCode  = Arrays.asList( "1 == 2" );

        assertRiverExpressionEquals( exp, expectedRiverCode, expectedJavaCode );
    }

}
