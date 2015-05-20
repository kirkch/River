package com.mosaic.river.parser;

import com.mosaic.parser.ParseResult;
import com.mosaic.parser.ParserStream;
import com.mosaic.river.compiler.model.RiverClass;
import org.junit.Test;

import static org.junit.Assert.*;


public class ParseRiverClassDeclarationTests extends ParseTestUtils {

    @Test
    public void minimalClassDeclaration() {
        RiverClass rc = parseAndExpectSuccess( "Account()" );

        assertRiverClassAsJava(rc, "public class Account {}");
    }

    @Test
    public void classDeclarationWithEmptyConstructorAndBody() {
        RiverClass rc = parseAndExpectSuccess( "Account() {}" );

        assertRiverClassAsJava( rc, "public class Account {}" );
    }

    @Test
    public void classDeclarationWithEmptyConstructorAndBody_withWhiteSpaceAndNewLines() {
        RiverClass rc = parseAndExpectSuccess(
            "   Account",
            "()",
            "{",
            "    ",
            "}"
        );

        assertRiverClassAsJava( rc, "public class Account {}" );
    }

    @Test
    public void minimalClassDeclaration_withWhiteSpace() {
        RiverClass rc = parseAndExpectSuccess( "  Account\t ( \t )  " );

        assertRiverClassAsJava( rc, "public class Account {}" );
    }

    @Test
    public void givenMalformedClassName_expectError() {
        RiverParser  p  = new RiverParser();
        ParserStream in = new ParserStream( "22Foo" );

        ParseResult<RiverClass> result = p.parseClass( in );

        assertFalse( result.matched() );
        assertEquals( "Expected a class name", result.getErrorMessageNbl() );
        assertEquals( 0, in.getCurrentPosition().getColumnNumber() );
        assertEquals( 0, in.getCurrentPosition().getLineNumber() );
    }
    
    @Test
    public void givenClassNameWithoutParamList_expectError() {
        RiverParser  p  = new RiverParser();
        ParserStream in = new ParserStream( "Account" );

        ParseResult<RiverClass> result = p.parseClass( in );

        assertFalse( result.matched() );
        assertEquals( "Expected a list of args, but the starting bracket '(' was missing", result.getErrorMessageNbl() );
        assertEquals( 0, in.getCurrentPosition().getColumnNumber() ); // zero because parse rolls back after a failure
        assertEquals( 0, in.getCurrentPosition().getLineNumber() );
    }

    @Test
    public void givenClassNameWithoutEndOfParamList_expectError() {
        RiverParser  p  = new RiverParser();
        ParserStream in = new ParserStream( "Account(" );

        ParseResult<RiverClass> result = p.parseClass( in );

        assertFalse( result.matched() );
        assertEquals( "Expected a list of args, but the closing bracket ')' was missing", result.getErrorMessageNbl() );
        assertEquals( 0, in.getCurrentPosition().getColumnNumber() ); // zero because parse rolls back after a failure
        assertEquals( 0, in.getCurrentPosition().getLineNumber() );
    }

    @Test
    public void givenClassWithMissingEndOfClassBlockBracket() {
        RiverParser  p  = new RiverParser();
        ParserStream in = new ParserStream( "Account() {" );

        ParseResult<RiverClass> result = p.parseClass( in );

        assertFalse( result.matched() );
        assertEquals( "Expected the end of the class 'Account' bracket '}'", result.getErrorMessageNbl() );
        assertEquals( 0, in.getCurrentPosition().getColumnNumber() ); // zero because parse rolls back after a failure
        assertEquals( 0, in.getCurrentPosition().getLineNumber() );
    }

//    /**
//     * Account
//     */
//    @Test
//    public void minimalClassDeclaration() {
//        RiverParser p = new RiverParser();
//
//        RiverClass rc = p.parse( "Account" ).getParsedValueNbl();
//
//        List<String> expected = Arrays.asList( "public class Account {}" );
//
//        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
//    }

}