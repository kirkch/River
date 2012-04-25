package com.mosaic.lang.bytegen;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 */
public class ParseError {
    public final int    lineNumber;
    public final int    columnNumber;
    public final String message;


    public ParseError( int lineNumber, int columnNumber, String message ) {
        this.lineNumber   = lineNumber;
        this.columnNumber = columnNumber;
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
