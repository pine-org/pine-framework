package com.pineframework.core.business.service;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.contract.service.entityservice.TreeEntityService;
import com.pineframework.core.contract.transformer.TreeTransformer;
import com.pineframework.core.datamodel.model.TreeTransient;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

public abstract class AbstractTreeEntityService<I extends Serializable,
        M extends TreeTransient<I, M>,
        E extends TreePersistence<I, E>,
        R extends CrudRepository<I, E> & QueryRepository<I, E> & BatchRepository<I, E> & TreeRepository<I, E>,
        T extends TreeTransformer<I, M, E>>
        extends AbstractFlatEntityService<I, M, E, R, T> implements TreeEntityService<I, M, E, R, T> {

    public AbstractTreeEntityService(R repository, T transformer) {
        super(repository, transformer);
    }

}
