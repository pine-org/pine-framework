package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.datamodel.exception.NotFoundDataByIdException;
import com.pineframework.core.spring.restapi.restcontroller.ErrorResponse;
import com.pineframework.core.spring.test.AbstractRestfulWebService;
import com.pineframework.core.tutorial.eshop.model.ProductModel;
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
@DisplayName("E-Shop Product Rest API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductRestApiTest extends AbstractRestfulWebService<Long, ProductModel> {

    public static final String API_V1_PRODUCT = "/api/v1/product";

    public static final Map<String, ProductModel> STORAGE = new HashMap<>();

    public ProductRestApiTest() {
        super(STORAGE);
    }

    @Override
    public void init() {
        addToStorage("table", new ProductModel.Builder("Table", "001")
                .price(BigDecimal.valueOf(8585, 2)).build());
        addToStorage("desk", new ProductModel.Builder("Desk", "002")
                .price(BigDecimal.valueOf(7575, 2)).build());
        addToStorage("chair", new ProductModel.Builder("Chair", "003")
                .price(BigDecimal.valueOf(6565, 2)).build());
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @DisplayName("post request")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(1)
    void save_GivenNewProduct_WhenPostRequest_ThenReturnCreateStatus(String name) {
        ProductModel testModel = getFromStorage(name);
        ResponseEntity<String> response = post(testModel, API_V1_PRODUCT);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        String identity = substringAfterLast(response.getHeaders().getLocation().getPath(), "/");
        assertNotNull(identity);
        assertTrue(isNumeric(identity));

        ProductModel model = new ProductModel.Builder(testModel.getName(), testModel.getCode())
                .from(testModel)
                .id(Long.valueOf(identity))
                .build();
        updateStorage(name, model);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @DisplayName("get request")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(2)
    void findById_GivenIdentity_WhenGetRequest_ThenReturnOKStatus(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        ResponseEntity<String> response = read(id, API_V1_PRODUCT);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductModel model = Try.of(() -> objectMapper.readValue(response.getBody(), modelType)).get();
        assertEquals(testModel.getId(), model.getId());
        assertEquals(testModel.getName(), model.getName());
        assertEquals(testModel.getCode(), model.getCode());
        assertEquals(testModel.getPrice(), model.getPrice());
        assertNotNull(model.getInsertDate());
        assertNotNull(model.getVersion());

        updateStorage(name, model);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @DisplayName("put request")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(3)
    void update_GivenChangedProduct_WhenPutRequest_ThenReturnNoContentStatus(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();

        ProductModel updatedModel = new ProductModel.Builder(testModel.getName() + "_updated", testModel.getCode())
                .from(testModel)
                .build();

        ResponseEntity<String> response = put(id, updatedModel, API_V1_PRODUCT);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ProductModel model = getById(id, API_V1_PRODUCT);
        assertEquals(updatedModel.getId(), model.getId());
        assertEquals(updatedModel.getName(), model.getName());
        assertEquals(updatedModel.getCode(), model.getCode());
        assertEquals(updatedModel.getPrice(), model.getPrice());
        assertEquals(updatedModel.getInsertDate(), model.getInsertDate());
        assertEquals(updatedModel.getVersion() + 1, model.getVersion());
        assertNotNull(model.getModifyDate());

        updateStorage(name, model);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @DisplayName("delete request")
    @ValueSource(strings = {"table", "desk", "chair"})
    @Order(3)
    void delete_GivenIdentity_WhenDeleteRequest_ThenReturnNoContentStatus(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();

        ResponseEntity<String> deleteResponse = delete(id, API_V1_PRODUCT);
        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<String> readResponse = read(id, API_V1_PRODUCT);
        assertNotNull(readResponse);
        assertNotNull(readResponse.getBody());

        ErrorResponse[] errors = Try.of(() -> objectMapper.readValue(readResponse.getBody(), ErrorResponse[].class))
                .get();
        assertNotNull(errors);
        assertEquals(NotFoundDataByIdException.CODE, errors[0].getCode());
        assertEquals(String.format("There is no any data for ProductModel with identity %s", id), errors[0].getMessage());

        deleteFromStorage(name);
    }

}