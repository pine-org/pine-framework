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
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E> & TreeRepository<I, E>,
        B extends TreeTransient.Builder<I, M, B>,
        T extends ImmutableTreeTransformer<I, M, E, B>>
        extends AbstractFlatEntityService<I, M, E, R, B, T> implements TreeEntityService<I, M, E, R, B, T> {

    private PathGenerator<I, E> pathGenerator;

    public AbstractTreeEntityService(R repository, T transformer, PathGenerator<I, E> pathGenerator) {
        super(repository, transformer);
        this.pathGenerator = pathGenerator;
    }

    @Override
    public PathGenerator<I, E> getPathGenerator() {
        return pathGenerator;
    }
}
