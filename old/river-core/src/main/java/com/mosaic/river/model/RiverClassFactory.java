package com.mosaic.river.compiler.model;

import com.mosaic.parsers.pull.TextPosition;
import com.mosaic.river.parser.RiverParserListener;

/**
 * Factory object that listens to events from the RiverParser and constructs a object model of the river classes.
 */
public class RiverClassFactory implements RiverParserListener {

    private RiverClass rc;

    @Override
    public void parseError( TextPosition position, String message ) {
    }

    @Override
    public void startOfClassDeclaration( TextPosition position ) {
        rc = new RiverClass()
            .withClassStartsAt( position );
    }

    @Override
    public void className( TextPosition position, String className ) {
        rc.withClassName( className );
    }

    @Override
    public void identityField( TextPosition position, String type, String name ) {
        rc.withIdentityField( new RiverTypeRef(type), name );
    }

    @Override
    public void endOfClassDeclaration( TextPosition position ) {
        rc.lock();
    }


    public RiverClass build() {
        if ( rc == null ) {
            return null;
        }

        rc.lock();

        return rc;
    }

}
