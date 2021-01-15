package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pineframework.core.test.AbstractTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("Test Product Model")
class ProductModelTest extends AbstractTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    @DisplayName("deserialize the string of data to Table model")
    void deserializeStringToModel() throws JsonProcessingException {
        String json = "{\n" +
                "    \"id\": 1,\n" +
                "    \"insertDate\": \"2020-11-07T16:13:41.918\",\n" +
                "    \"insertUserId\": 1,\n" +
                "    \"insertUnitId\": 1,\n" +
                "    \"modifyDate\": \"2020-11-07T16:15:12.015\",\n" +
                "    \"modifyUserId\": 1,\n" +
                "    \"modifyUnitId\": 1,\n" +
                "    \"version\": 1,\n" +
                "    \"name\": \"Table\",\n" +
                "    \"code\": \"001\",\n" +
                "    \"price\": 85.85,\n" +
                "    \"description\": null,\n" +
                "    \"photo\": null\n" +
                "}";
        ProductModel model = mapper.readValue(json, ProductModel.class);

        Assert.assertNotNull(model);
        Assert.assertEquals("Table", model.getName());
        Assert.assertEquals("001", model.getCode());
        Assert.assertEquals(BigDecimal.valueOf(8585L, 2), model.getPrice());
        logInfo(model);
    }
}