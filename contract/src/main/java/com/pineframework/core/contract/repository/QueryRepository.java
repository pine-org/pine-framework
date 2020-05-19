package com.pineframework.core.contract.repository;

import com.pineframework.core.contract.query.Column;
import com.pineframework.core.contract.query.Specification;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static com.pineframework.core.helper.CollectionUtils.createArray;

/**
 * data access layer for flat structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryRepository<I extends Serializable, E extends FlatPersistence<I>> extends Repository<I, E> {

    default List<E> findAll() {
        return getImpl().findAll(getType());
    }

    default List<E> find(int first, int max) {
        return getImpl().find(first, max, getType());
    }

    default List<E> find(Specification<E> condition) {
        return getImpl().find(condition, getType());
    }

    default List<E> find(Specification<E> condition, int first, int max) {
        return getImpl().find(condition, first, max, getType());
    }

    default <M> List<M> find(Specification<E> specification, Class<M> m, Column<E> column) {
        return getImpl().find(specification, getType(), m, column);
    }

    default Optional<E> findOne(Specification<E> condition) {
        return getImpl().findOne(condition, getType());
    }

    default <M> Optional<M> findOne(Specification<E> specification, Class<M> m, Column<E> column) {
        return getImpl().findOne(specification, getType(), m, column);
    }

    default Long count(Class<E> c) {
        return getImpl().count(c);
    }

    default Long count(Specification<E> condition) {
        return getImpl().count(condition, getType());
    }

    default Boolean contains(Specification<E> condition) {
        return getImpl().contains(condition, getType());
    }

    default Optional<E> findOneByField(String field, Object value) {
        return getImpl().findOneByField(field, value, getType());
    }

    default List<E> findByField(String field, Object value) {
        return getImpl().findByField(field, value, getType());
    }

    default List<E> findByIdentities(I... identities) {
        return getImpl().findByIdentities(identities, getType());
    }

    default List<E> findByIdentities(List<I> identities) {
        I[] a = identities.toArray(createArray(Serializable.class, identities.size()));
        return getImpl().findByIdentities(a, getType());
    }

    default <T extends Serializable> List<E> findIn(String field, T[] values) {
        return getImpl().findIn(field, values, getType());
    }
}