package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.List;

/**
 *
 */
public class JVMMethod extends Lockable {

    private JVMScope       scope;
    private JVMType        returnType;
    private String         methodName;
    private List<JVMParam> params;
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



    void appendMethodToClass( ClassWriter cw ) {
        throwIfUnlocked();

        int methodModifiers = scope.getASMCode();
//
//        if ( isFinal() ) {
//            fieldModifiers |= Opcodes.ACC_FINAL;
//        }
//
//        if ( isStatic() ) {
//            fieldModifiers |= Opcodes.ACC_STATIC;
//        }
//

        Type.getMethodType( returnType.getJVMDescription());

        MethodVisitor m = cw.visitMethod( methodModifiers, getMethodName(), "()V", null, null);

        m.visitCode();
        m.visitInsn( Opcodes.RETURN );
        m.visitEnd();

        m.visitEnd();
//
//        FieldVisitor f = cw.visitField( fieldModifiers, getName(), getType().getJVMDescription(), null, initialValue);
//        f.visitEnd();
    }
}
