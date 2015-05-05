package com.mosaic.river.compiler;

import com.mosaic.lang.bytegen.jvm.JVMClass;
import com.mosaic.river.compiler.model.RiverClass;

/**
 *
 */
public class River2JVMFactory {

    public JVMClass convert( RiverClass riverClass ) {
        JVMClass jc = new JVMClass()
            .withClassName( riverClass.getClassName() );

        return jc;
    }

}
