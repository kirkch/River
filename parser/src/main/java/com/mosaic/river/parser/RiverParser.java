//package com.mosaic.river.parser;
//
//
//import com.mosaic.lang.functional.Pair;
//import com.mosaic.lang.functional.Try;
//import com.mosaic.lang.text.PullParser;
//import com.mosaic.parser.MatchResult;
//import com.mosaic.parser.PullParser2;
//import com.mosaic.river.compiler.model.RiverClass;
//import com.mosaic.river.compiler.model.RiverField;
//import com.mosaic.utils.StringUtils;
//import com.mosaic.utils.string.BaseCharacterMatcher;
//import com.mosaic.utils.string.CharacterMatcher;
//import com.mosaic.utils.string.CharacterMatchers;
//
//import static com.mosaic.lang.functional.TryNow.successful;
//import static com.mosaic.river.parser.RiverLexicalTypes.*;
//
//
//@SuppressWarnings("unchecked")
//public class RiverParser {
//
//    public Try<RiverClass> parse( String...lines ) {
//        return parse( StringUtils.concat( "", lines, "\n", "" ) );
//    }
//
//    public Try<RiverClass> parse( CharSequence chars ) {
//        PullParser2 pp = new PullParser2( chars, WhiteSpace );
//
////        RiverClass rc = parseClass(pp);
//
//// pp.getAllMatchesSoFar -- useful for tools that provide text highlighting
//
//        return parseClass( pp );
//    }
//
//    private static CharacterMatcher RIVER_CLASSNAME = CharacterMatchers.regexp( ClassName, "[a-zA-Z][a-zA-Z0-9_$]+" );
//    private static CharacterMatcher BLOCK_START     = CharacterMatchers.constant( StartOfBlock, "{" );
//    private static CharacterMatcher BLOCK_END       = CharacterMatchers.constant( EndOfBlock, "}" );
//    private static CharacterMatcher ARGLIST_START   = CharacterMatchers.constant( StartOfArgList, "(" );
//    private static CharacterMatcher ARGLIST_END     = CharacterMatchers.constant( EndOfArgList, ")" );
//
//    /**
//     *
//     * @BNF CLASSNAME PRIMARY_CONSTRUCTOR
////     * @BNF CLASSNAME { DECLARATION* }
//     */
//    public Try<RiverClass> parseClass( PullParser2 pp ) {
////        return pp.parseWithRollback( () -> {
//            MatchResult classNameMatch = pp.pull( RIVER_CLASSNAME );
//            if ( !classNameMatch.isMatch() ) {
//                return pp.createFailure( "Expected a class name" );
////                return failed( pp.getPosition(), "Expected a class name, found '" + pp.pullToEndOfLine() + "'" );
//            }
//
//            String     className = classNameMatch.getMatchedText().toString();
//            RiverClass rc        = new RiverClass(className);
//
//
//            Try primaryConstructorResult = parsePrimaryConstructor( pp, rc );
//            if ( primaryConstructorResult.hasFailure() ) {
//                return primaryConstructorResult;
//            }
//
//            MatchResult startOfClassMatch = pp.pull( BLOCK_START );
//            if ( !startOfClassMatch.isMatch() ) {
//                return successful(rc);
//            }
//
//
////            pp.parseZeroOrMore(
//////                this::parseMethod, rc::addMethod,
////                new PullParser2.MatcherAndAction<>(new FieldMatcher(),  rc::addField)
////            );
//
//            MatchResult endOfClassMatch = pp.pull( BLOCK_END );
//            if ( !endOfClassMatch.isMatch() ) {
//                return pp.createFailure( "Expected the end of the class '%s' bracket '}'", className );
//            }
//
//            return successful(rc);
////        });
//    }
//
//    private Try parsePrimaryConstructor( PullParser2 pp, RiverClass rc ) {
//        MatchResult startOfArgListMatch = pp.pull( ARGLIST_START );
//        if ( !startOfArgListMatch.isMatch() ) {
//            return pp.createFailure( "Expected the start of the primary constructor '(' for class '%s'", rc.getName() );
//        }
//
//        MatchResult endOfArgListMatch = pp.pull( ARGLIST_END );
//        if ( !endOfArgListMatch.isMatch() ) {
//            return pp.createFailure( "Expected the end of the primary constructor ')' for class '%s'", rc.getName() );
//        }
//
//        return successful( null );
//    }
//
//    private Try<RiverField> parseField( PullParser2 pp ) {
//        return pp.createFailure( "tbd" );
//    }
//
//    private Try<RiverClass> parseDeclaration( PullParser pp ) {
//        return null;
//    }
//
//
//    private static class FieldMatcher extends BaseCharacterMatcher<RiverLexicalTypes> {
//
//        protected FieldMatcher() {
//            super( Field );
//        }
//
//        public int consumeFrom( CharSequence seq, int minIndex, int maxIndexExc ) {
//            return 0;
//        }
//
//    }
//}