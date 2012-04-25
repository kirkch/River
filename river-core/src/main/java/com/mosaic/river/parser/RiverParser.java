package com.mosaic.river.parser;

import com.mosaic.parsers.pull.TextPosition;
import com.mosaic.parsers.pull.Tokenizer;
import com.mosaic.utils.string.CharacterMatcher;
import com.mosaic.utils.string.CharacterMatchers;

import java.io.IOException;
import java.io.Reader;

/**
 *
 */
public class RiverParser {
    private static CharacterMatcher CLASS_NAME_MATCHER = CharacterMatchers.javaVariableName();


    public void parse( Reader in, RiverParserListener l ) throws IOException {
        Tokenizer tokenizer = new Tokenizer( in );
        tokenizer.autoskipWhitespace( true );


        if ( !tokenizer.walk(CLASS_NAME_MATCHER) ) {
            l.parseError( tokenizer.getPosition(), "missing class name" );
        } else {
            TextPosition from = tokenizer.getPosition();
            String className = tokenizer.consume();

            l.startOfClassDeclaration( from );
            l.className( from, className );
            l.endOfClassDeclaration( tokenizer.getPosition() );
        }
    }

}
