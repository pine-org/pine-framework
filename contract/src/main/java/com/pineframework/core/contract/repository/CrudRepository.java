package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Optional;

/**
 * @param <I> <description>id</description>
 * @param <E> <description>entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudRepository<I extends Serializable, E extends FlatPersistence<I>> extends Repository<I, E> {

    default Optional<E> save(E entity) {
        return getImpl().save(entity, getType());
    }

    default Optional<E> findById(I id) {
        return getImpl().findById(id, getType());
    }

    default void delete(I id) {
        getImpl().delete(id, getType());
    }

    default E getReference(I id) {
        return getImpl().getReference(id, getType());
    }

}
