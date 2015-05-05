package com.mosaic.river.compiler.model;

import com.mosaic.lang.Lockable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class RiverField extends Lockable {

    private String       fieldName;
    private RiverTypeRef fieldType;
    private boolean      isIdentityField;
//    private boolean isMutable;
//    private boolean isNullable;
    private RiverScope   scope;


    public static RiverField identityField( RiverTypeRef fieldType, String name ) {
        return new RiverField( RiverScope.PRIVATE, fieldType, name, true );
    }

    public RiverField( RiverScope scope, RiverTypeRef fieldType, String fieldName, boolean isIdentityField ) {
        this.scope           = scope;
        this.fieldType       = fieldType;
        this.fieldName       = fieldName;

        this.isIdentityField = isIdentityField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public RiverTypeRef getFieldType() {
        return fieldType;
    }

    public boolean isIdentityField() {
        return isIdentityField;
    }

    public RiverScope getScope() {
        return scope;
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
