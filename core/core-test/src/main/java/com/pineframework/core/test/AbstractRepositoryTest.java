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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @param <I> identity
 * @param <E> persistable type
 * @param <T> transformer
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
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
        logInfo(format("save entity[%s] successful", getTestObjectName((I) data.getId())));
        return (I) data.getId();
    }

    @Override
    public T findById(I id) {
        Optional<T> entity = getOperator().findById(id);
        assertTrue(entity.isPresent());
        logInfo(format("find entity[%s] by id[%s] successful", getTestObjectName(id), id));
        return entity.get();
    }

    @Override
    public T update(I id, T data) {
        throw new NotSupportedException();
    }

    @Override
    public void deleteById(I id) {
        getOperator().delete(id);
        logInfo(format("delete entity[%s] by id[%s] successful", getTestObjectName(id), id));
    }

    public String getTestObjectName(I id) {
        Optional<Map.Entry<String, T>> entry = storage.entrySet().stream()
                .filter(e -> Objects.equals(e.getValue().getId(), id))
                .findFirst();

        return entry.isPresent() ? entry.get().getKey() : "";
    }
}
