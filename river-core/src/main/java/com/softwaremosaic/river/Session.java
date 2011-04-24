package com.softwaremosaic.river;

/**
 *
 */
public interface Session {
    public <K,T> Repository<K,T> fetchRepository( Class<K> primaryKeyType, Class<T> documentType );

    public void startTransaction();
    public void commitTransaction();
    public void rollbackTransaction();

    public boolean isTransactionActive();
}
