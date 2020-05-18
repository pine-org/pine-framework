package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.FlatRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.helper.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class JpaFlatRepository<I extends Serializable, E extends FlatPersistence<I>>
        implements CrudRepository<I, E>, QueryRepository<I, E>, BatchRepository<I, E> {

    private final Class<E> entityType;

    @Autowired
    private FlatRepository repository;

    public JpaFlatRepository() {
        entityType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    @Override
    public FlatRepository getImpl() {
        return repository;
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
