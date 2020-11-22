package com.pineframework.core.tutorial.eshop.test;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.test.JpaRepositoryTestImpl;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class RepositoryTest extends AbstractRepositoryTest<Long, GoodsEntity, GoodsRepository> {

    public static final Map<String, GoodsEntity> STORAGE = new HashMap<>();

    public RepositoryTest() {
        super(new GoodsRepositoryImpl(new JpaRepositoryTestImpl()), STORAGE);
    }

    @Override
    public void init() {
        GoodsEntity table = new GoodsEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        addToStorage("table", table);

        GoodsEntity desk = new GoodsEntity();
        desk.setName("Desk");
        desk.setCode("002");
        desk.setPrice(BigDecimal.valueOf(7575, 2));
        addToStorage("desk", desk);

        GoodsEntity chair = new GoodsEntity();
        chair.setName("Chair");
        chair.setCode("003");
        chair.setPrice(BigDecimal.valueOf(6565, 2));
        addToStorage("chair", chair);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three goods entity {table, desk, chair}")
    @Order(1)
    void save_GivenGoods_WhenSaveFunction_ThenReturnID(String name) {
        GoodsEntity entity = getFromStorage(name);
        Long id = save(entity);
        assertNotNull(id);
        assertThat(id).isIn(1L, 2L, 3L);
    }
}
