package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
abstract class JVMOps {

    protected StackFrameContext stackframe;
    protected MethodVisitor     m;

    public JVMOps withStackFrame( StackFrameContext stackframe ) {
        this.stackframe = stackframe;

        return this;
    }

    public JVMOps withMethodVisitor( MethodVisitor m ) {
        this.m = m;

        return this;
    }

// FIELD OPS
    /**
     *
     * @param ownerDesc class name that the field is declared on;  eg java/lang/Object
     * @param fieldType as per JVM types, eg J, Z, S etc
     * @stack objectref -> value
     */
    public abstract void getField( String ownerDesc, String fieldName, String fieldType );

    /**
     *
     * @param ownerDesc class name that the field is declared on;  eg java/lang/Object
     * @param fieldType as per JVM types, eg J, Z, S etc
     * @stack objectref,value ->
     */
    public abstract void putField( String ownerDesc, String fieldName, String fieldType );
    /**
     *
     * @param ownerDesc class name that the field is declared on;  eg java/lang/Object
     * @param fieldType as per JVM types, eg J, Z, S etc
     * @stack  -> value
     */
    public abstract void getStaticField( String ownerDesc, String fieldName, String fieldType );

    /**
     *
     * @param ownerDesc class name that the field is declared on;  eg java/lang/Object
     * @param fieldType as per JVM types, eg J, Z, S etc
     * @stack value ->
     */
    public abstract void putStaticField( String ownerDesc, String fieldName, String fieldType );

    /**
     * @stack arrayref -> int
     */
    public abstract void arrayLength();

// OBJECT OPS

    /**
     * Allocates memory on the heap and assigns default values to each field for a new instance of the specified class.
     * Does not call a constructor, that requires a separate INVOKESPECIAL &lt;init&gt; command.
     *
     * @param eg java/lang/RuntimeException
     * @stack -> objectref
     */
    public abstract void newObject( String jvmClassName );


// CONTROL FLOW OPS

    /**
     * Pops the objectref off of the top of the stack and throws it as an exception.
     *
     * @stack objectref ->
     */
    public abstract void throwException();

    /**
     * Declares for debugging purposes what the line number was from the original source code for the next ops that follow this one.
     * Convenience method for visitLabel(newLabel(),lineNumber).
     *
     * @stack ->
     */
    public abstract void lineNumber( int lineNumber);

    /**
     * Creates a new JVMLabel instance without visiting it yet. This allows foreward referencing of labels for jump commands.
     */
    public abstract JVMLabel newLabel();

    /**
     * Binds the specified label to this location within the op stack. Jump commands will now be able to use this location to
     * calculate their jump offsets.
     */
    public abstract void visitLabel( JVMLabel label );

    /**
     * Binds the specified label to this location within the op stack and declares the line number for the following commands.
     * Jump commands will now be able to use this location to calculate their jump offsets.
     */
    public abstract void visitLabel( JVMLabel label, int lineNumber );

    /**
     * If the int on the head of the stack is zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifEqZero( JVMLabel label );

    /**
     * If the int on the head of the stack is not zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifNEZero( JVMLabel label );

    /**
     * If the int on the head of the stack is less than zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifLTZero( JVMLabel label );

    /**
     * If the int on the head of the stack is less than or equal to zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifLTEZero( JVMLabel label );

    /**
     * If the int on the head of the stack is greater than zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifGTZero( JVMLabel label );

    /**
     * If the int on the head of the stack is greater than or equal to zero then jump to label.
     *
     * @stack value(int) ->
     */
    public abstract void ifGTEZero( JVMLabel label );

    /**
     * Jumps to label if the value at the top of the stack is null. The value will be popped from the stack.
     *
     * @stack objectref ->
     */
    public abstract void ifNull( JVMLabel label );

    /**
     * Jumps to label if the value at the top of the stack is not null. The value will be popped from the stack.
     *
     * @stack objectref ->
     */
    public abstract void ifNotNull( JVMLabel label );

