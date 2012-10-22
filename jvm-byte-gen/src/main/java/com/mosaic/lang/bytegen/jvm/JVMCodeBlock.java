package com.mosaic.lang.bytegen.jvm;

import com.mosaic.lang.Lockable;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class JVMCodeBlock extends Lockable implements JVMStatement {

    private List<JVMStatement> statements;

    public JVMCodeBlock() {
        this( new ArrayList() );
    }

    public JVMCodeBlock( JVMStatement...statements ) {
        this( Arrays.asList(statements) );
    }

    public JVMCodeBlock( List<JVMStatement> statements ) {
        this.statements = statements;
    }


    public void appendCodeToMethod( ClassGenerationContext context, MethodVisitor m, StackFrameContext parentStackframe ) {
        StackFrameContext stackframe = parentStackframe.createChildFrame();

        for ( JVMStatement s : statements ) {
            s.appendCodeToMethod( context, m, stackframe );
        }
    }

}
