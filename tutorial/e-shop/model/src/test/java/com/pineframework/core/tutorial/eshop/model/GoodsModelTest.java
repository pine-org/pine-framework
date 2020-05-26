package com.pineframework.core.tutorial.eshop.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pineframework.core.test.AbstractTest;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("Test Goods Model")
class GoodsModelTest extends AbstractTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void deserializeModel() throws JsonProcessingException {
        String json = "{\"name\":\"Table\",\"code\":\"001\",\"price\":\"3.25\"}";
        GoodsModel model = mapper.readValue(json, GoodsModel.class);

        Assert.assertNotNull(model);
        Assert.assertEquals("Table", model.getName());
        Assert.assertEquals("001", model.getCode());
        Assert.assertEquals(BigDecimal.valueOf(325L, 2), model.getPrice());
        logInfo(model);
    }
}