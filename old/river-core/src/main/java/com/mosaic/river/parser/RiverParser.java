package com.mosaic.river.parser;

import com.mosaic.parsers.pull.BNFExpression;
import com.mosaic.parsers.pull.Match;
import com.mosaic.parsers.pull.ParserCallback;
import com.mosaic.parsers.pull.PullParser;
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



    private static BNFExpression IDENTITY_FIELD_BNF = and( matches(METHOD_NAME_MATCHER), discard(constant(":")), matches(TYPE_MATCHER) )
        .onMatch(
            new ParserCallback<RiverParserListener, List>() {
                public Match<List> onResult( RiverParserListener l, Match<List> match ) {
                    List<String> args      = match.getValue();
                    String       fieldName = args.get(0);
                    String       fieldType = args.get(1);

                    l.identityField( match.getPos(), fieldType, fieldName );

                    return match;
                }
            }
        );

    private static BNFExpression IDENTITY_FIELDS_BNF = and( discard(constant("(")), optional(repeatableWithSeparator(IDENTITY_FIELD_BNF, matches(","))), discard(constant(")")) );

    private static BNFExpression CLASS_NAME_BNF = matches(CLASS_NAME_MATCHER).onMatch(
        new ParserCallback<RiverParserListener, String>() {
            public Match<String> onResult( RiverParserListener l, Match<String> match ) {
                String       className = match.getValue();
                TextPosition pos       = match.getPos();

                l.startOfClassDeclaration( pos );
                l.className( pos, className );

                return match;
            }
        }
    );

    private static BNFExpression CLASS_BNF = and( CLASS_NAME_BNF, optional( IDENTITY_FIELDS_BNF ), eof() );



    private static final PullParser p = new PullParser();

    static {
        p.register( "CLASS", CLASS_BNF );
    }


    public void parse( Reader in, RiverParserListener l ) throws IOException {
        Tokenizer tokenizer = Tokenizer.autoskipWhitespace( in );
        Match     result    = p.parse( "CLASS", l, tokenizer );

        if ( result.isError() ) {
            l.parseError( result.getPos(), result.getError().toString() );
        } else {
            l.endOfClassDeclaration( tokenizer.getPosition() );
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


}
