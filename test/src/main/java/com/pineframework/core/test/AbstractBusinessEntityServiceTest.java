package com.pineframework.core.test;

import com.pineframework.core.contract.service.entityservice.BatchEntityService;
import com.pineframework.core.contract.service.entityservice.CrudEntityService;
import com.pineframework.core.contract.service.entityservice.QueryEntityService;
import com.pineframework.core.datamodel.model.FlatTransient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractBusinessEntityServiceTest<E extends CrudEntityService &
        QueryEntityService &
        BatchEntityService>
        extends AbstractTest implements BasicBusinessOperation<FlatTransient, E> {

    protected final E operator;

    protected Map<String, FlatTransient> models;

    public AbstractBusinessEntityServiceTest(E service) {
        this.operator = service;
        models = new HashMap<>();
        initData(models);
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
        return models.get(name);
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

}
