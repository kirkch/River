package com.mosaic.river.compiler.model.prettyprint;

import com.mosaic.io.IndentingWriter;
import com.mosaic.io.PrettyPrinter;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.exp.Expression;
import com.mosaic.river.compiler.model.exp.ExpressionVisitor;
import com.mosaic.river.compiler.model.RiverField;

import java.io.IOException;
import java.util.List;


public class JavaCodeFormatter implements PrettyPrinter<RiverClass> {

    public static final JavaCodeFormatter INSTANCE = new JavaCodeFormatter();

    private JavaCodeFormatter() {}


    public void write( Appendable buf, RiverClass rc ) throws IOException {
        IndentingWriter out = IndentingWriter.toIndentingWriter( buf );

        printClass( out, rc );
    }



    public void printClass( IndentingWriter out, RiverClass rc ) throws IOException {
        out.append( "public class " );
        out.append( rc.getName() );
        out.append( " {" );

        if ( rc.hasFields() ) {
            out.indent( () -> printFields(out, rc) );
            out.newLine();
        }

        out.append( "}" );
    }

    public void printFields( IndentingWriter out, RiverClass rc ) throws IOException {
        List<RiverField> fields = rc.getFields();

        for( RiverField f : fields ) {
            out.newLine();
            out.append( "public int " );
            out.append( f.getName() );
            out.append( " = " );

            printExpression( out, f.getInitialValue() );
            out.append( ';' );
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

            public void modulo() {
                out.print( " % " );
            }

            public void leftBitShift() {
                out.print( " << " );
            }

            public void rightBitShift() {
                out.print( " >> " );
            }

            public void zeroFillRightBitShift() {
                out.print( " >>> " );
            }

            public void greaterThan() {
                out.print( " > " );
            }

            public void gte() {
                out.print( " >= " );
            }

            public void lessThan() {
                out.print( " < " );
            }

            public void lte() {
                out.print( " <= " );
            }

            public void equalTo() {
                out.print( " == " );
            }

            public void notEqualTo() {
                out.print( " != " );
            }

            public void bitAnd() {
                out.print( " & " );
            }

            public void bitOr() {
                out.print( " | " );
            }
        });
    }
}
