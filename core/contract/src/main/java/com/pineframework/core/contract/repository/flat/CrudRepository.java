package com.pineframework.core.contract.repository.flat;

import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Optional;

/**
 * Data access layer to support CRUD operation on flat data structure.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudRepository<I extends Serializable, E extends FlatPersistence<I>>
        extends FlatRepository<I, E> {

    /**
     * Save one or more persistable object.
     *
     * @param entities persistable pbject
     */
    @SuppressWarnings("unchecked")
    default void save(E... entities) {
        getImpl().save(getType(), entities);
    }

    /**
     * Find a persistable object with its identity
     *
     * @param id identity
     * @return a persistable object
     */
    default Optional<E> findById(I id) {
        return getImpl().findOne(getType(), id);
    }

    /**
     * Delete one or more persistable object with its/their identity(ies).
     *
     * @param identities identities
     */
    @SuppressWarnings("unchecked")
    default void delete(I... identities) {
        getImpl().delete(getType(), identities);
    }

}
