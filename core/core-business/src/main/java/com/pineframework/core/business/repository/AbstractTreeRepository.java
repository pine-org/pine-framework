package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.datamodel.persistence.TreePersistence;
import com.pineframework.core.helper.GenericUtils;

import java.io.Serializable;

public abstract class AbstractTreeRepository<I extends Serializable, E extends TreePersistence<I, E>>
        extends AbstractFlatRepository<I, E> implements TreeRepository<I, E> {

    private final Class<E> entityType;

    public AbstractTreeRepository(Repository repository) {
        super(repository);
        entityType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
