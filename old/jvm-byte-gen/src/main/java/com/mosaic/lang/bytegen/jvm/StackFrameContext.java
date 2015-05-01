package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.bytegen.CompilerFeedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the runtime generation of a stack frame. Stack frames can be nested. Handles the dynamic allocation
 * of variables to indexes.
 */
public class StackFrameContext {

    private final ClassGenerationContext genContext;
    private final StackFrameContext      parentFrameContextNbl;

    private final Map<String,Integer> stackFrameAssignments = new HashMap();
    private final AtomicInteger       nextFreeIndexCounter  = new AtomicInteger(0);


    public StackFrameContext( ClassGenerationContext genContext ) {
        this.genContext            = genContext;
        this.parentFrameContextNbl = null;
    }

    public StackFrameContext( StackFrameContext parentFrameContext ) {
        this.genContext            = parentFrameContext.genContext;
        this.parentFrameContextNbl = parentFrameContext;
    }

    public StackFrameContext declareVariable( JVMStackVariable var ) {
        String varName = var.getName();
        if ( stackFrameAssignments.containsKey(varName) ) {
            genContext.error( "Duplicate declaration of variable '"+varName+"'" );

            return this;
        }

        int index = nextFreeIndexCounter.getAndIncrement();
        stackFrameAssignments.put( varName, index );

        return this;
    }

    public StackFrameContext createChildFrame() {
        return new StackFrameContext( this );
    }

    /**
     *
     * @return -1 if unrecognised
     */
    public int getIndexFor( JVMStackVariable var ) {
        Integer index = stackFrameAssignments.get(var.getName());

        if ( index == null ) {
            if ( parentFrameContextNbl != null ) {
                return parentFrameContextNbl.getIndexFor( var );
            }

            genContext.error( "Unknown variable '"+var.getName()+"'" );

            return -1;
        }

        return index.intValue();
    }


    public int getErrorCount() {
        return genContext.getErrorCount();
    }

    public List<CompilerFeedback> getMessages() {
        return genContext.getMessages();
    }

    public void visitSourceFile( String currentSourceFile ) {
        genContext.visitSourceFile( currentSourceFile );
    }

    public void visitClass( String currentClass ) {
        genContext.visitClass( currentClass );
    }

    public void visitMethod( String currentMethod ) {
        genContext.visitMethod( currentMethod );
    }

    public void visitLineNumber( int currentLineNumber ) {
        genContext.visitLineNumber( currentLineNumber );
    }

    public void visitColumnNumber( int currentColumnNumber ) {
        genContext.visitColumnNumber( currentColumnNumber );
    }
}
