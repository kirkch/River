package com.mosaic.river.compiler.model.builders;

import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.river.compiler.model.prettyprint.RiverCodeFormatter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;


public class RiverBuilderTest {

    /**
     * Sum()
     */
    @Test
    public void declareEmptyClass() {
        RiverClassBuilder  classBuilder  = new RiverClassBuilder("Sum");

        List<String> expectedRiverCode = Arrays.asList( "Sum()" );
        List<String> expectedJavaCode  = Arrays.asList( "public class Sum() {}" );

        RiverClass c = classBuilder.build();

        assertRiverClassEquals( c, expectedRiverCode, expectedJavaCode );
    }



//    /**
//     * Sum() {
//     *   result() = 1 + 1
//     * }
//     */
//    public void declareSum() {
//        RiverClassBuilder  classBuilder  = new RiverClassBuilder("Sum");
//        RiverMethodBuilder methodBuilder = classBuilder.addMethod("result");
//
//        methodBuilder.setReturnType( "int32" );
//
//        RiverCodeBlockBuilder codeBlockBuilder = methodBuilder.codeBlock();
//        RiverStatementBuilder statementBuilder = codeBlockBuilder.addStatement();
//
//        statementBuilder.appendPushInt32( 1 );
//        statementBuilder.appendPushInt32( 1 );
//        statementBuilder.appendSum();
//        statementBuilder.appendReturn();
//
//
//        List<String> expected = Arrays.asList(
//            "Sum() {",
//            "  result() : int32 = {",
//            "    return 1 + 1",
//            "  }",
//            "}"
//        );
//
//        RiverClass c = classBuilder.build();
//
//        assertEquals( expected, c.prettyPrint() );
//    }

//    /**
//     * Sum( a:int32, b:int32 ) {
//     *   result() = a + b
//     * }
//     */
//    public void declareSum() {
//        RiverClassBuilder  classBuilder  = new RiverClassBuilder("Sum");
//        RiverMethodBuilder methodBuilder = classBuilder.addMethod("result");
//
//        methodBuilder.setReturnType("int32");
//        methodBuilder.addArg("a", "int32");
//        methodBuilder.addArg("b", "int32");
//
//        RiverCodeBlockBuilder codeBlockBuilder = methodBuilder.codeBlock();
//        RiverStatementBuilder statementBuilder = codeBlockBuilder.addStatement();
//
//        statementBuilder.appendPushArg("a");
//        statementBuilder.appendPushArg("b");
//        statementBuilder.appendSum();
//        statementBuilder.appendReturn();
//    }



    private void assertRiverClassEquals( RiverClass c, List<String> expectedRiverCode, List<String> expectedJavaCode ) {
        assertEquals( expectedRiverCode, RiverCodeFormatter.INSTANCE.toText(c) );
        assertEquals( expectedJavaCode,  JavaCodeFormatter.INSTANCE.toText(c) );
    }

}




// pushClass("Sum")
// addMethod("result")
// setReturnType("int32")
// addArg("a", "int32")
// addArg("b", "int32")
// pushCodeBlock()
// pushArg("a")
// pushArg("b")
// add()
// returnResult()