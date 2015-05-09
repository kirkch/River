package com.mosaic.river.compiler.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.mosaic.river.compiler.model.RiverAssertions.assertRiverClassEquals;


public class FormatRiverClassDeclarationTests {

    /**
     * The simplest class that can be declared in River is a class with no parameters, fields
     * or methods.
     *
     * <code>
     *   Sum()
     * </code>
     */
    @Test
    public void declareEmptyClass() {
        RiverClass c = new RiverClass("Sum");

        List<String> expectedRiverCode = Arrays.asList( "Sum()" );
        List<String> expectedJavaCode  = Arrays.asList( "public class Sum {}" );

        assertRiverClassEquals( c, expectedRiverCode, expectedJavaCode );
    }



//    /**
//     * To declare a function in River, postfix a name with brackets and assign a value to it.
//     *
//     * <code>
//     *   Sum() {
//     *     result() = 1 + 1
//     *   }
//     * </code>
//     */
//    public void declareASimpleFunction() {
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
//
//        RiverClass c = classBuilder.build();
//
//        List<String> expectedRiverCode = Arrays.asList(
//            "Sum() {",
//            "  result() : int32 = 1 + 1",
//            "}"
//        );
//
//        List<String> expectedJavaCode = Arrays.asList(
//            "public class Sum {",
//            "  public int result() {",
//            "    return 1 + 1",
//            "  }",
//            "}"
//        );
//
//
//        assertRiverClassEquals( c, expectedRiverCode, expectedJavaCode );
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