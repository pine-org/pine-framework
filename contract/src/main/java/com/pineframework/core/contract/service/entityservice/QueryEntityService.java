package com.pineframework.core.contract.service.entityservice;

import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.transformer.FlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.model.paging.Page;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

import static com.pineframework.core.helper.CollectionUtils.ofNullable;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */

public interface QueryEntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends QueryRepository<I, E>,
        T extends FlatTransformer<I, M, E>>
        extends EntityService<I, M, E, R, T> {

    default M[] findAll() {
        return ofNullable(getTransformer().transform(getRepository().findAll()));
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
}
