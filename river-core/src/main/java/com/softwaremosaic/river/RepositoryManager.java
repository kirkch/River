package com.softwaremosaic.river;

import com.mosaic.lang.Listenable;

import java.io.Closeable;

/**
 * Factory class for Repository. Instances of Repository may or may not be thread safe, and they may or may not
 * be reusable. Whether they are or are not depends upon the implementation of the Repository and the RepositoryManager
 * being used. As such it is recommended to assume that they are not thread safe, and are not reusable and so always
 * ask the RepositoryManager for a new instance whenever one is needed. Do not store them between transactions/user
 * requests.
 */
public interface RepositoryManager extends Closeable, Listenable<RepositoryManagerListener> {
    public Session newSession();
}
