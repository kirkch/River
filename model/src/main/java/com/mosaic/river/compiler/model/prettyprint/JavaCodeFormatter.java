package com.mosaic.river.compiler.model.prettyprint;

import com.mosaic.io.IndentingWriter;
import com.mosaic.io.PrettyPrinter;
import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.RiverField;

import java.io.IOException;
import java.util.List;


public class JavaCodeFormatter implements PrettyPrinter<RiverClass> {

    public static final PrettyPrinter<RiverClass> INSTANCE = new JavaCodeFormatter();

    private JavaCodeFormatter() {}


    public void write( Appendable buf, RiverClass rc ) throws IOException {
        IndentingWriter out = IndentingWriter.toIndentingWriter( buf );

        printClass( out, rc );
    }

    private void printClass( IndentingWriter out, RiverClass rc ) throws IOException {
        out.append( "public class " );
        out.append( rc.getName() );
        out.append( " {" );

        if ( rc.hasFields() ) {
            out.indent( () -> printFields(out, rc) );
            out.newLine();
        }

        out.append( "}" );
    }

    private void printFields( IndentingWriter out, RiverClass rc ) throws IOException {
        List<RiverField> fields = rc.getFields();

        for( RiverField f : fields ) {
            out.newLine();
            out.append( "public int " );
            out.append( f.getName() );
            out.append( " = 1;" );
        }
    }

}
