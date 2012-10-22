package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class JVMMethodParameter extends Lockable implements JVMStackVariable {
    private JVMType type;
    private String  name;

    private boolean isFinal;


    public JVMMethodParameter( JVMType type, String name ) {
        this.type = type;
        this.name = name;
    }

    String getJVMDescription() {
        return type.getJVMDescription();
    }

    public JVMType  getType()  { return type; }
    public String   getName()  { return name; }
    public boolean  isFinal()  { return isFinal; }

    public JVMMethodParameter withType( JVMType type ) {
        throwIfLocked();

        this.type = type;

        return this;
    }

    public JVMMethodParameter withName( String name ) {
        throwIfLocked();

        this.name = name;

        return this;
    }

    public JVMMethodParameter withFinal( boolean aFinal ) {
        throwIfLocked();

        isFinal = aFinal;

        return this;
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
