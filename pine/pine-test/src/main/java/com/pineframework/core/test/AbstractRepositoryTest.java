package com.pineframework.core.test;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractRepositoryTest<E extends CrudRepository & QueryRepository & BatchRepository>
        extends AbstractTest implements BasicBusinessOperation<FlatPersistence, E> {

    protected final E operator;

    protected Map<String, FlatPersistence> records = new HashMap<>();

    public AbstractRepositoryTest(E service) {
        this.operator = service;
        initData(records);
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
    public final FlatPersistence getData(String name) {
        return records.get(name);
    }

    @Override
    public void saveDataThenAssertIdIsNotNull(String name) {
        FlatPersistence entity = getData(name);
        getOperator().save(entity);
        assertNotNull(entity.getId());
        logInfo(format("save %s is successful -> %d", name, entity.getId()));
    }

    @Override
    public void findAllDataWithExpectedCount(int count) {
        FlatPersistence[] data = getOperator().findAll();
        assertNotNull(data);
        assertEquals(count, data.length);
        logInfo(Arrays.toString(data));
    }

    @Override
    public void updateCurrentDataWith(String name) {

    }

    @Override
    public void deleteDataThenDecreaseCount(String name) {
        long countBeforeDelete = getOperator().count();
        getOperator().delete(findData(name).get().getId());
        long countAfterDelete = getOperator().count();

        assertEquals(1, (countBeforeDelete - countAfterDelete));
        logInfo(format("delete %s is successful", name));

    }

    private Optional<FlatPersistence> findData(String name) {
        Optional<FlatPersistence> data = getOperator().findOne(getData(name).toFilter());
        assertNotNull(data.get());
        assertNotNull(data.get().getId());
        return data;
    }
}
