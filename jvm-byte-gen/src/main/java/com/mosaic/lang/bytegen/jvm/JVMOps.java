package com.mosaic.lang.bytegen.jvm;

import org.objectweb.asm.MethodVisitor;



// dup_x1
// dup_x2
// dup2
// dup_x1
// dup_x2
// pop
// pop2
// swap
// nop



// dcmp
// fcmp
// goto/goto_w
// jsr/jsr_w
// lcmp
// lookupswitch
// ret
// tableswitch

// dconst
// fconst


// monitorenter
// monitorexit



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

    /**
     * Create a new multi dimensional array.
     *
     * @param jvmType eg [[Ljava/lang/String;
     *
     * @stack 1stDimensionLength,2ndDimensionLength,.. -> newArrayRef
     */
    public abstract void newMultiArray( String jvmType, int dimensionCount );

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
     * @param methodSignature   eg ()I
     *
     * @stack each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeStatic( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodSignature   eg ()I
     *
     * @stack obj,each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeMethod( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
     * @param methodSignature   eg ()I
     *
     * @stack obj,each param in order as defined by the method Signature -> return value
     */
    public abstract void invokeSpecial( String ownerDesc, String methodName, String methodSignature );

    /**
     *
     * @param ownerDesc eg java/lang/String
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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterBoolean( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterBoolean( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterByte( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterByte( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterChar( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterChar( int index );

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
    public abstract void loadRegisterShort( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterShort( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterInt( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterInt( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterLong( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterLong( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterFloat( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterFloat( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterDouble( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterDouble( int index );

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
     * Write the contents of register i onto the stack.
     *
     * @stack -> val
     */
    public abstract void loadRegisterObject( int index );

    /**
     * Write the contents of the stack into the register.
     *
     * @stack val ->
     */
    public abstract void storeRegisterObject( int index );

    /**
     * Push the value of 'this' onto the stack. Otherwise known as register zero.
     *
     * @stack -> val
     */
    public void pushThis() {
        loadRegisterObject( 0 );
    }

// CASTING

    /**
     * Errors if the object on the head of the stack cannot be cast to the specified type.
     *
     * @param jvmClass eg java/lang/String
     * @stack objectref -> objectref  (unchanged)
     */
    public abstract void checkcast( String jvmClass );

    /**
     * Checks whether the value at the top of the stack is castable to the specified class. Pushing the result as a
     * boolean onto the stack.
     *
     * @param jvmClass eg java/lang/String
     * @stack objectref -> boolean
     */
    public abstract void isInstanceOf( String jvmClass );

    /**
     * Narrows double down to a float.
     *
     * @stack v -> v
     */
    public abstract void d2f();

    /**
     * Narrows double down to an int.
     *
     * @stack v -> v
     */
    public abstract void d2i();

    /**
     * Narrows double down to an long.
     *
     * @stack v -> v
     */
    public abstract void d2l();

    /**
     * Upcasts float to a double.
     *
     * @stack v -> v
     */
    public abstract void f2d();

    /**
     * Converts float to an integer.
     *
     * @stack v -> v
     */
    public abstract void f2i();

    /**
     * Converts float to a long.
     *
     * @stack v -> v
     */
    public abstract void f2l();

    /**
     * Converts int to a byte.
     *
     * @stack v -> v
     */
    public abstract void i2b();

    /**
     * Converts int to a char.
     *
     * @stack v -> v
     */
    public abstract void i2c();

    /**
     * Converts int to a double.
     *
     * @stack v -> v
     */
    public abstract void i2d();

    /**
     * Converts int to a float.
     *
     * @stack v -> v
     */
    public abstract void i2f();

    /**
     * Converts int to a long.
     *
     * @stack v -> v
     */
    public abstract void i2l();

    /**
     * Converts int to a short.
     *
     * @stack v -> v
     */
    public abstract void i2s();

    /**
     * Converts long to a double.
     *
     * @stack v -> v
     */
    public abstract void l2d();

    /**
     * Converts long to a float.
     *
     * @stack v -> v
     */
    public abstract void l2f();

    /**
     * Converts long to a int.
     *
     * @stack v -> v
     */
    public abstract void l2i();

// EQUATION OPS

    public abstract void addInt();
    public abstract void addLong();
    public abstract void addFloat();
    public abstract void addDouble();

    public abstract void subtractInt();
    public abstract void subtractLong();
    public abstract void subtractFloat();
    public abstract void subtractDouble();

    public abstract void multiplyInt();
    public abstract void multiplyLong();
    public abstract void multiplyFloat();
    public abstract void multiplyDouble();

    public abstract void divideInt();
    public abstract void divideLong();
    public abstract void divideFloat();
    public abstract void divideDouble();

    public abstract void negateInt();
    public abstract void negateLong();
    public abstract void negateFloat();
    public abstract void negateDouble();

    public abstract void remainderInt();
    public abstract void remainderLong();
    public abstract void remainderFloat();
    public abstract void remainderDouble();

    /**
     * Increment the integer in register n by delta.
     *
     * @stack no change
     */
    public abstract void incInt( int registerIndex, byte delta );

    public abstract void bitAndInt();
    public abstract void bitAndLong();

    public abstract void bitOrInt();
    public abstract void bitOrLong();

    public abstract void bitXorInt();
    public abstract void bitXorLong();


    /**
     * Shifts value left n bits. Where only the low 5 bits (0x1F) are used.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftLeftInt();

    /**
     * Shifts value left n bits. Where only the low 6 bits (0x3F) are used.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftLeftLong();


    /**
     * Shifts value right n bits. Where only the low 5 bits (0x1F) are used. If value was negative then the left hand side
     * will be filled with 1's.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftRightInt();

    /**
     * Shifts value right n bits. Where only the low 6 bits (0x3F) are used. If value was negative then the left hand side
     * will be filled with 1's.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftRightLong();


    /**
     * Shifts value right n bits. Where only the low 5 bits (0x1F) are used. Always fills the left hand side with zeros.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftRightUnsignedInt();

    /**
     * Shifts value right n bits. Where only the low 6 bits (0x3F) are used. Always fills the left hand side with zeros.
     *
     * @stack value,n -> newValue
     */
    public abstract void bitShiftRightUnsignedLong();

}
