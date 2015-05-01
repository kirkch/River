package com.mosaic.river.model;

import com.mosaic.lang.Lockable;
import com.mosaic.parsers.pull.TextPosition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RiverClass extends Lockable<RiverClass> {

    private TextPosition startOfClass;

    private String packageName;
    private String className;

//    private boolean isMutable;
//    private boolean isLockable;

// private public protected


    private Map<String,RiverField> identityFields = new HashMap();
//    private Map<String,RiverField> fields         = new HashMap<String,RiverField>();
//    private List<RiverConstructor> constructors   = new LinkedList<RiverConstructor>();


    public static RiverClass newObject( TextPosition p, String packageName, String className ) {
        return new RiverClass( p, packageName, className );
    }


    public RiverClass() {}

    private RiverClass( TextPosition p, String packageName, String className ) {
        this.startOfClass = p;
        this.packageName  = packageName;
        this.className    = className;
    }


    public TextPosition getStartOfClass() {
        return startOfClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public Map<String, RiverField> getIdentityFields() {
        return Collections.unmodifiableMap(identityFields);
    }

    public RiverClass withIdentityField( RiverTypeRef fieldType, String name ) {
        throwIfLocked();

        RiverField field = RiverField.identityField( fieldType, name );

        identityFields.put( name, field );

        return this;
    }

    public RiverClass withClassStartsAt( TextPosition startOfClass ) {
        throwIfLocked();
        this.startOfClass = startOfClass;

        return this;
    }

    public RiverClass withPackageName( String packageName ) {
        throwIfLocked();
        this.packageName = packageName;

        return this;
    }

    public RiverClass withClassName( String className ) {
        throwIfLocked();
        this.className = className;

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
