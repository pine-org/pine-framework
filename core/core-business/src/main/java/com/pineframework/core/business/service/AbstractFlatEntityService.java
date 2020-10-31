package com.pineframework.core.business.service;

import com.pineframework.core.contract.repository.flat.BatchRepository;
import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.contract.transformer.ImmutableFlatTransformer;
import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.slf4j.Logger;

import java.io.Serializable;

import static com.pineframework.core.helper.GenericUtils.extract;

public abstract class AbstractFlatEntityService<I extends Serializable,
        E extends FlatPersistence<I>,
        M extends FlatTransient<I>,
        B extends FlatTransient.Builder<I, M, B>,
        T extends ImmutableFlatTransformer<I, M, E, B>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E>>
        implements CrudEntityService<I, E, M, B, T, R>, QueryEntityService<I, E, M, B, T, R>,
        BatchEntityService<I, E, M, B, T, R> {

    protected final Logger logger = defaultLogger();

    private final R repository;

    private final T transformer;

    private final Class<M> modelType;

    private final Class<E> entityType;

    public AbstractFlatEntityService(R repository, T transformer) {
        this.repository = repository;
        this.transformer = transformer;
        modelType = (Class<M>) extract(this.getClass(), 0);
        entityType = (Class<E>) extract(this.getClass(), 1);
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
