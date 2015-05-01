package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.bytegen.CompilerFeedback;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class StackFrameContextTest {

    private StackFrameContext context = new StackFrameContext( new ClassGenerationContext() );

    @Test
    public void fetchIndexThatDoesNotExistInRootContext_expectMinusOne() {
        int index = context.getIndexFor( new JVMMethodParameter(JVMType.BOOLEAN, "flag") );

        assertEquals( -1, index );
        assertEquals( 1, context.getErrorCount() );
    }

    @Test
    public void fetchIndexThatDoesNotExist_expectError() {
        int index = context.getIndexFor( new JVMMethodParameter(JVMType.BOOLEAN, "flag") );

        assertEquals( -1, index );
        assertEquals( 1, context.getErrorCount() );
    }

    @Test
    public void declareVariable_getIndexForIt_expectIndexZero() {
        JVMMethodParameter var = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );
        context.declareVariable( var );

        int index = context.getIndexFor( var );

        assertEquals( 0, index );
        assertEquals( 0, context.getErrorCount() );
    }

    @Test
    public void declareTwoVariables_getIndexForSecondOne_expectIndexOne() {
        JVMMethodParameter var1 = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );
        JVMMethodParameter var2 = new JVMMethodParameter( JVMType.STRING, "name" );

        context.declareVariable( var1 );
        context.declareVariable( var2 );

        int index = context.getIndexFor( var2 );

        assertEquals( 1, index );
        assertEquals( 0, context.getErrorCount() );
    }

    @Test
    public void declareTwoVariablesWithSameName_expectError() {
        JVMMethodParameter var1 = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );
        JVMMethodParameter var2 = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );

        context.declareVariable( var1 );

        context.visitSourceFile( "/sf/a" );
        context.visitClass( "A" );
        context.visitMethod( "foo" );
        context.visitLineNumber( 1 );
        context.visitColumnNumber( 10 );
        context.declareVariable( var2 );

        CompilerFeedback expected = CompilerFeedback.fatal( "/sf/a", "A", "foo", 1, 10, "Duplicate declaration of variable 'flag'" );

        assertEquals( 1, context.getErrorCount() );
        assertEquals( expected, context.getMessages().get( 0 ) );
    }

    @Test
    public void fetchIndexOfVariableAtParentContext_expectSuccess() {
        JVMMethodParameter var = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );

        context.declareVariable( var );

        StackFrameContext childContext = context.createChildFrame();

        int index = childContext.getIndexFor( var );

        assertEquals( 0, index );
        assertEquals( 0, childContext.getErrorCount() );
    }

    @Test
    public void declareTwoVariablesWithSameNameAtDifferentNestedLevels_expectError() {
        JVMMethodParameter var1 = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );
        JVMMethodParameter var2 = new JVMMethodParameter( JVMType.BOOLEAN, "flag" );

        context.declareVariable( var1 );

        context.visitSourceFile( "/sf/a" );
        context.visitClass( "A" );
        context.visitMethod( "foo" );
        context.visitLineNumber( 1 );
        context.visitColumnNumber( 10 );
        context.declareVariable( var2 );

        CompilerFeedback expected = CompilerFeedback.fatal( "/sf/a", "A", "foo", 1, 10, "Duplicate declaration of variable 'flag'" );

        assertEquals( 1, context.getErrorCount() );
        assertEquals( expected, context.getMessages().get(0) );
    }

}
