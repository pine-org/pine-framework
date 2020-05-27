package com.pineframework.core.tutorial.eshop.integrationtest;

import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("E-Shop Test ")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GoodsEntityServiceTest {

    private static HashMap<String, GoodsModel> models;

    @Autowired
    private GoodsEntityService service;

    @BeforeAll
    static void beforeAll() {
        models = new HashMap() {{
            put("table", new GoodsModel.Builder("Table", "001").price(BigDecimal.valueOf(235, 2)).build());
        }};

    }

    @BeforeEach
    void setUp() {
        assertNotNull(service);
    }

    @Test
    @Order(1)
    void save_SaveNewGoods_ReturnId() {
        Optional<Long> model = service.save(models.get("table"));
        assertNotNull(model.get());
    }

    @Test
    @Order(2)
    void findAll_FindAllGoods_ReturnGoodsArray() {
        GoodsModel[] models = service.findAll();
        assertNotNull(models);
        assertEquals(1, models.length);
    }
}