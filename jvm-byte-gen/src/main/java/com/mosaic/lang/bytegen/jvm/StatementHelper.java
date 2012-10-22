package com.mosaic.lang.bytegen.jvm;

/**
 *
 */
public class StatementHelper {

    public static JVMStatement returnValue( JVMStackVariable var ) {
        return new JVMExpression().returnValue(var);
    }

    public static JVMStatement returnNoValue() {
        return new JVMExpression().returnNoValue();
    }

}
