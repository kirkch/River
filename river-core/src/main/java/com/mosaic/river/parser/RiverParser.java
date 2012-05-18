package com.mosaic.river.parser;

import com.mosaic.parsers.pull.BNFExpression;
import com.mosaic.parsers.pull.Match;
import com.mosaic.parsers.pull.ParserCallback;
import com.mosaic.parsers.pull.TextPosition;
import com.mosaic.parsers.pull.Tokenizer;
import com.mosaic.utils.string.CharacterMatcher;
import com.mosaic.utils.string.CharacterMatchers;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static com.mosaic.parsers.pull.BNFExpressions.*;

/**
 *
 */
public class RiverParser {
    private static CharacterMatcher CLASS_NAME_MATCHER  = CharacterMatchers.javaVariableName();
    private static CharacterMatcher METHOD_NAME_MATCHER = CharacterMatchers.javaVariableName();
    private static CharacterMatcher TYPE_MATCHER        = CharacterMatchers.javaVariableType();


    private static BNFExpression CLASS_BNF = matches(CLASS_NAME_MATCHER);
    private static BNFExpression IDENTITY_FIELD_BNF = and( matches(METHOD_NAME_MATCHER), discard(constant(":")), matches(TYPE_MATCHER) )
        .onMatch(
            new ParserCallback<RiverParserListener, List>() {
                public Match<List<String>> onResult( RiverParserListener l, Match<List<String>> match ) {
                    List<String> args      = match.getValue();
                    String       fieldName = args.get(0);
                    String       fieldType = args.get(1);

                    l.identityField( match.getPos(), fieldType, fieldName );

                    return match;
                }
            }
        );
//    private static BNFExpression IDENTITY_FIELD_BNF = and( discard(constant("(")), matches(METHOD_NAME_MATCHER), discard(constant(":")) );


    public void parse( Reader in, RiverParserListener l ) throws IOException {
        Tokenizer tokenizer = new Tokenizer( in );
        tokenizer.autoskipWhitespace( true );


        if ( parseClass(tokenizer, l) ) {
            if ( !tokenizer.isEOF() ) {
                l.parseError( tokenizer.getPosition(), "Unrecognized symbol ')'" );
            }
        }
    }

    private boolean parseClass( Tokenizer tokenizer, RiverParserListener l ) throws IOException {
        if ( !tokenizer.walk(CLASS_NAME_MATCHER) ) {
            l.parseError( tokenizer.getPosition(), "missing class name" );

            return false;
        } else {
            TextPosition from = tokenizer.getPosition();
            String className = tokenizer.consume();

            l.startOfClassDeclaration( from );
            l.className( from, className );

            parseIdentityFields( tokenizer, l );

            l.endOfClassDeclaration( tokenizer.getPosition() );

            return true;
        }
    }


    // Parser p = new Parser();
    // p.register( "IDENTITY_FIELD",  keep(FIELDNAME), ":", keep(TYPE) ).withCallback()
    // p.register( "IDENTITY_FIELDS", seq(IDENTITY_FIELD) )
    // p.register( "CLASS",           keep(CLASSNAME), opt("(", keep(IDENTITY_FIELDS), ")") )




    // CLASSNAME <~ ["(" ~> IDENTITY_FIELDS <~ ")"]      => callback
    // CLASSNAME <~ opt("(" ~> IDENTITY_FIELDS <~ ")")
    // IDENTITY_FIELDS = seq( IDENTITY_FIELD )
    // IDENTITY_FIELD  = FIELDNAME <~ ":" ~> TYPE

    private void parseIdentityFields( Tokenizer tokenizer, RiverParserListener l ) throws IOException {
        if ( !consume(tokenizer,"(") ) return;


        parseIdentityField( tokenizer, l );


        consume( tokenizer, l, ")", "Missing closing ')'" );
    }

    private boolean parseIdentityField( Tokenizer tokenizer, RiverParserListener l ) throws IOException {

        if ( !tokenizer.walk(METHOD_NAME_MATCHER) ) {
            return false;
        }

        TextPosition fieldPos = tokenizer.getPosition();


        String fieldName = tokenizer.consume();

        tokenizer.walkConstant( ":" );
        tokenizer.consume();

        tokenizer.walk( TYPE_MATCHER );

        String type = tokenizer.consume();

        l.identityField( fieldPos, type, fieldName );

        return true;
    }

    private boolean consume( Tokenizer tokenizer, String target ) throws IOException {
        if ( tokenizer.walkConstant(target) ) {
            tokenizer.consume();

            return true;
        }

        return false;
    }

    private boolean consume( Tokenizer tokenizer, RiverParserListener l, String target, String errorIfMissing ) throws IOException {
        if ( tokenizer.walkConstant(target) ) {
            tokenizer.consume();

            return true;
        }

        l.parseError( tokenizer.getPosition(), errorIfMissing );
        tokenizer.undoWalk();

        return false;
    }

}
