package com.pineframework.core.business.repository;

import com.pineframework.core.contract.repository.Repository;
import com.pineframework.core.contract.repository.TreeRepository;
import com.pineframework.core.datamodel.persistence.TreePersistence;

import java.io.Serializable;

import static com.pineframework.core.helper.GenericUtils.extract;

/**
 * Include CRUD, search, batch and search on tree structure operations in one class.
 *
 * @param <I> identity
 * @param <E> persistable type
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public abstract class AbstractTreeRepository<I extends Serializable, E extends TreePersistence<I, E>>
        extends AbstractFlatRepository<I, E> implements TreeRepository<I, E> {

    private final Class<E> entityType;

    @SuppressWarnings("unchecked")
    public AbstractTreeRepository(Repository repository) {
        super(repository);
        entityType = (Class<E>) extract(this.getClass(), 1);
    }

    @Override
    public Class<E> getType() {
        return entityType;
    }
}
