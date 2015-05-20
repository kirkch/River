package com.mosaic.river.parser;

import com.mosaic.river.compiler.model.RiverClass;
import org.junit.Test;


public class ParseRiverInt32DeclarationTests extends ParseTestUtils {

    @Test
    public void minimalClassDeclaration() {
        RiverClass rc = parseAndExpectSuccess(
            "Constants() {",
            "  a : int32 = 0",
            "}"
        );

        assertRiverClassAsJava(
            rc,
            "public class Constants {",
            "  public int a = 0;",
            "}"
        );
    }

}
