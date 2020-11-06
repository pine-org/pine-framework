package com.pineframework.core.contract.service;

import java.util.Optional;

/**
 * Business logic layer to support CRUD operation on flat data structure.
 *
 * @param <I> identity
 * @param <M> transient object
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudService<I, M> {

    /**
     * Save operation.
     *
     * @param m transient object
     * @return optional of identity
     */
    Optional<I> save(M m);

    /**
     * Find persisted data by identity.
     *
     * @param id identity
     * @return optional of transient object
     */
    Optional<M> findById(I id);

    /**
     * Update operation.
     *
     * @param m transient object
     */
    void update(M m);

    /**
     * Delete one by identity.
     *
     * @param id identity
     */
    void delete(I id);
}
