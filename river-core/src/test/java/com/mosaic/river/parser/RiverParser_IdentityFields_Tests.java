package com.mosaic.river.parser;

import com.mosaic.parsers.pull.TextPosition;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Identity fields are the fields declared immediately after the class name. They declare fields just as the normal field
 * declarations but they are also used in the default generation of hashCode, equals and toString.
 */
public class RiverParser_IdentityFields_Tests {

    private RiverParser         parser       = new RiverParser();
    private RiverParserListener callbackMock = mock( RiverParserListener.class );

    @Test
    public void bracketsAfterClassNameButNoIdentityFields_expectEmptyClass() throws IOException {
        parser.parse( new StringReader("User()"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "User");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,7));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void bracketsAndWhiteSpaceAfterClassNameButNoIdentityFields_expectEmptyClass() throws IOException {
        parser.parse( new StringReader("User(   )"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "User");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,10));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void bracketsButNoIdentityFieldsAfterClassNameSpreadOverMultipleLines_expectEmptyClass() throws IOException {
        parser.parse( createReader(" User ","", "(", "", "  ) "), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "User");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(5,3));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void singleStringIdentityField() throws IOException {
        parser.parse( new StringReader("User(name:String)"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className( new TextPosition( 1, 1 ), "User" );
        verify(callbackMock).identityField(new TextPosition(1,6), "String", "name");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,18));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void mismatchedIdentityFieldDelimiter() throws IOException {
        parser.parse( new StringReader("User)"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className( new TextPosition(1,1), "User" );
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,5));
        verify(callbackMock).parseError( new TextPosition(1,5), "Unrecognized symbol ')'" );
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void identityFieldsNotClosedOff_expectError() throws IOException {
        parser.parse( new StringReader("User("), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className( new TextPosition(1,1), "User" );
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,6));
        verify(callbackMock).parseError( new TextPosition(1,6), "Missing closing ')'" );
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void identityFieldsNotClosedOffWithWhiteSpace_expectError() throws IOException {
        parser.parse( new StringReader("User ("), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className( new TextPosition(1,1), "User" );
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,7));
        verify(callbackMock).parseError( new TextPosition(1,7), "Missing closing ')'" );
        verifyNoMoreInteractions(callbackMock);
    }

//    @Test
    public void twoIdentityFields() throws IOException {
        parser.parse( new StringReader("User(name:String,height:Int)"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className( new TextPosition( 1, 1 ), "User" );
        verify(callbackMock).identityField(new TextPosition(1,6), "String", "name");
        verify(callbackMock).identityField(new TextPosition(1,18), "Int", "height");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,29));
        verifyNoMoreInteractions(callbackMock);
    }


    // User(name:String,height:Int)
    // User( name:String, height:Int )


    // User
    // (
    // name
    // :
    // String
    // ,
    // height:Int
    // )

    // User( name: )
    // User( name )
    // User( name String )

    // User( 43:String, height:Int )

//    @Test
//    public void bracketsAfterClassNameButNoIdentityFields() throws IOException {
//        parser.parse( new StringReader("User()"), callbackMock );
//
//        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
//        verify(callbackMock).className(new TextPosition(1,1), "User");
//        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,5));
//        verifyNoMoreInteractions(callbackMock);
//    }


    private Reader createReader( String...lines ) {
        StringBuilder buf = new StringBuilder(200);

        for ( String l : lines ) {
            if ( buf.length() > 0 ) {
                buf.append( "\n" );
            }

            buf.append( l );
        }

        return new StringReader( buf.toString() );
    }
}
