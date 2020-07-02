package com.pineframework.core.contract.repository;

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
 * data access layer for flat structure
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryRepository<I extends Serializable, E extends FlatPersistence<I>> extends FlatRepository<I, E> {

    default E[] findAll() {
        return getImpl().find(new SelectAll<E>(getType()));
    }

    default E[] find(Filter<E>... filters) {
        return getImpl().find(new SelectByFilter<E>(getType(), filters));
    }

    default E[] find(int offset, int limit) {
        return getImpl().paging(getType(), new Page(offset, limit));
    }

    default E[] find(Pageable page) {
        return getImpl().paging(getType(), page);
    }

    default E[] find(I... identities) {
        return getImpl().find(new SelectByFilter<E>(getType(), (root, query, cb) -> root.get("id").in(identities)));
    }

    default <M> M[] find(SelectByDifferentReturnType<E, M> select) {
        return getImpl().find(select);
    }

    default Optional<E> findOne(Filter<E>... filters) {
        return getImpl().findOne(new SelectByFilter<E>(getType(), filters));
    }

    default <M extends FlatTransient> Optional<M> findOne(SelectByDifferentReturnType<E, M> select) {
        return getImpl().findOne(select);
    }

    default Long count(Filter<E>... filters) {
        return getImpl().findOne(new SelectCount<E>(getType(), filters)).get();
    }

    default Boolean contains(Filter<E>... filters) {
        return count(filters) > 0;
    }

}
