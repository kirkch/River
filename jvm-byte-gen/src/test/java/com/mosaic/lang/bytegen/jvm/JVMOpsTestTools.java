package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.objectweb.asm.Opcodes.*;

/**
 *
 */
class JVMOpsTestTools {

    public static final String JAVA_CLASS_NAME = "com.mosaic.lang.bytegen.jvm.GeneratedClass";
    public static final String JVM_CLASS_NAME  = JAVA_CLASS_NAME.replaceAll( "\\.", "/" );

    public static MethodInstanceRef generateMethod( MethodGenerator methodGenerator ) {
        byte[]          bytes = generateClassBytes( methodGenerator );
        ByteClassLoader cl    = new ByteClassLoader();

        cl.declareJavaClass( "com.mosaic.lang.bytegen.jvm.GeneratedClass", bytes );

        try {
            Class  c = cl.loadClass( "com.mosaic.lang.bytegen.jvm.GeneratedClass" );
            Object o = c.newInstance();

            for ( Method m : c.getMethods() ) {
                if ( m.getName().equals("generatedMethod") ) {
                    return new MethodInstanceRef( o, m );
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    private static byte[] generateClassBytes( MethodGenerator methodGenerator ) {
        ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS );

        FieldVisitor fv = cw.visitField( ACC_PRIVATE, "booleanField", "Z", null, null );
        fv.visitEnd();



        MethodVisitor mv;

        cw.visit( V1_6, ACC_PUBLIC + ACC_SUPER, "com/mosaic/lang/bytegen/jvm/GeneratedClass", null, "java/lang/Object", null );

        cw.visitSource( "GeneratedClass.java", null );

        {
            mv = cw.visitMethod( ACC_PUBLIC, "<init>", "()V", null, null );
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel( l0 );
            mv.visitLineNumber( 6, l0 );
            mv.visitVarInsn( ALOAD, 0 );
            mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Object", "<init>", "()V" );
            mv.visitInsn( RETURN );
            Label l1 = new Label();
            mv.visitLabel( l1 );
            mv.visitLocalVariable( "this", "Lcom/mosaic/lang/bytegen/jvm/GeneratedClass;", null, l0, l1, 0 );
            mv.visitMaxs( 0, 0 ); // must be called even though COMPUTE_FRAMES is used; values don't matter
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod( ACC_PUBLIC, "generatedMethod", methodGenerator.desc, null, null );
            mv.visitCode();

            methodGenerator.init( mv );
            methodGenerator.appendMethod( mv );
            mv.visitMaxs( 0, 0 ); // must be called even though COMPUTE_FRAMES is used; values don't matter as it will compute the value

//            Label l0 = new Label();
//            mv.visitLabel( l0 );
//            mv.visitLineNumber( 9, l0 );
//            mv.visitLdcInsn( new Integer( 9999999 ) );
//            mv.visitInsn( IRETURN );
//            Label l1 = new Label();
//            mv.visitLabel( l1 );
//            mv.visitLocalVariable( "this", "Lcom/mosaic/lang/bytegen/jvm/ASMBox;", null, l0, l1, 0 );
//            mv.visitMaxs( 1, 1 );
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }


    public static abstract class MethodGenerator {
        public    final String desc;
        protected final JVMOps ops = new JVMOps6();

        public MethodGenerator( String desc ) {
            this.desc = desc;
        }

        public abstract void appendMethod( MethodVisitor m );

        void init( MethodVisitor m ) {
            ops.withStackFrame( new StackFrameContext(new ClassGenerationContext()) );
            ops.withMethodVisitor( m );
        }
    }

    private static class ByteClassLoader extends ClassLoader {
        private Map<String, byte[]> nodes = new ConcurrentHashMap();

        public void declareJavaClass( String name, byte[] bytes ) {
            nodes.put( name, bytes );
        }

        /**
         *
         * @throws MalformedJVMClassException
         */
        public Class findClass( String name ) throws ClassNotFoundException {
            byte[] b = nodes.get( name );

            if ( b == null ) {
                throw new ClassNotFoundException( name );
            }

            return defineClass( name, b, 0, b.length );
        }
    }

    public static class MethodInstanceRef {
        private final Object o;
        private final Method m;

        public MethodInstanceRef( Object o, Method m ) {
            this.o = o;
            this.m = m;
        }

        public Object invoke( Object...args ) {
            try {
                return m.invoke( o, args );
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
