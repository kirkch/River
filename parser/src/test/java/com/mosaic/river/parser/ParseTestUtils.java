package com.mosaic.river.parser;

import com.mosaic.io.IndentingWriter;
import com.mosaic.lang.QA;
import com.mosaic.lang.system.SystemX;
import com.mosaic.parser.ParseResult;
import com.mosaic.parser.ParserStream;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.utils.ArrayUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Collection of utils that help write tests for RiverParser.
 */
public class ParseTestUtils {

    protected static RiverClass parseAndExpectSuccess( String...source ) {
        RiverParser  p  = new RiverParser();
        ParserStream in = ParserStream.fromText(source);


        ParseResult<RiverClass> result = p.parseClass( in );
        assertTrue( "Parse failed: " + result, result.matched() );

        RiverClass rc = result.getParsedValueNbl();

        QA.notNull( rc, "rc" );

        return rc;
    }

    protected static void assertRiverClassAsJava( RiverClass rc, String...expectedJavaLines ) {
        String expected = ArrayUtils.toString( expectedJavaLines, SystemX.NEWLINE );

        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
    }



    protected static Expression parseExpression( String...source ) {
        RiverParser  p  = new RiverParser();
        ParserStream in = ParserStream.fromText(source);


        ParseResult<Expression> result = p.parseExpression( in );
        assertTrue( "Parse failed: " + result, result.matched() );

        Expression exp = result.getParsedValueNbl();

        QA.notNull( exp, "exp" );

        return exp;
    }

    protected static void assertExpressionAsJava( Expression exp, String...expectedJavaLines ) {
        String expected = ArrayUtils.toString( expectedJavaLines, SystemX.NEWLINE );

        StringBuilder   buf = new StringBuilder();
        IndentingWriter out = IndentingWriter.toIndentingWriter( buf );
        JavaCodeFormatter.INSTANCE.printExpression(out, exp);

        assertEquals( expected, buf.toString() );
    }

}
