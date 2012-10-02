package com.mosaic.river.parser;

import com.mosaic.parsers.pull.TextPosition;

/**
 *
 */
public interface  RiverParserListener {

    void parseError( TextPosition position, String message );


    void startOfClassDeclaration( TextPosition position );
    void className( TextPosition position, String className );
    void endOfClassDeclaration( TextPosition position );


    /**
     * Identity fields are fields that are used in the default hashCode, equals and toString methods. e.g. Account( name:String )
     */
    void identityField( TextPosition position, String type, String name );

//    /**
//     * Expression that evaluates to the default value to use for the field if no other value is supplied.  Account( name:String="Bob" )
//     */
//    void startOfDefaultIdentityFieldExpression( TextPosition position, String fieldName );
//    void endOfDefaultIdentityFieldExpression( TextPosition position );


//    void startOfExpression( TextPosition position );
//    void endOfExpression( TextPosition position );

}
