package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.contract.service.BatchService;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;

/**
 * It provides all CRUD, paging and batch operation in one class.
 *
 * @param <I> ID
 * @param <M> model
 * @param <S> business service
 */
public abstract class AbstractRestApi<I extends Serializable,
        M extends FlatTransient<I>,
        S extends CrudService<I, M> & QueryService<I, M> & BatchService<I, M>>
        implements CrudRestfulApi<I, M, S>, PagingRestfulApi<I, M, S>, BatchRestfulApi<I, M, S> {

    protected S service;

    protected AbstractRestApi(S service) {
        this.service = service;
    }

    @Override
    public S getService() {
        return service;
    }
}
