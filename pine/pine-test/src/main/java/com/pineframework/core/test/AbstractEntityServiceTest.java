package com.pineframework.core.test;

import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractEntityServiceTest<E extends CrudEntityService & QueryEntityService & BatchEntityService>
        extends AbstractTest implements BasicBusinessOperation<FlatTransient, E> {

    protected final E operator;

    protected Map<String, FlatTransient> records = new HashMap<>();

    public AbstractEntityServiceTest(E service) {
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
    public final FlatTransient getData(String name) {
        return records.get(name);
    }

    @Override
    public void saveDataThenAssertIdIsNotNull(String name) {
        Optional<Long> id = getOperator().save(getData(name));
        assertNotNull(id.get());
        logInfo(format("save %s is successful -> %d", name, id.get()));
    }

    @Override
    public void findAllDataAndExpectedCountIs(int count) {
        FlatTransient[] data = getOperator().findAll();
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

    public Optional<FlatTransient> findData(String name) {
        Optional<FlatTransient> data = getOperator().findByModel(getData(name));
        assertNotNull(data.get());
        assertNotNull(data.get().getId());
        return data;
    }
}
