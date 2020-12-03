package com.pineframework.core.test;

import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.junit.jupiter.api.BeforeEach;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <T> transformer
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public abstract class AbstractRepositoryTest<I extends Serializable, T extends FlatPersistence<I>,
        E extends CrudRepository & QueryRepository> extends AbstractTest<T> implements BasicBusinessOperation<I, T, E> {

    protected final E operator;

    public AbstractRepositoryTest(E repository, Map<String, T> storage) {
        super(storage);
        this.operator = repository;
    }

    @BeforeEach
    public void setUp() {
        assertNotNull(operator);
    }

    @Override
    public E getOperator() {
        return operator;
    }

    @Override
    public Optional<I> save(T data) {
        getOperator().save(data);
        return Optional.ofNullable(data.getId());
    }

    @Override
    public Optional<T> findById(I id) {
        return getOperator().findById(id);
    }

    @Override
    public void update(I id, T data) {
        getOperator().flush();
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);
    }

}
