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
     * Creates a new boolean area of the specified length. Leaves it on the stack.
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
     * Creates a new byte area of the specified length. Leaves it on the stack.
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
     * Creates a new char area of the specified length. Leaves it on the stack.
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
     * Creates a new short area of the specified length. Leaves it on the stack.
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



// INT OPS
    /**
     * Pushes the supplied int value onto the stack.
     *
     * @stack  -> int
     */
    public abstract void pushInt( int v );


// OBJECT OPS
    /**
     * Exits current method with the object value at the head of the stack.
     *
     * @stack object ->
     */
    public abstract void returnObject();

}
