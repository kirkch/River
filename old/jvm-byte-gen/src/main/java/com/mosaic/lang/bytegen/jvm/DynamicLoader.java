package com.mosaic.lang.bytegen.jvm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicLoader extends ClassLoader {
    private Map<String, JVMClass> nodes = new ConcurrentHashMap();

    public void declareJavaClass( JVMClass node ) {
        nodes.put( node.getFullyQualifiedJVMName().replaceAll( "/", "." ), node );
    }

    /**
     *
     * @throws MalformedJVMClassException
     */
    public Class findClass( String name ) throws ClassNotFoundException {
        JVMClass node = nodes.get( name );

        if ( node == null ) {
            throw new ClassNotFoundException( name );
        }

        ClassGenerationContext context = new ClassGenerationContext();

        byte[] b = node.generateClass( context );

        context.throwErrors();

        return defineClass( name, b, 0, b.length );
    }
}