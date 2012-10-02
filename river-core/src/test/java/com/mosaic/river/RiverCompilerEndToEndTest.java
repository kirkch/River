package com.mosaic.river;

import com.mosaic.lang.bytegen.jvm.DynamicLoader;
import com.mosaic.lang.bytegen.jvm.JVMClass;
import com.mosaic.river.compiler.River2JVMFactory;
import com.mosaic.river.model.RiverClass;
import com.mosaic.river.model.RiverClassFactory;
import com.mosaic.river.parser.RiverParser;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RiverCompilerEndToEndTest {

    private RiverParser       parser              = new RiverParser();
    private RiverClassFactory riverClassFactory   = new RiverClassFactory();
    private River2JVMFactory  river2JVMTranslator = new River2JVMFactory();
    private DynamicLoader     jvmClassLoader      = new DynamicLoader();

    @Test
    public void classWithNoFieldsOrMethodsOrAnything() throws IOException, ClassNotFoundException {
        parser.parse( new StringReader("User"), riverClassFactory );

        RiverClass riverClass = riverClassFactory.build();
        JVMClass   jvmClass   = river2JVMTranslator.convert( riverClass );

        jvmClassLoader.declareJavaClass( jvmClass );

        Class userClass = jvmClassLoader.loadClass( "User" );

        assertEquals( "User", userClass.getSimpleName() );
        assertEquals( "User", userClass.getName() );
        assertEquals( 0, userClass.getDeclaredFields().length );
    }

}
