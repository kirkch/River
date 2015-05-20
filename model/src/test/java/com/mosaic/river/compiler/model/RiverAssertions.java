package com.mosaic.river.compiler.model;

import com.mosaic.lang.system.SystemX;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import com.mosaic.river.compiler.model.prettyprint.RiverCodeFormatter;
import com.mosaic.utils.ListUtils;

import java.util.List;

import static junit.framework.Assert.assertEquals;


public class RiverAssertions {

    public static void assertRiverClassEquals( RiverClass c, List<String> expectedRiverCode, List<String> expectedJavaCode ) {
        assertEquals( ListUtils.toString(expectedRiverCode, SystemX.NEWLINE), RiverCodeFormatter.INSTANCE.toText(c) );
        assertEquals( ListUtils.toString(expectedJavaCode, SystemX.NEWLINE),  JavaCodeFormatter.INSTANCE.toText(c) );
    }

}
