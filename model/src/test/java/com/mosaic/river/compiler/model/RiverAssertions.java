package com.mosaic.river.compiler.model;

import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.river.compiler.model.prettyprint.RiverCodeFormatter;

import java.util.List;

import static junit.framework.Assert.assertEquals;


public class RiverAssertions {

    public static void assertRiverClassEquals( RiverClass c, List<String> expectedRiverCode, List<String> expectedJavaCode ) {
        assertEquals( expectedRiverCode, RiverCodeFormatter.INSTANCE.toText(c) );
        assertEquals( expectedJavaCode,  JavaCodeFormatter.INSTANCE.toText(c) );
    }

}
