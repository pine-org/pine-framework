package com.pineframework.core.test;

import com.pineframework.core.contract.service.QueryService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.datamodel.exception.NotFoundDataException;
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

/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <T> transformer
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
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

        logInfo(format("save model[%s] successful", getTestDataName(data)));
        return id.get();
    }

    @Override
    public T findById(I id) {
        Optional<T> model = getOperator().findById(id);
        assertTrue(model.isPresent());
        logInfo(format("find model[%s] by id[%s] successful", getTestDataName(id), id));
        return model.get();
    }

    @Override
    public T update(I id, T data) {
        getOperator().update(id, data);
        logInfo(format("update model[%s] successful", getTestDataName((I) data.getId())));
        Optional object = getOperator().findById(data.getId());
        if (!object.isPresent())
            throw new NotFoundDataException();
        return (T) object.get();
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);

        long count = getOperator().count((root, query, cb) -> cb.equal(root.get("id"), id));
        assertFalse(count > 0);
        logInfo(format("delete model[%s] by id[%s] successful", getTestDataName(id), id));
    }

    public String getTestDataName(I id) {
        Optional<Map.Entry<String, T>> entry = storage.entrySet().stream()
                .filter(e -> Objects.equals(e.getValue().getId(), id))
                .findFirst();

        return entry.isPresent() ? entry.get().getKey() : "";
    }

    public String getTestDataName(T data) {
        Optional<Map.Entry<String, T>> entry = storage.entrySet().stream()
                .filter(e -> Objects.equals(e.getValue(), data))
                .findFirst();

        return entry.isPresent() ? entry.get().getKey() : "";
    }
}
