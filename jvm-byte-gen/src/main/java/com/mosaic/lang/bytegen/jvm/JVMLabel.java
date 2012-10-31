package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.Label;

/**
 * Represents a placeholder within a methods operation stack. Used as a reference point for jump instructions.
 */
public class JVMLabel {
    private boolean visited;

    final Label l0 = new Label();

    public void errorIfAlreadyVisited() {
        if ( visited ) {
            throw new IllegalStateException("label already visited");
        }
    }

    public Label visit() {
        visited = true;

        return l0;
    }
}
