package com.mosaic.river.compiler;

import com.mosaic.lang.bytegen.jvm.JVMClass;
import com.mosaic.river.model.RiverClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class River2JVMFactoryTest {

    private River2JVMFactory compiler = new River2JVMFactory();

    @Test
    public void riverClassWithNoFieldsPackageMethodsOrAnything_toEmptyJavaClass() {
        RiverClass riverClass = new RiverClass().withClassName( "Account" );
        JVMClass   jvmClass   = compiler.convert( riverClass );

        assertEquals( "Account", jvmClass.getClassName() );
        assertNull( jvmClass.getClassPackage() );
        assertEquals( "Account", jvmClass.getFullyQualifiedJVMName() );
        assertNull( jvmClass.getSourceFile() );
        assertEquals( 0, jvmClass.getStaticMethodCount() );
        assertEquals( 0, jvmClass.getMethodCount() );
    }

}
