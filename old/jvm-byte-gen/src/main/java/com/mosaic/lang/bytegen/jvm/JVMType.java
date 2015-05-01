package com.mosaic.lang.bytegen.jvm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class JVMType {
    public static JVMType VOID    = new JVMType("V");
    public static JVMType BOOLEAN = new JVMType("Z");
    public static JVMType CHAR    = new JVMType("C");
    public static JVMType BYTE    = new JVMType("B");
    public static JVMType SHORT   = new JVMType("S");
    public static JVMType INT     = new JVMType("I");
    public static JVMType FLOAT   = new JVMType("F");
    public static JVMType LONG    = new JVMType("J");
    public static JVMType DOUBLE  = new JVMType("D");

    public static JVMType array( JVMType elementType ) { return new JVMType("["+elementType.desc); }
    public static JVMType object( Class c )            { return object(c.getName()); }
    public static JVMType object( String type )        { return new JVMType("L"+type.replaceAll("\\.", "/")+";"); }

    public static JVMType STRING = JVMType.object("java/lang/String");

    private final String desc;

    private JVMType( String desc ) {
        this.desc = desc;
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

    public String getJVMDescription() {
        return desc;
    }
}
