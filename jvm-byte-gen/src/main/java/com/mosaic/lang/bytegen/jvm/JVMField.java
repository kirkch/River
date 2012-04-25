package com.mosaic.lang.bytegen.jvm;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class JVMField {
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

    public JVMType getType() { return type; }
    public void setType( JVMType type ) { this.type = type; }

    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }

    public Object getInitialValue() { return initialValue; }
    public void setInitialValue( Object initialValue ) { this.initialValue = initialValue; }

    public JVMScope getScope() { return scope; }
    public void setScope( JVMScope scope ) { this.scope = scope; }

    public boolean isStatic() { return isStatic; }
    public void setStatic( boolean aStatic ) { isStatic = aStatic; }

    public boolean isFinal() { return isFinal; }
    public void setFinal( boolean aFinal ) { isFinal = aFinal; }

    void appendFieldToClass( ClassWriter cw ) {
        int fieldModifiers = scope.getASMCode();

        if ( isFinal() ) {
            fieldModifiers |= Opcodes.ACC_FINAL;
        }

        if ( isStatic() ) {
            fieldModifiers |= Opcodes.ACC_STATIC;
        }

        FieldVisitor f = cw.visitField( fieldModifiers, getName(), getType().getJVMDescription(), null, initialValue);
        f.visitEnd();
    }

    void initFieldWithinConstructor( JVMClass owningClass, MethodVisitor mv ) {
        if ( isStatic || initialValue == null ) {
            return;
        }

        mv.visitVarInsn( Opcodes.ALOAD, 0 );
        mv.visitLdcInsn( initialValue );
        mv.visitFieldInsn( Opcodes.PUTFIELD, owningClass.getFullyQualifiedJVMName(), getName(), getType().getJVMDescription() );
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