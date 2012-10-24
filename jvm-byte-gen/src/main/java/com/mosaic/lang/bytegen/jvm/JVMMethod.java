package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JVMMethod extends Lockable {

    private JVMScope       scope;
    private JVMType        returnType;
    private String         methodName;

    private JVMCodeBlock   codeBlock;

    private boolean        isStatic;
    private boolean        isFinal;
    private boolean        isAbstract;

    private List<JVMMethodParameter> params = new ArrayList();
// todo throws clause


    public JVMMethod( String methodName ) {
        this( JVMScope.PUBLIC, JVMType.VOID, methodName );
    }

    public JVMMethod( JVMScope scope, JVMType returnType, String methodName ) {
        this.scope      = scope;
        this.returnType = returnType;
        this.methodName = methodName;
    }

    public JVMType getReturnType() { return returnType; }
    public String  getMethodName() { return methodName; }
    public boolean isStatic()      { return isStatic;   }
    public boolean isFinal()       { return isFinal;    }
    public boolean isAbstract()    { return isAbstract; }

    public JVMMethod withReturnType( JVMType returnType ) {
        throwIfLocked();

        this.returnType = returnType;

        return this;
    }

    public JVMMethod withMethodName( String methodName ) {
        throwIfLocked();

        this.methodName = methodName;

        return this;
    }

    public JVMMethod withParameter( JVMType parameterType, String parameterName ) {
        return withParameter( new JVMMethodParameter(parameterType,parameterName) );
    }

    public JVMMethod withParameter( JVMMethodParameter ref ) {
        throwIfLocked();

        params.add( ref );

        return this;
    }

    public JVMMethod withCode( JVMStatement...statements ) {
        throwIfLocked();

        this.codeBlock = new JVMCodeBlock( statements );

        return this;
    }


    public JVMMethod withIsStatic( boolean isStatic ) {
        throwIfLocked();

        this.isStatic = isStatic;

        return this;
    }

    public JVMMethod withIsFinal( boolean isFinal ) {
        throwIfLocked();

        this.isFinal = isFinal;

        return this;
    }

    public JVMMethod withIsAbstract( boolean isAbstract ) {
        throwIfLocked();

        this.isAbstract = isAbstract;

        return this;
    }

    private String getJVMDescription() {
        StringBuilder buf = new StringBuilder(10);

        buf.append( '(' );

        for ( JVMMethodParameter p : params ) {
            buf.append( p.getJVMDescription() );
        }

        buf.append( ')' );
        buf.append( returnType.getJVMDescription() );

        return buf.toString();
    }

    @Override
    protected void onLock() {
        super.onLock();

        lockNbl( codeBlock );
    }

    void appendMethodToClass( ClassGenerationContext context, ClassWriter cw ) {
        int methodModifiers = scope.getASMCode();

        if ( isStatic() ) {
            methodModifiers |= Opcodes.ACC_STATIC;
        }

        if ( isFinal() ) {
            methodModifiers |= Opcodes.ACC_FINAL;
        }

        if ( isAbstract() ) {
            methodModifiers |= Opcodes.ACC_ABSTRACT;
        }


        Type.getMethodType( returnType.getJVMDescription());

        MethodVisitor m = cw.visitMethod( methodModifiers, getMethodName(), getJVMDescription(), null, null);

        if ( !isAbstract() ) {
            m.visitCode();
//            m.visitInsn( Opcodes.RETURN );
            if ( codeBlock == null ) {
//                context.error( sourceFileName, methodDeclarationPosition, "None abstract method '%s' has no implementation" );
            } else {
                codeBlock.appendCodeToMethod( m, new StackFrameContext(context) );
            }

            m.visitEnd();
        }

        m.visitEnd();
//
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
