package com.pineframework.core.contract.repository;

import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.Optional;

/**
 * @param <I> <description>id</description>
 * @param <E> <description>entity</description>
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface CrudRepository<I extends Serializable, E extends FlatPersistence<I>> extends FlatRepository<I, E> {

    default void save(E... entity) {
        getImpl().save(getType(), entity);
    }

    default Optional<E> findById(I id) {
        return getImpl().findOne(getType(), id);
    }

    default void delete(I... id) {
        getImpl().delete(getType(), id);
    }

}
