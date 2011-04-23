package com.softwaremosaic.river.idgenerators;

import com.softwaremosaic.river.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class IdGeneratorSequence implements IdGenerator<String> {
    private AtomicLong nextSequence;

    public IdGeneratorSequence() {
        this(1);
    }

    public IdGeneratorSequence( long initialValue ) {
        nextSequence = new AtomicLong(initialValue);
    }

    @Override
    public String generateIdFor( Object entity ) {
        return Long.toString( nextSequence.getAndIncrement() );
    }
}
