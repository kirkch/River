package com.mosaic.river.compiler.model;

public interface RiverClassVisitor {

    public void enterClass( String name );
    public void enterMethod( String name );
    public void enterStatement();

    public void constantInt32( ConstantInt32 v );
    public void opSum();
    public void opReturn();

    public void exitStatement();
    public void exitMethod();
    public void exitClass();

}
