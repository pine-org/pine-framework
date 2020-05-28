package com.pineframework.core.tutorial.eshop.integrationtest;

import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Map;

@DisplayName("E-Shop Goods Repository Test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class GoodsRepositoryTest extends AbstractRepositoryTest<GoodsRepository> {

    @Autowired
    public GoodsRepositoryTest(GoodsRepository service) {
        super(service);
    }

    @Override
    public void initData(Map<String, FlatPersistence> models) {
        GoodsEntity table = new GoodsEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        models.put("table", table);

        GoodsEntity desk = new GoodsEntity();
        table.setName("Desk");
        table.setCode("002");
        table.setPrice(BigDecimal.valueOf(7575, 2));
        models.put("desk", desk);

        GoodsEntity chair = new GoodsEntity();
        table.setName("Chair");
        table.setCode("003");
        table.setPrice(BigDecimal.valueOf(6565, 2));
        models.put("chair", chair);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three entities")
    @Order(1)
    void save_SaveNewGoods_ReturnId(String name) {
        saveDataThenAssertIdIsNotNull(name);
    }

    @Test
    @DisplayName("Find all entities and expected size is three.")
    @Order(2)
    public void findAll_FindAllEntity_ReturnEntityArray() {
        findAllDataAndExpectedCountIs(3);
    }
}