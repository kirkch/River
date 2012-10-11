package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class JVMField extends Lockable {
    private JVMType type;
    private String  name;
    private Object  initialValue;

    private JVMScope scope = JVMScope.PUBLIC;

    private boolean isStatic;
    private boolean isFinal;


    public JVMField( JVMType type, String name ) {
        this.type = type;
        this.name = name;
    }

    public JVMType  getType()         { return type; }
    public String   getFieldName()    { return name; }
    public Object   getInitialValue() { return initialValue; }
    public JVMScope getScope()        { return scope; }
    public boolean  isStatic()        { return isStatic; }


    public void setType( JVMType type ) {
        throwIfLocked();
        this.type = type;
    }

    public boolean isFinal() { return isFinal; }

    public JVMField withFieldName( String name ) {
        throwIfLocked();
        this.name = name;
        return this;
    }

    public JVMField withInitialValue( Object initialValue ) {
        throwIfLocked();
        this.initialValue = initialValue;
        return this;
    }

    public JVMField withScope( JVMScope scope ) {
        throwIfLocked();
        this.scope = scope;
        return this;
    }

    public JVMField withIsStatic( boolean aStatic ) {
        throwIfLocked();
        isStatic = aStatic;
        return this;
    }

    public JVMField withIsFinal( boolean aFinal ) {
        throwIfLocked();
        isFinal = aFinal;
        return this;
    }

    void appendFieldToClass( ClassWriter cw ) {
        throwIfUnlocked();

        int fieldModifiers = scope.getASMCode();

        if ( isFinal() ) {
            fieldModifiers |= Opcodes.ACC_FINAL;
        }

        if ( isStatic() ) {
            fieldModifiers |= Opcodes.ACC_STATIC;
        }

        FieldVisitor f = cw.visitField( fieldModifiers, getFieldName(), getType().getJVMDescription(), null, initialValue);
        f.visitEnd();
    }

    void initFieldWithinConstructor( JVMClass owningClass, MethodVisitor mv ) {
        throwIfUnlocked();

        if ( isStatic || initialValue == null ) {
            return;
        }

        mv.visitVarInsn( Opcodes.ALOAD, 0 );
        mv.visitLdcInsn( initialValue );
        mv.visitFieldInsn( Opcodes.PUTFIELD, owningClass.getFullyQualifiedJVMName(), getFieldName(), getType().getJVMDescription() );
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
