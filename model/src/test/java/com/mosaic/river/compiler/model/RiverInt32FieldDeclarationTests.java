package com.mosaic.river.compiler.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.mosaic.river.compiler.model.RiverAssertions.assertRiverClassEquals;


public class RiverInt32FieldDeclarationTests {

    /**
     * To declare a field in River, write the name of the field and assign a value to it.  Notice
     * that the type of the field does not need to be included as it can be inferred from the value.
     *
     * <code>
     *   Constants() {
     *     a : int32 = 1
     *   }
     * </code>
     */
    @Test
    public void declareASingleField() {
        RiverClass c = new RiverClass("Constants");
        c.addField( new RiverInt32Field("a", 1) );


        List<String> expectedRiverCode = Arrays.asList(
            "Constants() {",
            "  a : int32 = 1",
            "}"
        );

        List<String> expectedJavaCode = Arrays.asList(
            "public class Constants {",
            "  public int a = 1;",
            "}"
        );


        assertRiverClassEquals( c, expectedRiverCode, expectedJavaCode );
    }

}
