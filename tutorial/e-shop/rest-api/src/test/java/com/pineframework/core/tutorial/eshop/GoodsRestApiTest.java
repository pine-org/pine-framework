package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.spring.test.AbstractRestfulWebService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("E-Shop Goods Rest API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GoodsRestApiTest extends AbstractRestfulWebService<Long, GoodsModel> {

    public static final String API_V1_GOODS = "/api/v1/goods";

    public static final Map<String, GoodsModel> STORAGE = new HashMap<>();

    public GoodsRestApiTest() {
        super(STORAGE);
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
    @DisplayName("GIVEN a Goods as a JSON model WHEN post the model THEN response empty and header has location")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(1)
    void post_GivenGoodsJsonModel_WhenPostTheModel_ThenResponseIsEmptyAndHeaderHasLocation(String name) {
        GoodsModel model = getFromStorage(name);
        URI location = post(model, API_V1_GOODS);
        String identity = substringAfterLast(location.getPath(), "/");
        assertNotNull(identity);
        assertTrue(isNumeric(identity));

        GoodsModel goods = new GoodsModel.Builder(model.getName(), model.getCode())
                .from(model)
                .id(Long.valueOf(identity))
                .build();
        updateStorage(name, goods);
    }

    @ParameterizedTest
    @DisplayName("GIVEN identity WHEN get the object THEN response is a Goods")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(2)
    void findById_GIVENIdentityWHENGetTheObjectTHENResponseIsGoods(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        GoodsModel model = getById(id, API_V1_GOODS);

        assertEquals(testModel.getId(), model.getId());
        assertEquals(testModel.getName(), model.getName());
        assertEquals(testModel.getCode(), model.getCode());
        assertEquals(testModel.getPrice(), model.getPrice());
        assertNotNull(model.getInsertDate());
        assertNotNull(model.getVersion());

        updateStorage(name, model);
    }

}