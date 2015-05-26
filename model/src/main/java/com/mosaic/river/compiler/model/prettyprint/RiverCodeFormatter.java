package com.mosaic.river.compiler.model.prettyprint;

import com.mosaic.io.IndentingWriter;
import com.mosaic.io.PrettyPrinter;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.compiler.model.exp.ExpressionVisitor;
import com.mosaic.river.compiler.model.RiverField;

import java.io.IOException;
import java.util.List;


public class RiverCodeFormatter implements PrettyPrinter<RiverClass> {

    public static final RiverCodeFormatter INSTANCE = new RiverCodeFormatter();

    private RiverCodeFormatter() {}


    public void write( Appendable buf, RiverClass rc ) throws IOException {
        IndentingWriter out = IndentingWriter.toIndentingWriter( buf );

        printClass( out, rc );
    }



    public void printClass( IndentingWriter out, RiverClass rc ) throws IOException {
        out.append( rc.getName() );
        out.append( "()" );

        if ( rc.hasFields() ) {
            out.append( " {" );
            out.newLine();

            out.indent( () -> printFields(out, rc) );

            out.append( "}" );
        }
    }

    public void printFields( IndentingWriter out, RiverClass rc ) throws IOException {
        List<RiverField> fields = rc.getFields();

        for( RiverField f : fields ) {
            out.append( f.getName() );
            out.append( " : int32 = " );
            printExpression( out, f.getInitialValue() );
            out.newLine();
        }
    }

    public void printExpression( IndentingWriter out, Expression initialValue ) {
        initialValue.visitWith( new ExpressionVisitor() {
            public void int32Constant( int v )  {
                out.print( Integer.toString(v) );
            }

            public void add() {
                out.print( " + " );
            }

            public void subtract() {
                out.print( " - " );
            }

            public void multiply() {
                out.print( " * " );
            }

            public void divide() {
                out.print( " / " );
            }
        });
    }

}
