package com.mosaic.river.parser;

import com.mosaic.io.CharPosition;
import com.mosaic.parser.ParseResult;
import com.mosaic.parser.Parser;
import com.mosaic.parser.ParserStream;
import com.mosaic.parser.ParserStream.ParserAndAction;
import com.mosaic.river.compiler.model.RiverType;
import com.mosaic.river.compiler.model.exp.BinaryOp;
import com.mosaic.river.compiler.model.exp.BinaryOpEnum;
import com.mosaic.river.compiler.model.exp.ConstantInt32;
import com.mosaic.river.compiler.model.RiverArgList;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.compiler.model.RiverField;
import com.mosaic.utils.StringUtils;
import com.mosaic.utils.string.CharacterMatcher;
import com.mosaic.utils.string.CharacterMatchers;


@SuppressWarnings("unchecked")
public class RiverParser {

    private static Parser<RiverClass> CLASS_PARSER      = new ClassParser();
    private static Parser<Expression> EXPRESSION_PARSER = new ExpressionParser();

    public ParseResult<RiverClass> parse( String...lines ) {
        return parse( StringUtils.concat( "", lines, "\n", "" ) );
    }

    public ParseResult<RiverClass> parse( CharSequence chars ) {
        return parseClass( new ParserStream(chars) );
    }



    /**
     *
     * @BNF CLASSNAME PRIMARY_CONSTRUCTOR
//     * @BNF CLASSNAME { DECLARATION* }
     */
    public ParseResult<RiverClass> parseClass( ParserStream in ) {
        return CLASS_PARSER.parseFrom( in );
    }

    public ParseResult<Expression> parseExpression( ParserStream in ) {
        return EXPRESSION_PARSER.parseFrom( in );
    }


    /**
     * @BNF CLASS     :=  CLASSNAME PRIMARY_CONSTRUCTOR
     * @BNF CLASSNAME := [a-zA-Z][a-zA-Z0-9_$]+
     */
    private static class ClassParser extends Parser<RiverClass> {
        private static CharacterMatcher RIVER_CLASSNAME = CharacterMatchers.regexp( "[a-zA-Z][a-zA-Z0-9_$]*" );
        private static CharacterMatcher BLOCK_START     = CharacterMatchers.constant( "{" );
        private static CharacterMatcher BLOCK_END       = CharacterMatchers.constant( "}" );


        private static ArgListParser ARG_LIST_PARSER = new ArgListParser();
        private static FieldParser   FIELD_PARSER    = new FieldParser();


        protected ParseResult<RiverClass> doParse( ParserStream in ) {
            CharPosition classStart = in.getCurrentPosition();

            String className = in.consume( RIVER_CLASSNAME );
            if ( className == null ) {
                return failure( classStart, "Expected a class name" );
            }

            RiverClass rc = new RiverClass( className );

            ParseResult withArgsResult = ARG_LIST_PARSER.parseFrom( in );
            if ( !withArgsResult.matched() ) { // todo DIRTY
                return withArgsResult;
            }


            in.skipWhitespace();

            if ( in.skip(BLOCK_START) == 0 ) {
                return matched( rc, classStart, in.getCurrentPosition() );
            }

            in.parseZeroOrMore(
                new ParserAndAction<>(FIELD_PARSER, rc::addField)
            );

            in.skipWhitespace();

            if ( in.skip(BLOCK_END) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected the end of the class '%s' bracket '}'", className );
            }


            CharPosition classEnd = in.getCurrentPosition();

            return matched( rc, classStart, classEnd );
        }
    }

    /**
     * @BNF ARG_LIST := '(' ')'
     */
    private static class ArgListParser extends Parser<RiverArgList> {
        private static CharacterMatcher ARGLIST_START   = CharacterMatchers.constant( "(" );
        private static CharacterMatcher ARGLIST_END     = CharacterMatchers.constant( ")" );

