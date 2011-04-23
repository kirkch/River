package com.softwaremosaic.river;

import java.io.Closeable;

/**
 *
 */
public interface RepositoryManager extends Closeable {
    public Repository newSession();


}
