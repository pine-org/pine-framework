package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.datamodel.model.FlatTransient;
import com.pineframework.core.test.AbstractEntityServiceTest;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Map;

@SpringBootTest
@DisplayName("E-Shop Goods Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class GoodsEntityServiceTest extends AbstractEntityServiceTest<GoodsEntityService> {

    @Autowired
    public GoodsEntityServiceTest(GoodsEntityService service) {
        super(service);
    }

    @Override
    public void initData(Map<String, FlatTransient> models) {
        models.put("table", new GoodsModel.Builder("Table", "001").price(BigDecimal.valueOf(8585, 2)).build());
        models.put("desk", new GoodsModel.Builder("Desk", "002").price(BigDecimal.valueOf(7575, 2)).build());
        models.put("chair", new GoodsModel.Builder("Chair", "003").price(BigDecimal.valueOf(6565, 2)).build());
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three models")
    @Order(1)
    public void save_SaveNewGoodsModelInDatabase_ReturnId(String name) {
        saveDataThenAssertIdIsNotNull(name);
    }

    @Test
    @DisplayName("Find all models and expected size is three.")
    @Order(2)
    public void findAll_FindAllGoodsModel_ReturnGoodsModelArray() {
        findAllDataAndExpectedCountIs(3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Delete three models")
    @Order(3)
    public void delete_WhenDataIsDelete_ThenDecreaseCountAllAsManyOne(String name) {
        deleteDataThenDecreaseCount(name);
    }

}