        protected ParseResult<RiverArgList> doParse( ParserStream in ) {
            CharPosition from = in.getCurrentPosition();
            if ( in.skip(ARGLIST_START) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected a list of args, but the starting bracket '(' was missing" );
            }

            in.skipWhitespace();

            if ( in.skip(ARGLIST_END) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected a list of args, but the closing bracket ')' was missing" );
            }

            return matched( new RiverArgList(), from, in.getCurrentPosition() );
        }
    }

    //a : int32 = 0

    /**
     * @BNF FIELD := FIELD_NAME ':' TYPE '=' 0
     */
    private static class FieldParser extends Parser<RiverField> {
        private static CharacterMatcher FIELDNAME_MATCHER      = CharacterMatchers.regexp( "[a-zA-Z][a-zA-Z0-9_$]*" );
        private static CharacterMatcher FIELDTYPE_MATCHER      = CharacterMatchers.regexp( "[a-zA-Z][a-zA-Z0-9_$]*" );
        private static CharacterMatcher TYPE_SEPARATOR_MATCHER = CharacterMatchers.constant( ":" );
        private static CharacterMatcher EQUALS_MATCHER         = CharacterMatchers.constant( "=" );

        private static CharacterMatcher VALUE_MATCHER         = CharacterMatchers.constant( "0" );

        protected ParseResult<RiverField> doParse( ParserStream in ) {
            CharPosition from = in.getCurrentPosition();

            String fieldName = in.consume( FIELDNAME_MATCHER );
            if ( fieldName == null ) {
                return noMatch( from );
            }

            in.skipWhitespace();


            if ( in.skip(TYPE_SEPARATOR_MATCHER) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected ':' after the field name '%s'", fieldName );
            }

            in.skipWhitespace();
            String fieldType = in.consume( FIELDTYPE_MATCHER );
            if ( !fieldType.equals("int32") ) {
                return failure( in.getCurrentPosition(), "Expected 'int32' after the field '%s'", fieldName );
            }

            in.skipWhitespace();

            if ( in.skip(EQUALS_MATCHER) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected '=' after the type of the field '%s'", fieldName );
            }

            in.skipWhitespace();

            if ( in.skip(VALUE_MATCHER) == 0 ) {
                return failure( in.getCurrentPosition(), "Expected '0' after the assignment operator", fieldName );
            }

            Expression initialValue = new ConstantInt32( 0 );
            RiverField      field        = new RiverField(fieldName, initialValue);

            return matched( field, from, in.getCurrentPosition() );
        }
    }

    /**
     * @BNF EXP := NUM
     */
    private static class ExpressionParser extends Parser<Expression> {
//        private static CharacterMatcher NUM_MATCHER = CharacterMatchers.regexp( "[-+]?[0-9]+" );
        private static CharacterMatcher NUM_MATCHER = CharacterMatchers.integer( true );
        private static CharacterMatcher OP_MATCHER  = CharacterMatchers.constant( "+" );

        protected ParseResult<Expression> doParse( ParserStream in ) {
            ParseResult<Expression> lhs = in.parse(NUM_MATCHER).map( v -> new ConstantInt32(Integer.parseInt(v.replaceAll(",",""))) );

            if ( !lhs.matched() ) {
                return lhs;
            }

            return recursiveParse( lhs, in );
        }

        private ParseResult<Expression> recursiveParse( ParseResult<Expression> lhs, ParserStream in ) {
            in.skipWhitespace();

            String op = in.consume( OP_MATCHER );
            if ( op == null ) {
                return lhs;
            }

            in.skipWhitespace();

            ParseResult<Expression> rhs = in.parse(NUM_MATCHER).map( v -> new ConstantInt32(Integer.parseInt(v)) );
            if ( !rhs.matched() ) {
                return lhs;
            }

            Expression exp = new BinaryOp( lhs.getParsedValueNbl(), BinaryOpEnum.ADD, rhs.getParsedValueNbl() );
            exp.setType( RiverType.INT32 );

            return recursiveParse( ParseResult.matchSucceeded( exp, lhs.getFrom(), rhs.getToExc() ), in );
        }
    }

}