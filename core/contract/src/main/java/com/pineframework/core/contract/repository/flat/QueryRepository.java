package com.pineframework.core.contract.repository.flat;

import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.paging.Pageable;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.datamodel.select.SelectAll;
import com.pineframework.core.datamodel.select.SelectByDifferentReturnType;
import com.pineframework.core.datamodel.select.SelectByFilter;
import com.pineframework.core.datamodel.select.SelectCount;

import java.io.Serializable;
import java.util.Optional;

/**
 * Data access layer to support Searching operation on flat data structure.
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

@SuppressWarnings(value = {"unchecked", "Convert2Diamond", "unused"})
public interface QueryRepository<I extends Serializable, E extends FlatPersistence<I>>
        extends FlatRepository<I, E> {

    /**
     * @return persistable object array
     */
    default E[] findAll() {
        return getImpl().find(new SelectAll<E>(getType()));
    }

    /**
     * Get persistable objects from {@code offset} index with {@code limit} length.
     *
     * @param offset begin index
     * @param limit  array length
     * @return persistable object array
     */
    default E[] find(int offset, int limit) {
        return getImpl().paging(getType(), new Page(offset, limit));
    }

    /**
     * Find persistable objects with specific condition(s).
     *
     * @param filters where clause(s)
     * @return persistable object array
     */
    default E[] find(Filter<E>... filters) {
        return getImpl().find(new SelectByFilter<E>(getType(), filters));
    }

    /**
     * Find a page include persistable object array.
     *
     * @param page page
     * @return persistable object array
     */
    default E[] find(Pageable page) {
        return getImpl().paging(getType(), page);
    }

    /**
     * Find persistable object(s) with identity(ies).
     *
     * @param identities identities
     * @return persistable object array
     */
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    default E[] find(I... identities) {
        return getImpl().find(new SelectByFilter<E>(getType(), (root, query, cb) -> root.get("id").in(identities)));
    }

    /**
     * Find persistable objects then convert to a transient type {@code M}.
     *
     * @param select query clause
     * @param <M>    transient type
     * @return persistable object array
     */
    default <M> M[] find(SelectByDifferentReturnType<E, M> select) {
        return getImpl().find(select);
    }

    /**
     * Find only one persistable object with specific condition(s).
     *
     * @param filters where clause(s)
     * @return persistable object
     */
    default Optional<E> findOne(Filter<E>... filters) {
        return getImpl().findOne(new SelectByFilter<E>(getType(), filters));
    }

    /**
     * Find only one persistable object then convert to a transient type {@code M}.
     *
     * @param select query clause
     * @param <M>    transient type
     * @return persistable object array
     */
    default <M extends FlatTransient<I>> Optional<M> findOne(SelectByDifferentReturnType<E, M> select) {
        return getImpl().findOne(select);
    }

    /**
     * Count persistable object(s) with specific condition(s).
     *
     * @param filters where clause(s)
     * @return long number
     */
    default Long count(Filter<E>... filters) {
        return getImpl()
                .findOne(new SelectCount<E>(getType(), filters))
                .orElse(-1L);
    }

    /**
     * Check if there is a persistable object the return {@code true} otherwise {@code false}.
     *
     * @param filters where clause(s)
     * @return boolean value {@code true} or {@code false}
     */
    default Boolean contains(Filter<E>... filters) {
        return count(filters) > 0;
    }

}
