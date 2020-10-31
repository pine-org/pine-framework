package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

/**
 * Data access layer to support Flat, table, two dimensional data structure.
 *
 * @param <I> identity of object
 * @param <E> persistable type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface FlatRepository<I extends Serializable, E extends FlatPersistence<I>> {

    /**
     * @return persistable type
     */
    Class<E> getType();

    /**
     * @return repository implementation
     */
    Repository getImpl();

    /**
     * Apply flush concept on database.
     */
    default void flush() {
        getImpl().flush();
    }

    /**
     * Clear ORM session.
     */
    default void clear() {
        getImpl().clear();
    }
}