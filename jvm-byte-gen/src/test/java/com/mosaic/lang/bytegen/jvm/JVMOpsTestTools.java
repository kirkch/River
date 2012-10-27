package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.ClassWriter;
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

        cw.visitField( ACC_PRIVATE, "booleanField", "Z", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "byteArrayField", "[B", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "charArrayField", "[C", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "shortArrayField", "[S", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "intArrayField", "[I", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "longArrayField", "[J", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "floatArrayField", "[F", null, null ).visitEnd();
        cw.visitField( ACC_PRIVATE, "doubleArrayField", "[D", null, null ).visitEnd();




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

            initByteArrayField( mv );
            initCharArrayField( mv );
            initShortArrayField( mv );
            initIntArrayField( mv );
            initLongArrayField( mv );
            initFloatArrayField( mv );
            initDoubleArrayField( mv );

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

    private static void initByteArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_BYTE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitIntInsn( BIPUSH, 10 );
        mv.visitInsn( BASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitIntInsn( BIPUSH, 42 );
        mv.visitInsn( BASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "byteArrayField", "[B" );
    }

    private static void initCharArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_CHAR );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitIntInsn( BIPUSH, 10 );
        mv.visitInsn( CASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitIntInsn( BIPUSH, 42 );
        mv.visitInsn( CASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "charArrayField", "[C" );
    }

    private static void initShortArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_SHORT );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitIntInsn( BIPUSH, 10 );
        mv.visitInsn( SASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitIntInsn( BIPUSH, 42 );
        mv.visitInsn( SASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "shortArrayField", "[S" );
    }

    private static void initIntArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_INT );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitIntInsn( BIPUSH, 10 );
        mv.visitInsn( IASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitIntInsn( BIPUSH, 42 );
        mv.visitInsn( IASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "intArrayField", "[I" );
    }

    private static void initLongArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_LONG );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitLdcInsn( 42L );
        mv.visitInsn( LASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitLdcInsn( 42L );
        mv.visitInsn( LASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "longArrayField", "[J" );
    }

    private static void initFloatArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_FLOAT );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitLdcInsn( 42.0f );
        mv.visitInsn( FASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitLdcInsn( 42.0f );
        mv.visitInsn( FASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "floatArrayField", "[F" );
    }

    private static void initDoubleArrayField( MethodVisitor mv ) {
        mv.visitVarInsn( ALOAD, 0 );
        mv.visitInsn( ICONST_2 );
        mv.visitIntInsn( NEWARRAY, T_DOUBLE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_0 );
        mv.visitLdcInsn( 42.0 );
        mv.visitInsn( DASTORE );
        mv.visitInsn( DUP );
        mv.visitInsn( ICONST_1 );
        mv.visitLdcInsn( 42.0 );
        mv.visitInsn( DASTORE );
        mv.visitFieldInsn( PUTFIELD, "com/mosaic/lang/bytegen/jvm/GeneratedClass", "doubleArrayField", "[D" );
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
