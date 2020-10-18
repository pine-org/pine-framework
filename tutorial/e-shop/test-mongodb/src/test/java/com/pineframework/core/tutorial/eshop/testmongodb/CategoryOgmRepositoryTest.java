package com.pineframework.core.tutorial.eshop.testmongodb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryOgmRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(CategoryOgmRepositoryTest.class);

    @Autowired
    private CategoryOgmRepository repository;

    @Before
    public void setUp() {
        assertNotNull(repository);
    }

    @Test
    public void save_CategoryAsParam_ReturnId() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Tools");
        category.setCode("001");
        Long id = repository.save(category);
        assertNotNull(id);
        logger.info(format("\n TEST : Category [Stuff] persisted : Return ID=%d", id));
    }
}