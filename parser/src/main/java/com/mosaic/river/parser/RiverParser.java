package com.mosaic.river.parser;


import com.mosaic.lang.functional.Try;
import com.mosaic.lang.text.PullParser;
import com.mosaic.parser.MatchResult;
import com.mosaic.parser.PullParser2;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.utils.string.CharacterMatcher;
import com.mosaic.utils.string.CharacterMatchers;

import static com.mosaic.lang.functional.TryNow.successful;
import static com.mosaic.river.parser.RiverLexicalTypes.*;


public class RiverParser {

    public Try<RiverClass> parse( CharSequence chars ) {
        PullParser2 pp = new PullParser2( chars, WhiteSpace );

//        RiverClass rc = parseClass(pp);

// pp.getAllMatchesSoFar -- useful for tools that provide text highlighting

        return parseClass( pp );
    }

    private static CharacterMatcher RIVER_CLASSNAME = CharacterMatchers.regexp( ClassName, "[a-zA-Z][a-zA-Z0-9_$]+" );
    private static CharacterMatcher BLOCK_START     = CharacterMatchers.constant( StartOfBlock, "{" );
    private static CharacterMatcher BLOCK_END       = CharacterMatchers.constant( EndOfBlock, "}" );

    /**
     *
     * @BNF CLASSNAME { DECLARATION* }
     */
    public Try<RiverClass> parseClass( PullParser2 pp ) {
//        return pp.parseWithRollback( () -> {
            MatchResult classNameMatch = pp.pull( RIVER_CLASSNAME );
            if ( !classNameMatch.isMatch() ) {
                return pp.createFailure( "Expected a class name" );
//                return failed( pp.getPosition(), "Expected a class name, found '" + pp.pullToEndOfLine() + "'" );
            }

            String     className = classNameMatch.getMatchedText().toString();
            RiverClass rc        = new RiverClass(className);


////            MatchResult startOfClassMatch = pp.pull( BLOCK_START );
////            if ( !startOfClassMatch.isMatch() ) {
////                return pp.createFailure( "Expected the start of class marker '{' for class '%s'", className );
//////                return failed( pp.getPosition(), "Expected the end of class marker '}' for class '%s', but found '%s' instead", className, pp.pullToEndOfLine() );
////            }
////
////
////            pp.parseZeroOrMore(
////                this::parseField,  rc::addField,
////                this::parseMethod, rc::addMethod
////            );
////
////            MatchResult endOfClassMatch = pp.pull( BLOCK_END );
////            if ( !endOfClassMatch.isMatch() ) {
////                return pp.createFailure( "Expected the end of class marker '}' for class '%s'", className );
//////                return failed( pp.getPosition(), "Expected the end of class marker '}' for class '%s', but found '%s' instead", className, pp.pullToEndOfLine() );
////            }
//
            return successful(rc);
//        });
    }

    private Try<RiverClass> parseDeclaration( PullParser pp ) {
        return null;
    }
}

//class MatchResult {
//
//}
//
//
//import com.mosaic.river.compiler.model.RiverClass;
//import com.mosaic.utils.string.CharacterMatcher;
//import com.mosaic.utils.string.CharacterMatchers;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.util.List;
//
//
//public class RiverParser {
//
//    public List<RiverClass> parseFile( File f ) {
//        return null;
//    }
//
//    public List<RiverClass> parseInputStream( InputStream in ) {
//        return null;
//    }
//
//
//
//    private static CharacterMatcher CLASS_NAME_MATCHER  = CharacterMatchers.javaVariableName();
//    private static CharacterMatcher METHOD_NAME_MATCHER = CharacterMatchers.javaVariableName();
//    private static CharacterMatcher TYPE_MATCHER        = CharacterMatchers.javaVariableType();
//
//
//
//    private static BNFExpression IDENTITY_FIELD_BNF = and( matches(METHOD_NAME_MATCHER), discard(constant(":")), matches(TYPE_MATCHER) )
//        .onMatch(
//            new ParserCallback<RiverParserListener, List>() {
//                public Match<List> onResult( RiverParserListener l, Match<List> match ) {
//                    List<String> args      = match.getValue();
//                    String       fieldName = args.get(0);
//                    String       fieldType = args.get(1);
//
//                    l.identityField( match.getPos(), fieldType, fieldName );
//
//                    return match;
//                }
//            }
//        );
//
//    private static BNFExpression IDENTITY_FIELDS_BNF = and( discard(constant("(")), optional(repeatableWithSeparator(IDENTITY_FIELD_BNF, matches(","))), discard(constant(")")) );
//
//    private static BNFExpression CLASS_NAME_BNF = matches(CLASS_NAME_MATCHER).onMatch(
//        new ParserCallback<RiverParserListener, String>() {
//            public Match<String> onResult( RiverParserListener l, Match<String> match ) {
//                String       className = match.getValue();
//                TextPosition pos       = match.getPos();
//
//                l.startOfClassDeclaration( pos );
//                l.className( pos, className );
//
//                return match;
//            }
//        }
//    );
//
//    private static BNFExpression CLASS_BNF = and( CLASS_NAME_BNF, optional( IDENTITY_FIELDS_BNF ), eof() );
//
//
//
//    private static final PullParser p = new PullParser();
//
//    static {
//        p.register( "CLASS", CLASS_BNF );
//    }
//
//
//    public void parse( Reader in, RiverParserListener l ) throws IOException {
//        Tokenizer tokenizer = Tokenizer.autoskipWhitespace( in );
//        Match     result    = p.parse( "CLASS", l, tokenizer );
//
//        if ( result.isError() ) {
//            l.parseError( result.getPos(), result.getError().toString() );
//        } else {
//            l.endOfClassDeclaration( tokenizer.getPosition() );
//        }
//    }
//}
