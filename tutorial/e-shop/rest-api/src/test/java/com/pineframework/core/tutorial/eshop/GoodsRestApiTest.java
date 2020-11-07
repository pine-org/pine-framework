package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.spring.test.AbstractRestfulWebService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    @DisplayName("GIVEN a Goods as a JSON model WHEN post the model THEN response is empty and header has location")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(1)
    void save_GivenGoodsAsJsonModel_WhenPostTheModel_ThenResponseIsEmptyAndHeaderHasLocation(String name) {
        GoodsModel testModel = getFromStorage(name);
        ResponseEntity<String> response = post(testModel, API_V1_GOODS);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        String identity = substringAfterLast(response.getHeaders().getLocation().getPath(), "/");
        assertNotNull(identity);
        assertTrue(isNumeric(identity));

        GoodsModel model = new GoodsModel.Builder(testModel.getName(), testModel.getCode())
                .from(testModel)
                .id(Long.valueOf(identity))
                .build();
        updateStorage(name, model);
    }

    @ParameterizedTest
    @DisplayName("GIVEN identity WHEN fetch the object THEN response body include a Goods")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(2)
    void findById_GivenIdentity_WhenFetchTheObject_ThenResponseBodyIncludeGoods(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        ResponseEntity<String> response = read(id, API_V1_GOODS);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        GoodsModel model = Try.of(() -> objectMapper.readValue(response.getBody(), modelType)).get();
        assertEquals(testModel.getId(), model.getId());
        assertEquals(testModel.getName(), model.getName());
        assertEquals(testModel.getCode(), model.getCode());
        assertEquals(testModel.getPrice(), model.getPrice());
        assertNotNull(model.getInsertDate());
        assertNotNull(model.getVersion());

        updateStorage(name, model);
    }

    @ParameterizedTest
    @DisplayName("GIVEN a Goods as a JSON model WHEN put the model THEN response body is empty and status is no content")
    @ValueSource(strings = {"table"})
    @Order(3)
    void update_GivenGoodsAsJsonModel_WhenPutTheModel_ThenResponseIsEmptyAndStatusIsNoContent(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();

        GoodsModel updatedModel = new GoodsModel.Builder(testModel.getName() + "_updated", testModel.getCode())
                .from(testModel)
                .build();

        ResponseEntity<String> response = put(id, updatedModel, API_V1_GOODS);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        GoodsModel model = getById(id, API_V1_GOODS);
        assertEquals(updatedModel.getId(), model.getId());
        assertEquals(updatedModel.getName(), model.getName());
        assertEquals(updatedModel.getCode(), model.getCode());
        assertEquals(updatedModel.getPrice(), model.getPrice());
        assertEquals(updatedModel.getInsertDate(), model.getInsertDate());
        assertEquals(updatedModel.getVersion() + 1, model.getVersion());
        assertNotNull(model.getModifyDate());

        updateStorage(name, model);
    }

    @ParameterizedTest
    @DisplayName("GIVEN an identity WHEN send a delete request THEN response body is empty and status is no content")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(3)
    void delete_GivenIdentity_WhenSendDeleteRequest_ThenResponseIsEmptyAndStatusIsNoContent(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();

        ResponseEntity<String> response = delete(id, API_V1_GOODS);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<String> response2 = read(id, API_V1_GOODS);

        deleteFromStorage(name);
    }

}