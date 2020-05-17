package com.pineframework.core.contract.service.entity;

import com.pineframework.core.contract.query.Specification;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.transformer.Transformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.model.paging.Paging;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;
import java.util.List;

import static com.pineframework.core.helper.CollectionUtils.ofNullable;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends QueryRepository<I, E>,
        T extends Transformer<I, M, E>>
        extends EntityService<I, M, E, R, T> {

    default List<M> findAll() {
        return ofNullable(getTransformer().transformEntitiesToModels(getRepository().findAll()));
    }

    default Paging<I, M> find(Paging<I, M> paging) {
        Specification<E> condition = null;
        Long count = getRepository().count(condition);
        paging.setCount(count);

        if (count == 0)
            return paging;

        List<E> entities = getRepository().find(condition, paging.getStart(), paging.getMax());
        List<M> content = getTransformer().transformEntitiesToModels(entities);

        paging.setContent(content);
        return paging;
    }
}
