package com.pineframework.core.test;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.junit.jupiter.api.BeforeEach;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public I save(T data) {
        Optional<I> id = getOperator().save(data);
        assertTrue(id.isPresent());

        logInfo(format("save model[%s] successful", getDataKey(data)));
        return id.get();
    }

    @Override
    public T findById(I id) {
        Optional<T> model = getOperator().findById(id);
        assertTrue(model.isPresent());
        logInfo(format("find model[%s] by id[%s] successful", getDataKey(id), id));
        return model.get();
    }

    @Override
    public T update(I id, T data) {
        getOperator().update(id, data);
        logInfo(format("update model[%s] successful", getDataKey((I) data.getId())));
        return (T) getOperator().findById(data.getId()).get();
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);

        Optional model = getOperator().findById(id);
        assertFalse(model.isPresent());
        logInfo(format("delete model[%s] by id[%s] successful", getDataKey(id), id));
    }

    public String getDataKey(I id) {
        return storage.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getId(), id))
                .findFirst().get().getKey();
    }

    public String getDataKey(T data) {
        return storage.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), data))
                .findFirst().get().getKey();
    }
}
