package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class JVMClass {

    private String classPackage;
    private String className;

    private String sourceFile;

    private Map<String,JVMField> fields = new ConcurrentHashMap();


    public JVMClass() {}

    public JVMClass( String pkg, String cName ) {
        this.classPackage = pkg;
        this.className    = cName;
    }

    public String getClassName()                       { return className; }
    public void setClassName( String className )       { this.className = className; }

    public String getClassPackage()                    { return classPackage; }
    public void setClassPackage( String classPackage ) { this.classPackage = classPackage; }

    public String getSourceFile()                      { return sourceFile; }
    public void setSourceFile( String sourceFile )     { this.sourceFile = sourceFile; }

    public int getStaticMethodCount() { return 0; }
    public int getMethodCount() { return 0; }


    public String getFullyQualifiedJVMName() {
        return classPackage + "/" + className;
    }

    public void appendField( JVMField field ) {
        fields.put( field.getName(), field );
    }

    public byte[] generateClass() {
        ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS );

        cw.visit( Opcodes.V1_2, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, classPackage+"/"+className, null, "java/lang/Object", null );

        if ( sourceFile != null ) {
            cw.visitSource( "pm/H1.java", null );
        }

        cw.visitEnd();

        for ( JVMField field : fields.values() ) {
            field.appendFieldToClass( cw );
        }

        appendDefaultNoArgsConstructor( cw );


        return cw.toByteArray();
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
