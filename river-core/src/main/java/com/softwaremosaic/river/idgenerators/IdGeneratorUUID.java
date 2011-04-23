package com.softwaremosaic.river.idgenerators;

import com.softwaremosaic.river.IdGenerator;

import java.util.UUID;

/**
 *
 */
public class IdGeneratorUUID implements IdGenerator<String> {
    @Override
    public String generateIdFor( Object entity ) {
        return UUID.randomUUID().toString();
    }
}
