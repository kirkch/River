package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class JVMClass extends Lockable {

    private String classPackage;
    private String className;

    private String sourceFile;

    private Map<String,JVMField>  fields  = new ConcurrentHashMap();
    private Map<String,JVMMethod> methods = new ConcurrentHashMap();


    public JVMClass() {}

    public JVMClass( String pkg, String cName ) {
        this.classPackage = pkg;
        this.className    = cName;
    }

    public String getClassName() {
        return className;
    }

    public JVMClass withClassName( String className ) {
        throwIfLocked();

        this.className = className;

        return this;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public JVMClass withClassPackage( String classPackage ) {
        throwIfLocked();

        this.classPackage = classPackage;

        return this;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public JVMClass withSourceFile( String sourceFile ) {
        throwIfLocked();
        this.sourceFile = sourceFile;

        return this;
    }

    public int getStaticMethodCount() {
        return 0;
    }

    public int getMethodCount() {
        return 0;
    }


    public String getFullyQualifiedJVMName() {
        if ( classPackage == null ) {
            return className;
        }

        return classPackage + "/" + className;
    }

    public JVMClass withField( JVMField field ) {
        throwIfLocked();
        field.lock();

        fields.put( field.getFieldName(), field );

        return this;
    }

    public JVMClass withMethod( JVMMethod method ) {
        throwIfLocked();
        method.lock();

        methods.put( method.getMethodName(), method );

        return this;
    }

    public byte[] generateClass() {
        ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS );

        cw.visit( Opcodes.V1_2, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, getFullyQualifiedJVMName(), null, "java/lang/Object", null );

        if ( sourceFile != null ) {
            cw.visitSource( sourceFile, null );
        }

        cw.visitEnd();

        for ( JVMField field : fields.values() ) {
            field.appendFieldToClass( cw );
        }

        appendDefaultNoArgsConstructor( cw );

        for ( JVMMethod method : methods.values() ) {
            method.appendMethodToClass( cw );
        }

        return cw.toByteArray();
    }

    @Override
    protected void onLock() {
        super.onLock();

        lockAll( fields.values() );
    }

    private void appendDefaultNoArgsConstructor( ClassWriter cw ) {
        MethodVisitor mv = cw.visitMethod( Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");

        for ( JVMField field : fields.values() ) {
            field.initFieldWithinConstructor( this, mv );
        }

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
}
