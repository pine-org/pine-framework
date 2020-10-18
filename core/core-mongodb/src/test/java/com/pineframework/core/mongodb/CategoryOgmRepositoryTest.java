package com.pineframework.core.mongodb;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pineframework.core.mongodb.HibernateOgmUtils.closeApplication;
import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;

public class CategoryOgmRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(CategoryOgmRepositoryTest.class);

    private final CategoryOgmRepository repository = CategoryOgmRepository.INSTANCE;

    @AfterClass
    public static void afterClass() throws Exception {
        closeApplication();
    }

    @Before
    public void setUp() {
        assertNotNull(repository);
    }

    @Test
    public void insert_GoodsAsParam_ReturnId() {
        CategoryEntity goods = new CategoryEntity();
        goods.setName("Stuff");
        goods.setCode("001");
        Long id = repository.insert(goods);
        assertNotNull(id);
        logger.info(format("\n TEST [insert_GoodsAsParam_ReturnId] : Category [Stuff] persisted : Return ID=%d", id));
    }
}