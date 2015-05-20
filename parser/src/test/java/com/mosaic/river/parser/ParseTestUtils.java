package com.mosaic.river.parser;

import com.mosaic.lang.QA;
import com.mosaic.parser.ParseResult;
import com.mosaic.parser.ParserStream;
import com.mosaic.river.compiler.model.RiverClass;

import static org.junit.Assert.assertTrue;


/**
 * Collection of utils that help write tests for RiverParser.
 */
class ParseTestUtils {

    protected RiverClass parseAndExpectSuccess( String...source ) {
        RiverParser  p  = new RiverParser();
        ParserStream in = ParserStream.fromText(
            "Constants() {",
            "  a : int32 = 0",
            "}"
        );


        ParseResult<RiverClass> result = p.parseClass( in );
        assertTrue( "Parse failed: " + result, result.matched() );

        RiverClass rc = result.getParsedValueNbl();

        QA.notNull( rc, "rc" );

        return rc;
    }

}
