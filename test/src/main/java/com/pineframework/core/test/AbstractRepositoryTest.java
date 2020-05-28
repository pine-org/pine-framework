package com.pineframework.core.test;

import com.pineframework.core.contract.repository.BatchRepository;
import com.pineframework.core.contract.repository.CrudRepository;
import com.pineframework.core.contract.repository.QueryRepository;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Commit
public abstract class AbstractRepositoryTest<E extends CrudRepository & QueryRepository & BatchRepository>
        extends AbstractTest implements BasicBusinessOperation<FlatPersistence, E> {

    protected final E operator;

    protected Map<String, FlatPersistence> entities;

    public AbstractRepositoryTest(E service) {
        this.operator = service;
        entities = new HashMap<>();
        initData(entities);
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
        return entities.get(name);
    }

    @Override
    public void saveDataThenAssertIdIsNotNull(String name) {
        FlatPersistence entity = getData(name);
        getOperator().save(entity);
        assertNotNull(entity.getId());
    }

    @Override
    public void findAllData(int expectedSize) {
        FlatPersistence[] entities = getOperator().findAll();
        assertNotNull(entities);
        assertEquals(expectedSize, entities.length);
    }
}
