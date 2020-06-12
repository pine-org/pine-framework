package com.pineframework.core.rest.api;

import com.pineframework.core.contract.service.BatchService;
import com.pineframework.core.contract.service.CrudService;
import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.datamodel.model.FlatTransient;

import java.io.Serializable;

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
