package com.pineframework.core.business.service;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.contract.service.entityservice.TreeEntityService;
import com.pineframework.core.contract.transformer.ImmutableTreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.path.PathGenerator;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

public abstract class AbstractTreeEntityService<I extends Serializable,
        E extends TreePersistence<I, E>,
        M extends TreeTransient<I, M>,
        B extends TreeTransient.Builder<I, M, B>,
        T extends ImmutableTreeTransformer<I, M, E, B>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E> & TreeRepository<I, E>>
        extends AbstractFlatEntityService<I, E, M, B, T, R> implements TreeEntityService<I, E, M, B, T, R> {

    private final PathGenerator<I, E> pathGenerator;

    public AbstractTreeEntityService(R repository, T transformer, PathGenerator<I, E> pathGenerator) {
        super(repository, transformer);
        this.pathGenerator = pathGenerator;
    }

    @Override
    public PathGenerator<I, E> getPathGenerator() {
        return pathGenerator;
    }
}
