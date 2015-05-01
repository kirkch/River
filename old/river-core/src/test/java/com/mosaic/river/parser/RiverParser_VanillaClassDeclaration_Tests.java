package com.mosaic.river.parser;

import com.mosaic.parsers.pull.TextPosition;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.mockito.Mockito.*;

/**
 *
 */
public class RiverParser_VanillaClassDeclaration_Tests {

    private RiverParser         parser       = new RiverParser();
    private RiverParserListener callbackMock = mock( RiverParserListener.class );

    @Test
    public void classWithNoFieldsOrMethodsOrAnything() throws IOException {
        parser.parse( new StringReader("User"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "User");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,5));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void simplistRiverFile_classOnly() throws IOException {
        parser.parse( new StringReader("Account"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "Account");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,8));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void simplistRiverFileWithWhitespace_classOnly() throws IOException {
        parser.parse( new StringReader("Account"), callbackMock );

        verify(callbackMock).startOfClassDeclaration(new TextPosition(1,1));
        verify(callbackMock).className(new TextPosition(1,1), "Account");
        verify(callbackMock).endOfClassDeclaration(new TextPosition(1,8));
        verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void expectedClassNameButFoundPercentSymbol_expectError() throws IOException {
        parser.parse( new StringReader("%"), callbackMock );

        verify(callbackMock).parseError(new TextPosition(1,1), "expected 'java variable name'");
        verifyNoMoreInteractions(callbackMock);
    }

}
