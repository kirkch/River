package com.mosaic.river.compiler.model.prettyprint;

import com.mosaic.io.IndentingWriter;
import com.mosaic.io.PrettyPrinter;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.RiverExpression;
import com.mosaic.river.compiler.model.RiverExpressionVisitor;
import com.mosaic.river.compiler.model.RiverField;

import java.io.IOException;
import java.util.List;


public class RiverCodeFormatter implements PrettyPrinter<RiverClass> {

    public static final PrettyPrinter<RiverClass> INSTANCE = new RiverCodeFormatter();

    private RiverCodeFormatter() {}


    public void write( Appendable buf, RiverClass rc ) throws IOException {
        IndentingWriter out = IndentingWriter.toIndentingWriter( buf );

        printClass( out, rc );
    }

    private void printClass( IndentingWriter out, RiverClass rc ) throws IOException {
        out.append( rc.getName() );
        out.append( "()" );

        if ( rc.hasFields() ) {
            out.append( " {" );
            out.newLine();

            out.indent( () -> printFields(out, rc) );

            out.append( "}" );
        }
    }

    private void printFields( IndentingWriter out, RiverClass rc ) throws IOException {
        List<RiverField> fields = rc.getFields();

        for( RiverField f : fields ) {
            out.append( f.getName() );
            out.append( " : int32 = " );
            printExpression( out, f.getInitialValue() );
            out.newLine();
        }
    }

    private void printExpression( IndentingWriter out, RiverExpression initialValue ) {
        initialValue.visitWith( new RiverExpressionVisitor() {
            public void int32Constant( int v )  {
                out.print( Integer.toString(v) );
            }
        });
    }

}
