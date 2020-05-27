package com.pineframework.core.tutorial.eshop.integrationtest;

import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("E-Shop Goods Repository Test ")
@SpringBootTest
@Transactional
@Commit
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository repository;

    @BeforeEach
    void setUp() {
        assertNotNull(repository);
    }

    @Test
    void save_SaveNewGoods_ReturnId() {
        GoodsEntity entity = new GoodsEntity();
        entity.setName("Table");
        entity.setCode("001");
        entity.setPrice(BigDecimal.valueOf(235, 2));

        repository.save(entity);

        assertNotNull(entity.getId());
    }
}