    /**
     * Jumps to label if the two objectrefs at the top of the stack are identical. The values will be popped from the stack.
     *
     * @stack objectref,objectref ->
     */
    public abstract void ifEqualObjectRefs( JVMLabel label );

    /**
     * Jumps to label if the two objectrefs at the top of the stack are not identical. The values will be popped from the stack.
     *
     * @stack objectref,objectref ->
     */
    public abstract void ifNotEqualObjectRefs( JVMLabel label );

    /**
     * Jumps to label if the two int values at the top of the stack are equal. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsEqual( JVMLabel label );

    /**
     * Jumps to label if the two int values at the top of the stack are not equal. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsNotEqual( JVMLabel label );

    /**
     * Jumps to label if head-1 is less than head int value on the stack. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsLT( JVMLabel label );

    /**
     * Jumps to label if head-1 is less than or equal to head int value on the stack. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsLTE( JVMLabel label );

    /**
     * Jumps to label if head-1 is greater than head int value on the stack. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsGT( JVMLabel label );

    /**
     * Jumps to label if head-1 is greater than or equal to head int value on the stack. The values will be popped from the stack.
     *
     * @stack intvalue,intvalue ->
     */
    public abstract void ifIntsGTE( JVMLabel label );

// METHOD OPS

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodName
     * @param methodSignature   eg ()I
     *
     * @stack each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeStatic( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodName
     * @param methodSignature   eg ()I
     *
     * @stack obj,each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeMethod( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodName
     * @param methodSignature   eg ()I
     *
     * @stack obj,each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeSpecial( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodName
     * @param methodSignature   eg ()I
     *
     * @stack obj,each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeInterface( String ownerDesc, String methodName, String methodSignature );


// MISC STACK OPS

    /**
     * Duplicates the value at the head of the stack with a secondary copy.
     *
     * @stack v -> v,v
     */
    public abstract void dup();

// VOID OPS

    /**
     * Exits a method with no value being returned.
     *
     * @stack no change
     */
    public abstract void returnNoValue();


// BOOLEAN OPS
    /**
     * Pushes the supplied boolean value onto the stack.
     *
     * @stack  -> boolean
     */
    public abstract void pushBoolean( boolean v );

    /**
     * Exits current method with the boolean value at the head of the stack.
     *
     * @stack boolean ->
     */
    public abstract void returnBoolean();

    /**
     * Creates a new boolean array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayBoolean( int length );

    /**
     * Pops the current value on the stack and stores it into index i of the array
     *
     * @stack  array,index(int),boolean(int) ->
     */
    public abstract void setArrayBoolean();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> boolean
     */
    public abstract void getArrayBoolean();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterBoolean( int index );

// BYTE OPS
    /**
     * Pushes the supplied byte value onto the stack.
     *
     * @stack  -> byte
     */
    public abstract void pushByte( byte v );

    /**
     * Exits current method with the byte value at the head of the stack.
     *
     * @stack byte ->
     */
    public abstract void returnByte();

    /**
     * Creates a new byte array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayByte( int length );

    /**
     * Pops the current value on the stack and stores it into index i of the array
     *
     * @stack  array,index(int),byte(int) ->
     */
    public abstract void setArrayByte();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayByte();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterByte( int index );

// CHAR OPS
    /**
     * Pushes the supplied char value onto the stack.
     *
     * @stack  -> char
     */
    public abstract void pushChar( char v );

    /**
     * Exits current method with the char value at the head of the stack.
     *
     * @stack char ->
     */
    public abstract void returnChar();

    /**
     * Creates a new char array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayChar( int length );

    /**
     * Pops the current value on the stack and stores it into index i of the array
     *
     * @stack  array,index(int),char(int) ->
     */
    public abstract void setArrayChar();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayChar();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterChar( int index );

// SHORT OPS
    /**
     * Pushes the supplied short value onto the stack.
     *
     * @stack  -> short
     */
    public abstract void pushShort( short v );

    /**
     * Exits current method with the short value at the head of the stack.
     *
     * @stack short ->
     */
    public abstract void returnShort();

    /**
     * Creates a new short array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayShort( int length );

    /**
     * Pops the current value on the stack and stores it into index i of the array
     *
     * @stack  array,index(int),short(int) ->
     */
    public abstract void setArrayShort();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayShort();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterShort( int index );

// INT OPS
    /**
     * Pushes the supplied int value onto the stack.
     *
     * @stack  -> int
     */
    public abstract void pushInt( int v );

    /**
     * Exits current method with the int value at the head of the stack.
     *
     * @stack int ->
     */
    public abstract void returnInt();

    /**
     * Creates a new int array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayInt( int length );

    /**
     * Pops the current value on the stack and stores it into index i of the array
     *
     * @stack  array,index(int),int(int) ->
     */
    public abstract void setArrayInt();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayInt();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterInt( int index );

// LONG OPS
    /**
     * Pushes the supplied long value onto the stack.
     *
     * @stack  -> long
     */
    public abstract void pushLong( long v );

    /**
     * Exits current method with the long value at the head of the stack.
     *
     * @stack long ->
     */
    public abstract void returnLong();

    /**
     * Creates a new long array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayLong( int length );

    /**
     * Pops the current value on the stack and stores it longo index i of the array
     *
     * @stack  array,index(long),long(long) ->
     */
    public abstract void setArrayLong();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayLong();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterLong( int index );

// FLOAT OPS
    /**
     * Pushes the supplied float value onto the stack.
     *
     * @stack  -> float
     */
    public abstract void pushFloat( float v );

    /**
     * Exits current method with the float value at the head of the stack.
     *
     * @stack float ->
     */
    public abstract void returnFloat();

    /**
     * Creates a new float array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayFloat( int length );

    /**
     * Pops the current value on the stack and stores it floato index i of the array
     *
     * @stack  array,index(float),float(float) ->
     */
    public abstract void setArrayFloat();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayFloat();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterFloat( int index );

// DOUBLE OPS
    /**
     * Pushes the supplied double value onto the stack.
     *
     * @stack  -> double
     */
    public abstract void pushDouble( double v );

    /**
     * Exits current method with the double value at the head of the stack.
     *
     * @stack double ->
     */
    public abstract void returnDouble();

    /**
     * Creates a new double array of the specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayDouble( int length );

    /**
     * Pops the current value on the stack and stores it index i of the array
     *
     * @stack  array,index(int),value(double) ->
     */
    public abstract void setArrayDouble();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayDouble();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterDouble( int index );

// OBJECT OPS
    /**
     * Pushes null onto the stack.
     *
     * @stack -> null
     */
    public abstract void pushNull();
    /**
     * Pushes a string constant onto the stack. Stores the string within the classes constant pool.
     *
     * @stack -> str
     */
    public abstract void pushString( String v );

    /**
     * Exits current method with the object value at the head of the stack.
     *
     * @stack object ->
     */
    public abstract void returnObject();

    /**
     * Creates a new typed array of object refs of specified length. Leaves it on the stack.
     *
     * @stack  -> newarray
     */
    public abstract void newArrayObject( int length, String refDesc );

    /**
     * Pops the current value on the stack and stores it double index i of the array
     *
     * @stack  array,index(double),double(double) ->
     */
    public abstract void setArrayObject();

    /**
     * Reads a value out of an array and leaves it on the stack.
     *
     * @stack  array,index(int) -> value
     */
    public abstract void getArrayObject();

    /**
     * Push the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void pushRegisterObject( int index );

    /**
     * Push the value of 'this' onto the stack. Otherwise known as register zero.
     *
     * @stack -> val
     */
    public void pushThis() {
        pushRegisterObject( 0 );
    }

}
