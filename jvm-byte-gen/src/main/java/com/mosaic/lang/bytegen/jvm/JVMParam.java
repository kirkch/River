package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class JVMParam extends Lockable {
    private JVMType type;
    private String  name;

    private JVMScope scope = JVMScope.PUBLIC;

    private boolean isFinal;


    public JVMParam( JVMType type, String name ) {
        this.type = type;
        this.name = name;
    }

    public JVMType getType() { return type; }

    public void setType( JVMType type ) {
        throwIfLocked();
        this.type = type;
    }

    public String getName() { return name; }

    public void setName( String name ) {
        throwIfLocked();
        this.name = name;
    }

    public JVMScope getScope() { return scope; }

    public void setScope( JVMScope scope ) {
        throwIfLocked();
        this.scope = scope;
    }

    public boolean isFinal() { return isFinal; }

    public void setFinal( boolean aFinal ) {
        throwIfLocked();
        isFinal = aFinal;
    }

    void appendFieldToClass( ClassWriter cw ) {
        throwIfLocked();

        int fieldModifiers = scope.getASMCode();

        if ( isFinal() ) {
            fieldModifiers |= Opcodes.ACC_FINAL;
        }





//        FieldVisitor f = cw.visitField( fieldModifiers, getName(), getType().getJVMDescription(), null, initialValue);
//        f.visitEnd();
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
