package com.pineframework.core.mongodb;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pineframework.core.mongodb.HibernateOgmUtils.closeApplication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryOgmRepositoryTest {

    public static final Map<String, CategoryEntity> TEST_STORAGE = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(CategoryOgmRepositoryTest.class);

    private final CategoryOgmRepository repository = CategoryOgmRepository.INSTANCE;

    @AfterAll
    public static void afterClass() throws Exception {
        closeApplication();
    }

    @BeforeEach
    public void setUp() {
        assertNotNull(repository);
    }

    @Order(1)
    @Test
    public void insert_GivenProduct_WhenExecuteInsertQuery_ThenSaveAndReturnId() {
        CategoryEntity entity = new CategoryEntity();
        entity.setName("Test_Category");
        entity.setCode("Test_001");
        Long id = repository.insert(entity);
        assertNotNull(id);

        TEST_STORAGE.put("Test_1", entity);
    }

    @Order(2)
    @Test
    public void selectAll_GivenNoParam_WhenExecuteSelectAllQuery_ThenReturnEntityList() {
        List<CategoryEntity> entities = repository.selectAll();
        assertNotNull(entities);
        assertFalse(entities.isEmpty());
    }

    @Order(3)
    @Test
    public void selectById_GivenAnId_WhenExecuteSelectByIdQuery_ThenReturnOneEntity() {
        Optional<CategoryEntity> entity = repository.selectById(TEST_STORAGE.get("Test_1").getId());
        assertTrue(entity.isPresent());
        entity.ifPresent(it -> {
            assertEquals("Test_Category", it.getName());
            assertEquals("Test_001", it.getCode());
        });
    }

    @Order(4)
    @Test
    public void deleteById_GivenAnId_WhenExecuteDeleteByIdQuery_ThenReturnTrue() {
        Boolean deleted = repository.deleteById(TEST_STORAGE.get("Test_1").getId());
        assertTrue(deleted);
    }
}