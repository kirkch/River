package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.bytegen.CompilerFeedback;

import java.util.LinkedList;
import java.util.List;

/**
 * Runtime context used while walking the JVMClass tree to generate the bytes for a new class. Captures errors and
 * provides progress feedback.
 *
 * NB avoid
 */
public class ClassGenerationContext {

    private List<CompilerFeedback> messages = new LinkedList();
    private int errorCount;
    private int warningCount;

    private String currentSourceFile;
    private String currentClass;
    private String currentMethod;
    private int    currentLineNumber;
    private int    currentColumnNumber;


    public void throwErrors() throws MalformedJVMClassException {
    }

    public int getErrorCount() {
        return errorCount;
    }

    public List<CompilerFeedback> getMessages() {
        return messages;
    }

    public void error( String msg ) {
        messages.add( CompilerFeedback.fatal(currentSourceFile, currentClass, currentMethod, currentLineNumber, currentColumnNumber, msg) );
        errorCount++;
    }

    public void warn( String msg ) {
        messages.add( CompilerFeedback.warn( currentSourceFile, currentClass, currentMethod, currentLineNumber, currentColumnNumber, msg ) );
        warningCount++;
    }

    public void visitSourceFile( String currentSourceFile ) {
        this.currentSourceFile = currentSourceFile;
    }

    public void visitClass( String currentClass ) {
        this.currentClass = currentClass;
    }

    public void visitMethod( String currentMethod ) {
        this.currentMethod = currentMethod;
    }

    public void visitLineNumber( int currentLineNumber ) {
        this.currentLineNumber = currentLineNumber;
    }

    public void visitColumnNumber( int currentColumnNumber ) {
        this.currentColumnNumber = currentColumnNumber;
    }

}
