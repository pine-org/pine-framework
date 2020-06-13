package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.JpaRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.GenericUtils;

import java.io.Serializable;

public abstract class AbstractFlatRepository<I extends Serializable, E extends FlatPersistence<I>>
        implements CrudRepository<I, E>, QueryRepository<I, E>, BatchRepository<I, E> {

    private final Class<E> entityType;

    private final JpaRepository repository;

    public AbstractFlatRepository(JpaRepository repository) {
        this.repository = repository;
        entityType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    @Override
    public JpaRepository getImpl() {
        return repository;
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
