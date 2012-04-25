package com.mosaic.lang.bytegen.river;

import com.mosaic.lang.bytegen.ParseError;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class RiverClass {

    private String           className;
    private List<ParseError> errors = new LinkedList<ParseError>();


    public String getClassName() { return className; }
    public void setClassName( String className ) { this.className = className; }

    public int getStaticMethodCount() { return 0; }
    public int getMethodCount() { return 0; }


    public List<ParseError> getErrors() { return errors; }

    public void appendError( ParseError parseError ) {
        errors.add( parseError );
    }

    public void appendError( int lineNumber, int columnNumber, String description ) {
        appendError( new ParseError(lineNumber,columnNumber,description) );
    }

}
