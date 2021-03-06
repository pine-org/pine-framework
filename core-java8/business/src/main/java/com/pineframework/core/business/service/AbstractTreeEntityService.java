package com.pineframework.core.business.service;

import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.contract.repository.flat.BatchRepository;
import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.contract.service.entityservice.TreeEntityService;
import com.pineframework.core.contract.transformer.ImmutableTreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.path.PathGenerator;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <M> transient type
 * @param <B> transient object builder
 * @param <T> transformer
 * @param <R> data access object (DAO/Repository)
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractTreeEntityService<I extends Serializable,
        E extends TreePersistence<I, E>,
        M extends TreeTransient<I, M>,
        B extends TreeTransient.Builder<I, M, B>,
        T extends ImmutableTreeTransformer<I, E, M, B>,
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
