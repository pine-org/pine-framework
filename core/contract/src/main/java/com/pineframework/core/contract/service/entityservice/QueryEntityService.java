package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.paging.Page;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.CollectionUtils;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.Math.ceil;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryEntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>,
        R extends QueryRepository<I, E>>
        extends EntityService<I, E, M, B, T, R>, QueryService<I, M> {

    @Override
    default M[] findAll() {
        return CollectionUtils.ofNullable(getTransformer().transform(getRepository().findAll()));
    }

    @Override
    default Page findByPage(Page page) {
        Long count = getRepository().count(page.getFilters());
        page.setCount(count);

        if (count == 0)
            return page;

        int totalPage = (int) ceil((count.doubleValue() / page.getSize()));
        page.setIndices(IntStream.rangeClosed(0, totalPage - 1).toArray());

        int offset = (page.getIndex()) * page.getSize();
        page.setOffset(offset);

        page.setContent(getTransformer().transform(getRepository().find(page)));
        return page;
    }

    @Override
    default long count(Filter... filters) {
        return getRepository().count(filters);
    }

    @Override
    default M[] findByFilter(Filter... filters) {
        return getTransformer().transform((E[]) getRepository().find(filters));
    }

    @Override
    default Optional<M> findByModel(M m) {
        E e = getRepository().findOne(getTransformer().transform(m).toFilter()).get();
        return Optional.ofNullable(getTransformer().transform(e));
    }
}
