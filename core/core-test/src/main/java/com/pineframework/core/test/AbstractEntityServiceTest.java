package com.pineframework.core.test;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.datamodel.model.FlatTransient;
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
public abstract class AbstractEntityServiceTest<I extends Serializable, T extends FlatTransient,
        E extends CrudEntityService & QueryService> extends AbstractTest<T> implements BasicBusinessOperation<I, T, E> {

    protected final E operator;

    public AbstractEntityServiceTest(E service, Map<String, T> storage) {
        super(storage);
        this.operator = service;
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
        return getOperator().save(data);
    }

    @Override
    public Optional<T> findById(I id) {
        return getOperator().findById(id);
    }

    @Override
    public void update(I id, T data) {
        getOperator().update(id, data);
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);
    }

}
