package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.CollectionUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryEntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends QueryRepository<I, E>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>>
        extends EntityService<I, M, E, R, B, T> {

    default M[] findAll() {
        return CollectionUtils.ofNullable(getTransformer().transform(getRepository().findAll()));
    }

    default Page find(Page page) {
        Long count = getRepository().count(page.getFilters());
        if (count == 0)
            return page;

        M[] content = getTransformer().transform(getRepository().find(page));

        page.setTotalCount(count);
        page.setContent(content);

        return page;
    }

    default long count(Filter<E>... filters) {
        return getRepository().count(filters);
    }

    default Optional<M> findByExample(M m) {
        E e = getRepository().findOne(getTransformer().transform(m).toFilter()).get();
        return Optional.ofNullable(getTransformer().transform(e));
    }
}
