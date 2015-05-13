//package com.mosaic.river.parser;
//
//import com.mosaic.lang.functional.Try;
//import com.mosaic.parser.PullParser2;
//import com.mosaic.river.compiler.model.RiverClass;
//import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//
//public class ParseRiverClassDeclarationTests {
//
//    /**
//     * Account()
//     */
//    @Test
//    public void minimalClassDeclaration() {
//        RiverParser p = new RiverParser();
//
//        RiverClass rc = p.parse( "Account()" ).getResultNoBlock();
//
//        List<String> expected = Arrays.asList( "public class Account {}" );
//
//        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
//    }
//
//    @Test
//    public void classDeclarationWithEmptyConstructorAndBody() {
//        RiverParser p = new RiverParser();
//
//        RiverClass rc = p.parse( "Account() {}" ).getResultNoBlock();
//
//        List<String> expected = Arrays.asList( "public class Account {}" );
//
//        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
//    }
//
//    @Test
//    public void classDeclarationWithEmptyConstructorAndBody_withWhiteSpaceAndNewLines() {
//        RiverParser p = new RiverParser();
//
//        RiverClass rc = p.parse(
//            "   Account",
//            "()",
//            "{",
//            "    ",
//            "}"
//        ).getResultNoBlock();
//
//        List<String> expected = Arrays.asList( "public class Account {}" );
//
//        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
//    }
//
//    @Test
//    public void minimalClassDeclaration_withWhiteSpace() {
//        RiverParser p = new RiverParser();
//
//        RiverClass rc = p.parse( "  Account\t ( \t )  " ).getResultNoBlock();
//
//        List<String> expected = Arrays.asList( "public class Account {}" );
//
//        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
//    }
//
//    /**
//     * 22Foo
//     */
//    @Test
//    public void givenMalformedClassName_expectError() {
//        RiverParser p  = new RiverParser();
//        PullParser2 pp = new PullParser2<>( "22Foo", RiverLexicalTypes.WhiteSpace );
//
//        Try<RiverClass> result = p.parseClass( pp );
//
//        assertTrue( result.hasFailure() );
//        assertEquals( "Expected a class name, instead found '22Foo'", result.getFailureNoBlock().getMessage() );
//        assertEquals( 0, pp.getPosition().getColumnNumber() );
//        assertEquals( 0, pp.getPosition().getLineNumber() );
//    }
//
//
//    @Test
//    public void givenClassNameWithoutParamList_expectError() {
//        RiverParser p  = new RiverParser();
//        PullParser2 pp = new PullParser2<>( "Account", RiverLexicalTypes.WhiteSpace );
//
//        Try<RiverClass> result = p.parseClass( pp );
//
//        assertTrue( result.hasFailure() );
//        assertEquals( "Expected the start of the primary constructor '(' for class 'Account', instead found ''", result.getFailureNoBlock().getMessage() );
//        assertEquals( 7, pp.getPosition().getColumnNumber() );
//        assertEquals( 0, pp.getPosition().getLineNumber() );
//    }
//
//    @Test
//    public void givenClassNameWithoutEndOfParamList_expectError() {
//        RiverParser p  = new RiverParser();
//        PullParser2 pp = new PullParser2<>( "Account(", RiverLexicalTypes.WhiteSpace );
//
//        Try<RiverClass> result = p.parseClass( pp );
//
//        assertTrue( result.hasFailure() );
//        assertEquals( "Expected the end of the primary constructor ')' for class 'Account', instead found ''", result.getFailureNoBlock().getMessage() );
//        assertEquals( 8, pp.getPosition().getColumnNumber() );
//        assertEquals( 0, pp.getPosition().getLineNumber() );
//    }
//
//    @Test
//    public void givenClassWithMissingEndOfClassBlockBracket() {
//        RiverParser p  = new RiverParser();
//        PullParser2 pp = new PullParser2<>( "Account() {", RiverLexicalTypes.WhiteSpace );
//
//        Try<RiverClass> result = p.parseClass( pp );
//
//        assertTrue( result.hasFailure() );
//        assertEquals( "Expected the end of the class 'Account' bracket '}', instead found ''", result.getFailureNoBlock().getMessage() );
//        assertEquals( 11, pp.getPosition().getColumnNumber() );
//        assertEquals( 0, pp.getPosition().getLineNumber() );
//    }
//
////    /**
////     * Account
////     */
////    @Test
////    public void minimalClassDeclaration() {
////        RiverParser p = new RiverParser();
////
////        RiverClass rc = p.parse( "Account" ).getResultNoBlock();
////
////        List<String> expected = Arrays.asList( "public class Account {}" );
////
////        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
////    }
//
//}