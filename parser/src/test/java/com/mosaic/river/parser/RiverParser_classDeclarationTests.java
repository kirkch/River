package com.mosaic.river.parser;

import com.mosaic.lang.functional.Try;
import com.mosaic.parser.PullParser2;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.river.compiler.model.prettyprint.RiverCodeFormatter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class RiverParser_classDeclarationTests {

    /**
     * Account
     */
    @Test
    public void minimalClassDeclaration() {
        RiverParser p = new RiverParser();

        RiverClass rc = p.parse( "Account" ).getResultNoBlock();

        List<String> expected = Arrays.asList( "public class Account {}" );

        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
    }

    /**
     * Account
     */
    @Test
    public void givenMalformedClassName_expectFailure() {
        RiverParser p  = new RiverParser();
        PullParser2 pp = new PullParser2<>( "22Foo", RiverLexicalTypes.WhiteSpace );

        Try<RiverClass> result = p.parseClass( pp );

        assertTrue( result.hasFailure() );
        assertEquals( "Expected a class name, found '22Foo'", result.getFailureNoBlock().getMessage() );
        assertEquals( 0, pp.getPosition().getColumnNumber() );
        assertEquals( 0, pp.getPosition().getLineNumber() );
    }

}