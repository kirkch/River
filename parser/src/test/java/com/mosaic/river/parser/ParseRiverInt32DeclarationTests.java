package com.mosaic.river.parser;

import com.mosaic.river.compiler.model.RiverClass;
import com.mosaic.river.compiler.model.prettyprint.JavaCodeFormatter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ParseRiverInt32DeclarationTests extends ParseTestUtils {

    /**
     * Constants() {
     *     a : int32 = 0
     * }
     */
    @Test
    public void minimalClassDeclaration() {
        RiverClass rc = parseAndExpectSuccess(
            "Constants() {",
            "  a : int32 = 0",
            "}"
        );


        List<String> expected = Arrays.asList(
            "public class Constants {",
            "  public int a = 0;",
            "}"
        );

        assertEquals( expected, JavaCodeFormatter.INSTANCE.toText(rc) );
    }

}
