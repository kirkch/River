package com.mosaic.river.compiler.model;

import com.mosaic.io.IndentingWriter;
import com.mosaic.lang.functional.VoidFunction1;
import com.mosaic.lang.functional.VoidFunction2;
import com.mosaic.lang.system.SystemX;
import com.mosaic.river.compiler.model.exp.RiverExpression;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.river.compiler.model.prettyprint.RiverCodeFormatter;
import com.mosaic.utils.ListUtils;

import java.io.StringWriter;
import java.util.List;

import static com.mosaic.lang.functional.TryNow.tryNow;
import static junit.framework.Assert.assertEquals;


public class RiverAssertions {

    public static void assertRiverClassEquals( RiverClass c, List<String> expectedRiverCode, List<String> expectedJavaCode ) {
        assertEquals( ListUtils.toString(expectedRiverCode, SystemX.NEWLINE), RiverCodeFormatter.INSTANCE.toText(c) );
        assertEquals( ListUtils.toString(expectedJavaCode, SystemX.NEWLINE),  JavaCodeFormatter.INSTANCE.toText(c) );
    }

    public static void assertRiverExpressionEquals( RiverExpression exp, List<String> expectedRiverCode, List<String> expectedJavaCode ) {
        String actualRiver = format( out -> RiverCodeFormatter.INSTANCE.printExpression(out,exp) );
        String actualJava  = format( out -> JavaCodeFormatter.INSTANCE.printExpression(out,exp) );

        assertEquals( ListUtils.toString(expectedRiverCode, SystemX.NEWLINE), actualRiver );
        assertEquals( ListUtils.toString(expectedJavaCode, SystemX.NEWLINE),  actualJava );
    }



    public static String format( VoidFunction1<IndentingWriter> f ) {
        StringWriter    buf     = new StringWriter();
        IndentingWriter out     = new IndentingWriter( buf );

        return tryNow( () -> {
            f.invoke( out );

            return buf.toString();
        } ).getResultNoBlock();
    }

}
