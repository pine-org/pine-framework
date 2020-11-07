package com.pineframework.core.test;

import com.pineframework.core.contract.repository.flat.CrudRepository;
import com.pineframework.core.contract.repository.flat.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.junit.jupiter.api.BeforeEach;

import javax.ws.rs.NotSupportedException;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractRepositoryTest<I extends Serializable, T extends FlatPersistence,
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
    public I save(T data) {
        getOperator().save(data);
        assertNotNull(data.getId());
        logInfo(format("save entity[%s] successful", getDataKey((I) data.getId())));
        return (I) data.getId();
    }

    @Override
    public T findById(I id) {
        Optional<T> entity = getOperator().findById(id);
        assertTrue(entity.isPresent());
        logInfo(format("find entity[%s] by id[%s] successful", getDataKey(id), id));
        return entity.get();
    }

    @Override
    public T update(I id, T data) {
        throw new NotSupportedException();
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);
        Optional entity = getOperator().findById(id);
        assertFalse(entity.isPresent());
        logInfo(format("delete entity[%s] by id[%s] successful", getDataKey(id), id));
    }

    public String getDataKey(I id) {
        return storage.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getId(), id))
                .findFirst().get().getKey();
    }
}
