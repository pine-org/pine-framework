package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.repository.flat.BatchRepository;
import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;

import java.io.Serializable;

import static com.pineframework.core.helper.GenericUtils.extract;

/**
 * Include CRUD, search and batch operations in one class.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractFlatRepository<I extends Serializable, E extends FlatPersistence<I>>
        implements CrudRepository<I, E>, QueryRepository<I, E>, BatchRepository<I, E> {

    private final Class<E> entityType;

    private final Repository repository;

    @SuppressWarnings("unchecked")
    public AbstractFlatRepository(Repository repository) {
        this.repository = repository;
        entityType = (Class<E>) extract(this.getClass(), 1);
    }

    @Override
    public Repository getImpl() {
        return repository;
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
