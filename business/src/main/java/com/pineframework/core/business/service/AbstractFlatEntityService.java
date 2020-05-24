package com.pineframework.core.business.service;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.contract.transformer.FlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.GenericUtils;
import org.slf4j.Logger;

import java.io.Serializable;

public abstract class AbstractFlatEntityService<I extends Serializable,
        M extends FlatTransient<I>,
        E extends FlatPersistence<I>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>,
        T extends FlatTransformer<I, M, E>>
        implements CrudEntityService<I, M, E, R, T>,
        QueryEntityService<I, M, E, R, T>,
        BatchEntityService<I, M, E, R, T> {

    protected final Logger logger = defaultLogger();

    private final R repository;

    private final T transformer;

    private final Class<M> modelType;

    private final Class<E> entityType;

    public AbstractFlatEntityService(R repository, T transformer) {
        this.repository = repository;
        this.transformer = transformer;
        modelType = (Class<M>) GenericUtils.extract(this.getClass(), 0);
        entityType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    @Override
    public Class<M> getTransientType() {
        return modelType;
    }

    @Override
    public Class<E> getPersistenceType() {
        return entityType;
    }

    @Override
    public R getRepository() {
        return repository;
    }

    @Override
    public T getTransformer() {
        return transformer;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
