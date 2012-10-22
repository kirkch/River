package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JVMExpression extends Lockable implements JVMStatement {

    private List<JVMOp> ops = new ArrayList<JVMOp>();


    /**
     * Pops the head value from the stack frame and returns it from the currently executing method.
     */
    public JVMStatement returnNoValue() {
        return append( JVMOps.RETURN );
    }

    /**
     * Loads and pushes the specified variable from the current frame onto the processing stack.
     */
    public JVMStatement load( JVMStackVariable var ) {
        return append( JVMOps.load(var) );
    }



    public JVMStatement returnValue( JVMStackVariable var ) {
        load(var);
        returnNoValue();

        return this;
    }

    public JVMStatement append( JVMOp op ) {
        throwIfLocked();

        ops.add( op );

        return this;
    }

    public void appendCodeToMethod( ClassGenerationContext context, MethodVisitor m, StackFrameContext stackframe ) {
        for ( JVMOp op : ops ) {
            op.appendCodeToMethod( context, m, stackframe );
        }
    }
}
