package com.mosaic.lang.bytegen;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class CompilerFeedback {
    public static CompilerFeedback warn( String sourceFile, String className, String methodName, int lineNumber, int columnNumber, String message ) {
        return new CompilerFeedback( sourceFile, className, methodName, lineNumber, columnNumber, Severity.WARN, message );
    }

    public static CompilerFeedback fatal( String sourceFile, String className, String methodName, int lineNumber, int columnNumber, String message ) {
        return new CompilerFeedback( sourceFile, className, methodName, lineNumber, columnNumber, Severity.FATAL, message );
    }

    public final String   sourceFile;
    public final String   className;
    public final String   methodName;
    public final int      lineNumber;
    public final int      columnNumber;

    public final Severity severity;
    public final String   message;


    public CompilerFeedback( String sourceFile, String className, String methodName, int lineNumber, int columnNumber, Severity severity, String message ) {
        this.sourceFile   = sourceFile;
        this.className    = className;
        this.methodName   = methodName;
        this.lineNumber   = lineNumber;
        this.columnNumber = columnNumber;
        this.severity     = severity;
        this.message      = message;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode( this );
    }

    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }

    public boolean equals( Object o ) {
        return EqualsBuilder.reflectionEquals( this, o );
    }
}
