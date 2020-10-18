package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.test.AbstractEntityServiceTest;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("E-Shop Goods Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GoodsEntityServiceTest extends AbstractEntityServiceTest<Long, GoodsModel, GoodsEntityService> {

    public static final Map<String, GoodsModel> STORAGE = new HashMap();

    @Autowired
    public GoodsEntityServiceTest(GoodsEntityService service) {
        super(service, STORAGE);
    }

    @Override
    public void init() {
        addToStorage("table", new GoodsModel.Builder("Table", "001")
                .price(BigDecimal.valueOf(8585, 2)).build());
        addToStorage("desk", new GoodsModel.Builder("Desk", "002")
                .price(BigDecimal.valueOf(7575, 2)).build());
        addToStorage("chair", new GoodsModel.Builder("Chair", "003")
                .price(BigDecimal.valueOf(6565, 2)).build());
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three goods model {table, desk, chair}")
    @Order(1)
    public void save_SaveNewGoods_ReturnId(String name) {
        GoodsModel model = getFromStorage(name);
        Long id = save(model);
        assertNotNull(id);
        assertThat(id).isIn(1L, 2L, 3L);
        updateStorage(name, new GoodsModel.Builder(model.getName(), model.getCode())
                .from(model)
                .id(id)
                .build());
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Find by goods model id")
    @Order(2)
    public void findById_LongNumberAsGoodsId_ReturnGoodsModel(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        GoodsModel model = findById(id);

        assertNotNull(model);
        assertEquals(testModel.getName(), model.getName());
        assertEquals(testModel.getCode(), model.getCode());
        assertEquals(testModel.getPrice(), model.getPrice());
        updateStorage(name, model);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Update name of three goods model {table, desk, chair}")
    @Order(3)
    public void update_WhenDataIsChanged_ThenSaveUpdatedModel(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        Integer version = testModel.getVersion();
        assertNotNull(id);
        assertNotNull(version);

        GoodsModel updateTestModel = new GoodsModel.Builder(testModel.getName() + "_updated", testModel.getCode())
                .from(testModel).build();

        GoodsModel model = update(updateTestModel);
        assertNotNull(model);
        assertEquals(testModel.getName() + "_updated", model.getName());
        assertTrue(model.getVersion() == version + 1);
        updateStorage(name, model);
    }


    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Delete three goods model {table, desk, chair}")
    @Order(4)
    public void delete_WhenDataIsDelete_ThenDecreaseCountAllAsManyOne(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);
        deleteById(id);
    }

}