package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.HierarchyRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.datamodel.persistence.TreePersistence;
import com.pineframework.core.helper.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class JpaTreeRepository<I extends Serializable, E extends TreePersistence<I, E>>
        implements CrudRepository<I, E>, QueryRepository<I, E>, BatchRepository<I, E>, HierarchyRepository<I, E> {

    private final Class<E> entityType;

    @Autowired
    private TreeRepository repository;

    public JpaTreeRepository() {
        entityType = (Class<E>) GenericUtils.extract(this.getClass(), 1);
    }

    @Override
    public TreeRepository getImpl() {
        return repository;
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
